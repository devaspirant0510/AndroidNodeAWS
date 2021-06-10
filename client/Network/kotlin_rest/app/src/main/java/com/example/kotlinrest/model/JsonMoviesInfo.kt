package com.example.kotlinrest.model

/**
 * @project 
 * @class JsonMoviesInfo.kt
 * @author seungho
 * @github devaspirant0510
 * @email seungho020510@gmail.com
 * @since 2021-06-10
 * @description
 **/
data class JsonMoviesInfo (val id:Int,val title:String,val director:String,
                      val year:Int,val synopsis:String){
}