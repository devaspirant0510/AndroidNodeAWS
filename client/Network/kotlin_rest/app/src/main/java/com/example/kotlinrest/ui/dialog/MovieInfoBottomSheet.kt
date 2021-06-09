package com.example.kotlinrest.ui.dialog

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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

class MovieInfoBottomSheet(context: Context, private val pos: String) : BottomSheetDialog(context) {
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
        val request: StringRequest = StringRequest(
            Request.Method.GET,
            "$url/$pos",
            { response ->
                requestGet(response)

            },
            { error ->
            }
        )
        volley.add(request)
        // 3) 생성한 StringRequest를 RequestQueue에 추가




        mb.appCompatButton.setOnClickListener {

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