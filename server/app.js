const http = require("http");

const PORT = 3001;
const data = {0:{"name":"seungho",
		"age":20,
		"lang":"python"},
		1:{"name":"ttttt",
		"age":30,
		"lang":"go"}}

const server = http.createServer((req,res)=>{
	if (req.url==="/"){
		res.writeHead(200,{"Content-Type":"text/json"});
		let data2Json = JSON.stringify(data);
		res.end(data2Json);
	}
});
server.listen(PORT,()=>{
	console.log("server is open");
});
