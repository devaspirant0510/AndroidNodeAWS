package com.example.kotlinrest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlinrest.JsonAdapter
import com.example.kotlinrest.databinding.ActivityMainBinding
import com.example.kotlinrest.model.JsonMovies
import com.example.kotlinrest.model.JsonMoviesAll
import com.example.kotlinrest.model.ListData
import com.example.kotlinrest.model.MovieInfoDialogData
import com.example.kotlinrest.ui.dialog.MovieInfoBottomSheet
import com.google.gson.Gson
import kotlin.math.log
import kotlin.String as String

/**
 * @project
 * @class MainActivity.kt
 * @author seungho
 * @github devaspirant0510
 * @email seungho020510@gmail.com
 * @since 2021-06-10
 * @description
 **/
class MainActivity : AppCompatActivity(), JsonAdapter.jsonAdapterCallback,
    MovieInfoBottomSheet.MovieInfoBottomSheetCallback {
    private val adapter: JsonAdapter = JsonAdapter()
    private val url = "http://ec2-3-36-106-46.ap-northeast-2.compute.amazonaws.com:3001/movies";
    private lateinit var queue: RequestQueue
    private val TAG: String = "MainActivity"

    private val mb by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mb.root)
        init()
        requestGet()
        mb.btnTest.setOnClickListener {
            val bottomSheetDialog = MovieInfoBottomSheet(this@MainActivity, null)
            bottomSheetDialog.setMovieInfoCallback(this)
            bottomSheetDialog.show()
        }
    }

    private fun init() {
        queue = Volley.newRequestQueue(this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        mb.recyclerView.layoutManager = layoutManager
        mb.recyclerView.adapter = adapter
        adapter.setJsonAdapterCallback(this)

    }

    private fun requestGet() {
        var getData: String? = ""
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                moviesSuccessGet(response.toString())
            },
            { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    // url:3001/movies 로 get 요청 보냈을때 Gson
    private fun moviesSuccessGet(response: String) {
        val gson: Gson = Gson()
        val jsonMovies = gson.fromJson(response, JsonMovies::class.java)
        val data = jsonMovies.data

        data.forEachIndexed { index, jsonMoviesInfo ->
            adapter.addItem(ListData(jsonMoviesInfo.title, jsonMoviesInfo.id))
            adapter.notifyItemInserted(index)

        }


    }

    override fun onClick(pos: Int) {
        Toast.makeText(applicationContext, pos.toString(), Toast.LENGTH_SHORT).show()
        val dialog: MovieInfoBottomSheet = MovieInfoBottomSheet(this@MainActivity, pos.toString())
        dialog.show()
    }

    private fun volleyRequestPost(data: MovieInfoDialogData) {
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener { response ->
                Log.e(TAG, "volleyRequestPost: ${response.toString()}")
                val gson:Gson = Gson()
                val getJson = gson.fromJson(response,JsonMovies::class.java)
                val startPos = adapter.itemCount
                val endPos = getJson.data.size
                for (i in startPos until endPos){
                    adapter.addItem(ListData(getJson.data[i].title,getJson.data[i].id))
                    adapter.notifyItemInserted(i)
                }
            },

            Response.ErrorListener { error ->
                Log.e(TAG, "volleyRequestPost: ${error.message}" )
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["title"] = data.title
                params["director"] = data.director
                params["year"] = data.year.toString() // 얘만 int 로 넘겨야함
                params["synopsis"] = data.subscription

                return params
            }

        }
        queue.add(stringRequest)


    }

    override fun onSubmit(data: MovieInfoDialogData) {
        volleyRequestPost(data)
        Log.e(TAG, "onSubmit: "+data )
        Toast.makeText(applicationContext, data.director, Toast.LENGTH_SHORT).show()


    }

}