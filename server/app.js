const express = require("express");
const fs = require("fs");
const bodyParser =require("body-parser");

const app = express();

let data = fs.readFileSync(`${__dirname}/movies.json`);
data = JSON.parse(data);
//let json_data = JSON.stringify(data);

function addJsonData(id,title,director,year,synopsis){
    return {id:id,
            title:title,
            director:director,
            year:year,
            synopsis:synopsis};
}

app.use("/",express.static("html"));
app.use("/game",express.static("game"));
app.use(bodyParser.urlencoded({extended:true}));

app.get("/s",(req, res) => {
    //res.writeHead(200, {"Content-Type": "text/html;charset=utf-8"});
    // fs.readFile("html/index.html",(err,data)=>{
    //     res.end(data);
    // });
    console.log("d");
    console.log(req.hostname);
    res.redirect("http://www.naver.com");
});


app.get("/game",((req, res) => {
    console.log(__dirname)
    fs.readFile("index.game",(err,data)=>{
        console.log(data);
        res.end(data);
    });
}))

app.get("/movies/:id",(req, res) => {
    const id = req.params.id;
    let index_data = data[id];
    index_data = JSON.stringify(index_data);
    res.writeHead(200,{"Content-Type":"application/json;charset=utf-8"});
    res.end(index_data);
});

app.post("/movies",((req, res,next) => {
    const id = data.length;
    const title = req.body.title;
    const director = req.body.director;
    const year = req.body.year;
    const synopsis = req.body.synopsis;
    const jsonBody = addJsonData(id,title,director,year,synopsis);
    data.push(jsonBody);
    console.log(typeof (data));
    fs.writeFileSync(`${__dirname}/movies.json`,JSON.stringify(data));
    //res.writeHead(200,{"Content-Type":"text/plain;charset=utf-8"});
    //res.redirect("https://www.google.co.kr");
    // res.end();
    res.redirect(`/movies`);

}))
app.get("/movies",(req, res) => {
    const movie_list = [];
    res.writeHead(200,{"Content-Type":"application/json;charset=utf-8"});

    data.forEach(value => {
        movie_list.push({id:value['id'],title:value['title']});
    });

    const data2obj = {data:movie_list};

    const data2json = JSON.stringify(data2obj);
    res.end(data2json);
});

app.listen(3001,()=>{
    console.log("127.0.0.1:3001");
})
