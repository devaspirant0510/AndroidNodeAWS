package com.example.kotlinrest.ui.dialog

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlinrest.R
import com.example.kotlinrest.databinding.ActivityMainBinding
import com.example.kotlinrest.databinding.BottomSheetMovieInfoBinding
import com.example.kotlinrest.model.JsonMoviesInfo
import com.example.kotlinrest.model.MovieInfoDialogData
import com.example.kotlinrest.ui.activity.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson

/**
 * @project 
 * @class MovieInfoBottomSheet.kt
 * @author seungho
 * @github devaspirant0510
 * @email seungho020510@gmail.com
 * @since 2021-06-10
 * @description
 **/
class MovieInfoBottomSheet(context: Context, private val pos: String? = null)  : BottomSheetDialog(context) {
    lateinit var mb:BottomSheetMovieInfoBinding


    private var movieInfoBottomSheetCallback: MovieInfoBottomSheetCallback? = null;

    private val url = "http://ec2-3-36-106-46.ap-northeast-2.compute.amazonaws.com:3001/movies"
    interface MovieInfoBottomSheetCallback{
        fun onSubmit(data:MovieInfoDialogData)
    }

    public fun setMovieInfoCallback(callback: MovieInfoBottomSheetCallback){
        this.movieInfoBottomSheetCallback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mb = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.bottom_sheet_movie_info,null,false)
        setContentView(mb.root)
        init()
    }
    private fun init(){
        val volley = Volley.newRequestQueue(context)
        if (pos!=null){
            mb.tvMovieDirector.visibility = View.VISIBLE
            mb.tvMovieTitle.visibility = View.VISIBLE
            mb.tvMovieYear.visibility = View.VISIBLE
            mb.tvMovieSynopsis.visibility = View.VISIBLE
            mb.etMovieDirector.visibility = View.GONE
            mb.etMovieTitle.visibility = View.GONE
            mb.etMovieYear.visibility = View.GONE
            mb.etMovieSynopsis.visibility = View.GONE
            mb.btnDestroy.visibility = View.VISIBLE
            mb.btnSubmit.visibility = View.GONE
            val request: StringRequest = StringRequest(
                Request.Method.GET,
                "$url/$pos",
                { response ->
                    requestGet(response)

                },
                { error ->
                }
            )
            // 3) 생성한 StringRequest를 RequestQueue에 추가
            volley.add(request)
            mb.btnDestroy.setOnClickListener {
                dismiss()

            }

        }
        else{
            mb.etMovieDirector.visibility = View.VISIBLE
            mb.etMovieTitle.visibility = View.VISIBLE
            mb.etMovieYear.visibility = View.VISIBLE
            mb.etMovieSynopsis.visibility = View.VISIBLE
            mb.tvMovieDirector.visibility = View.GONE
            mb.tvMovieTitle.visibility = View.GONE
            mb.tvMovieYear.visibility = View.GONE
            mb.tvMovieSynopsis.visibility = View.GONE
            mb.btnDestroy.visibility = View.GONE
            mb.btnSubmit.visibility = View.VISIBLE
            mb.btnSubmit.setOnClickListener {
                movieInfoBottomSheetCallback?.onSubmit(
                    MovieInfoDialogData(
                        mb.etMovieTitle.text.toString(),
                        mb.etMovieDirector.text.toString(),
                        mb.etMovieSynopsis.text.toString(),
                        mb.etMovieYear.text.toString().toInt()
                    )

                )
                dismiss()
            }

        }




    }
    private fun requestGet(response: String){
        val gson:Gson = Gson()
        val jsonMoviesInfo = gson.fromJson(response,JsonMoviesInfo::class.java)

        val getData:JsonMoviesInfo = JsonMoviesInfo(
            jsonMoviesInfo.id,
            jsonMoviesInfo.title,
            jsonMoviesInfo.director,
            jsonMoviesInfo.year,
            jsonMoviesInfo.synopsis
        )
        mb.data = getData




    }
}