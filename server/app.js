const express = require("express");
const path = require("path");
const dotenv = require("dotenv");
dotenv.config();
const Sequelize = require("sequelize");
const nunjucks = require("nunjucks");

const {sequelize,Movie,MovieInfo} = require("./models/");

const app = express();

app.use(express.urlencoded({extended:false}));
app.use(express.json());

app.set("port",process.env.PORT||3001);
app.set("view engine","html")
app.set("views",path.join(__dirname,"views"))

nunjucks.configure({
    express:app,
    watch:true
})

sequelize.sync({force:false}).then(()=>{
    console.log("연결성공");
}).catch(reason =>{
    console.log(reason)
});


//let json_data = JSON.stringify(data);

function addJsonData(id,title,director,year,synopsis){
    return {id:id,
            title:title,
            director:director,
            year:year,
            synopsis:synopsis};
}

// app.use("/",express.static("html"));
app.use("/game",express.static("game"));
app.use("/a",express.static("views"));
app.use(bodyParser.urlencoded({extended:true}));

app.get("/",(req, res) => {
    fs.readFile(__dirname+"\\html\\index.html",(err, data1) => {
        console.log(data1);
        res.end(data1);
    })
});
app.get("/mypage",(req,res)=>{
    //res.writeHead(200,{"Content-Type":"text/html"});

    fs.readFile("./html/index.html",(err, data1) => {
        res.end(data1);
    });
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
