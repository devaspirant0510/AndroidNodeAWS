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
function resDataParse(resCode,data){
    return {
        status:resCode,
        data:data
    };
}

function addJsonData(id,title,director,year,synopsis){
    return {id:id,
            title:title,
            director:director,


            year:year,
            synopsis:synopsis};
}


app.get("/",(req, res) => {
    res.render(path.join(__dirname,"views\\index.html"),{url:"127.0.0.1",port:app.get("port")});
});

app.get("/movies",async (req, res) => {
    try{
        const data = await MovieInfo.findAll({})
        const returnData = resDataParse(res.statusCode,data);
        res.json(returnData);

   }catch (err){

    }
});
app.post("/movies",async (req, res) => {
    try{
        const title = req.body.title;
        const synopsis = req.body.synopsis;
        const year = req.body.year;
        const director = req.body.director;

        const createData = await MovieInfo.create({
            title:title,
            director:director,
            year:year,
            synopsis:synopsis
        });
        const resData = resDataParse(res.statusCode,createData);
        res.json(resData);

    }catch (err){
        res.status(200).send("error");
    }
})
app.get("/movies/:id",async (req, res) => {
    try{
        const id = req.params.id;
        const getData = await MovieInfo.findOne({where:{id:id}});
        if (getData==null){
            res.status(404).send({status:res.statusCode,"data":"error"});
        }else{
            const returnData = resDataParse(res.statusCode,getData);
            res.json(returnData);

        }
    }catch (err){
        res.status(404).send({status:res.statusCode,"data":"error"});
    }

});

app.delete("/movies/:id",async (req, res) => {
    const id = req.params.id;
    const getData = await MovieInfo.destroy({where:{id:id}});
    if(getData==null){
        res.status(404).send({status:res.statusCode,"data":"error"});
    }else{
        const returnData = resDataParse(res.statusCode,getData);
        res.json(returnData);
    }
})

app.put("/movies/:id",async (req, res) => {
    try{
        const id = req.params.id;
        const title = req.body.title;
        console.log(title)
        const synopsis = req.body.synopsis;
        const year = req.body.year;
        const director = req.body.director;
        const getData = await MovieInfo.update({
            id:id,
            title:title,
            director:director,
            year:year,
            synopsis:synopsis
        },{where:{id:id}});
        console.log(getData);
        res.json(getData);
    }catch (err){
        res.status(404).send("error");
    }


});



app.listen(app.get("port"),()=>{
    console.log("127.0.0.1:3001");
})
