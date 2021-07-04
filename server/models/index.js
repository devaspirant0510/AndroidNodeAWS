
const Sequelize = require('sequelize');
const env = process.env.NODE_ENV || 'development';
const config = require(__dirname + '\\..\\config\\config.json')[env];

console.log(env)
const Movie = require("./Movie");
const MovieInfo = require("./MovieInfo");

const db = {};

let sequelize = new Sequelize(config.database, config.username, config.password, config);

db.sequelize = sequelize;
Movie.init(sequelize)
MovieInfo.init(sequelize)

db.Movie =  Movie;
db.MovieInfo = MovieInfo;

module.exports = db;
