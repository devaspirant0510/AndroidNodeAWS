package com.example.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String myUrl = "ec2-3-36-106-46.ap-northeast-2.compute.amazonaws.com:3001";
    private static final String movies = myUrl + "/movies";
    private static final String TAG = "MainActivity";
    private TextView text;
    HttpRequest httpRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

        Toast.makeText(getApplicationContext(), "sdf", Toast.LENGTH_SHORT).show();
        httpRequest = new HttpRequest();
        httpRequest.start();

    }

    private void write(String str) {
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                text.setText(text.getText().toString() + str);

            }
        });
        Log.e(TAG, "wirte: " + str);
    }
    @Override
    protected void onStop() {
        httpRequest.interrupt();
        super.onStop();
    }
    class HttpRequest extends Thread {

        @Override
        public void run() {
            try {
                String naver = "https://www.naver.com";
                URL url = new URL(myUrl);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                int status = http.getResponseCode();

                Log.e(TAG, "run: " + http.getURL());
                http.setConnectTimeout(100000);
                http.setRequestMethod("GET");
//                http.setDoInput(true);
//                http.setDoOutput(true);
                int resCode = http.getResponseCode();
                Log.e(TAG, "run: " + resCode);

                if (resCode == 302) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null)
                            break;
                        write(line);
                    }
                    reader.close();
                }
                http.disconnect();
            } catch (ProtocolException e) {
                Log.e(TAG, e.getMessage() );
            } catch (MalformedURLException e) {
                Log.e(TAG, e.getMessage() );
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage() );
            }

        }
    }
}