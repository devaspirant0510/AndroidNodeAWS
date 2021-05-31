const express = require("express");
const fs = require("fs");

const app = express();

let data = fs.readFileSync(`${__dirname}\\movies.json`);
data = JSON.parse(data);
//let json_data = JSON.stringify(data);

app.get("/",(req, res) => {
    res.writeHead(200, {"Content-Type": "text/plain;charset=utf-8"});
    res.end("모바일 API 서버");
});

app.get("/movies",(req, res) => {
    const movie_list = [];
    res.writeHead(200,{"Content-Type":"application/json;charset=utf-8"});

    data.forEach(value => {
        movie_list.push({id:value['id'],title:value['title']});
    });

    const data2json = JSON.stringify(movie_list);
    res.end(data2json);
});

app.get("/movies/:id",(req, res) => {
    const id = req.params.id;
    let index_data = data[id];
    index_data = JSON.stringify(index_data);
    res.writeHead(200,{"Content-Type":"application/json;charset=utf-8"});
    res.end(index_data);
});



app.listen(3001,()=>{
    console.log("127.0.0.1:3001");
})
