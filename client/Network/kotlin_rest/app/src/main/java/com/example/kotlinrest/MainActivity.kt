package com.example.kotlinrest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlinrest.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlin.String as String


class MainActivity : AppCompatActivity() {
    val adapter: JsonAdapter = JsonAdapter()

    val mb by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mb.root)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://ec2-3-36-106-46.ap-northeast-2.compute.amazonaws.com:3001/movies";

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        mb.recyclerView.layoutManager = layoutManager

        mb.recyclerView.adapter = adapter


        var getData: String? = ""
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                moviesSuccessGet(response.toString())
                getData = "Response is: ${response.substring(0, 100)}"
                Toast.makeText(applicationContext, getData, Toast.LENGTH_SHORT).show()
            },
            {
                getData = "That didn't work!"
                Toast.makeText(applicationContext, getData, Toast.LENGTH_SHORT).show()
            })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        mb.btnTest.setOnClickListener {
            Toast.makeText(applicationContext, "저는 코틀린을 잘하고 싶어요ㅜㅜ", Toast.LENGTH_SHORT).show()
        }
    }

    // url:3001/movies 로 get 요청 보냈을때 Gson
    private fun moviesSuccessGet(response: String) {
        val TAG = "asdfa"
        Log.e(TAG, "moviesSuccessGet: $response")
        val gson: Gson = Gson()
        val jsonMovies = gson.fromJson(response, JsonMovies::class.java)
        val data = jsonMovies.data

        data.forEachIndexed { index, jsonMoviesInfo ->
            adapter.addItem(JsonListData(jsonMoviesInfo.title, jsonMoviesInfo.id))
            adapter.notifyItemInserted(index)

        }


    }
}