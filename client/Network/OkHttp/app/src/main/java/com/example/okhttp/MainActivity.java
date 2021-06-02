package com.example.okhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private RcAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient client = new OkHttpClient();
        recyclerView = findViewById(R.id.json_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RcAdapter();
        recyclerView.setAdapter(adapter);


        FormBody formBody = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url("http://ec2-3-36-106-46.ap-northeast-2.compute.amazonaws.com:3001/movies")
                .get().build();

        client.newCall(request).enqueue(httpCallback);
    }

    private final Callback httpCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.body() != null) {
                String myJsonData = response.body().string();

                Log.e("fd", myJsonData);
                try {
                    JSONObject jsonObject = new JSONObject(myJsonData);
                    JSONArray jsonList = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonList.length(); i++) {
                        JSONObject element = (JSONObject)jsonList.get(i);
                        int getId = element.getInt("id");
                        String getTitle = element.getString("title");
                        adapter.addItem(new RcData(getId,getTitle));
                        Handler handler = new Handler(getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("d", e.toString());
                }

            }
            response.close();
        }
    };
}