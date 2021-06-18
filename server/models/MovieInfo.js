const Sequelize = require("sequelize");

module.exports = class MovieInfo extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            title:{
                type:Sequelize.STRING,
                allowNull:false,
                unique:true
            },
            director:{
                type:Sequelize.STRING,
                allowNull:false,
            },
            year:{
                type:Sequelize.INTEGER,
                allowNull: false
            },
            synopsis:{
                type:Sequelize.TEXT,
                allowNull:true
            }
        },{
            sequelize,
            timestamps:false,
            underscored:false,
            paranoid:false,
            modelName:"MovieInfo",
            tableName:"movieInfos",
            charset:"utf8",
            collate:"utf8_general_ci"
        });


    }
    static associate(db){

    }
}