const Sequelize = require("sequelize");

module.exports = class Movie extends Sequelize.Model{
    static init(sequelize){
        return super.init({
            title: {
                type: Sequelize.STRING,
                unique: true,
                allowNull: false
            }
        }, {
            sequelize,
            timestamps:false,
            underscored:false,
            paranoid:false,
            modelName:"Movie",
            tableName:"movies",
            charset:"utf8",
            collate:"utf8_general_ci"
        })

    }
}
