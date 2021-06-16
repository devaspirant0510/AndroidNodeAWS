const express = require("express");
const fs = require("fs");

const app = express();

let data = fs.readFileSync(`${__dirname}/movies.json`);
data = JSON.parse(data);

//let json_data = JSON.stringify(data);

function addJsonData(id, title, director, year, synopsis) {
    return {
        id: id,
        title: title,
        director: director,
        year: year,
        synopsis: synopsis
    };
}

// app.use("/",express.static("html"));
app.use("/game", express.static("game"));
app.use("/a", express.static("views"));
app.use(express.urlencoded({extended:false}));
app.use(express.json());

app.get("/", (req, res) => {
    res.sendFile(__dirname+"/html/index.html");
});

app.get("/game", ((req, res) => {
    res.sendFile(__dirname+"/game/index.html");
}))


app.post("/movies", ((req, res, next) => {
    const id = data.length;
    const title = req.body.title;
    const director = req.body.director;
    const year = Number(req.body.year);
    const synopsis = req.body.synopsis;
    const jsonBody = addJsonData(id, title, director, year, synopsis);
    data.push(jsonBody);
    fs.writeFileSync(`${__dirname}/movies.json`, JSON.stringify(data));
    res.json(jsonBody);

}))
app.get("/movies", (req, res) => {
    const movie_list = [];

    data.forEach(value => {
        movie_list.push({id: value['id'], title: value['title']});
    });

    const data2obj = {data: movie_list};

    res.json(data2obj);
});

app.get("/movies/:id", (req, res) => {
    const id = req.params.id;
    let index_data = data[id];
    res.json(index_data);
});

app.put("/movies/:id", (req, res) => {
    const id = req.params.id;
    console.log(id)
    const title = req.body.title;
    console.log(title)
    const director = req.body.director;
    const year = req.body.year;
    const synopsis = req.body.synopsis;
    const jsonBody = addJsonData(id, title, director, year, synopsis);
    data[id] = jsonBody;
    fs.writeFileSync(`${__dirname}/movies.json`, JSON.stringify(data));
    res.json(jsonBody);


});
app.delete("/movies/:id", (req, res) => {
    const id = req.params.id;
    const deleteJson = {index:id,data:data[id]}
    console.log(deleteJson);
    data.splice(id, 1);
    console.log(data)
    fs.writeFileSync(`${__dirname}/movies.json`, JSON.stringify(data));
    res.json(deleteJson);

});

app.listen(3001, () => {
    console.log("127.0.0.1:3001");
})
