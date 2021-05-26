const http = require("http");
const fs = require("fs");
const { stringify } = require("querystring");

const PORT = 3001;
const data = {0:{"name":"seungho",
		"age":20,
		"lang":"python"},
		1:{"name":"ttttt",
		"age":30,
		"lang":"go"}}

const server = http.createServer((req,res)=>{
	if(req.method = "GET"){
		if(req.url==="/"){
			res.writeHead(200,{"Content-Type":"text/json"});
			let data2json = JSON.stringify(data);
			res.end(data2json);
		}
		else if(req.url==="/game"){
			fs.readFile("index.html",(err,data)=>{
				res.writeHead(200,{"Content-Type":"text/html"});
				return res.end(data);
			});
		}
		else{
			console.log(req.url);
			fs.readFile(`.${req.url}`,(err,data)=>{
				return res.end(data);
			})
		}
	}
});
server.listen(PORT,()=>{
	console.log("server is open");
});
