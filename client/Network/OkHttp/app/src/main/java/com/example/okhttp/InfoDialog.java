package com.example.okhttp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class InfoDialog extends BottomSheetDialog {
    private EditText movieTitle, movieDirector,movieYear,movieSynopsis;
    private Button submit;
    BottomSheetCallback callback = null;
    public interface BottomSheetCallback{
        void setOnData(String title,String director,int year,String synopsis );
    }
    public void setBottomSheetCallback(BottomSheetCallback callback){
        this.callback = callback;
    }

    public InfoDialog(@NonNull  Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_dialog_info);
        init();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.setOnData(
                        movieTitle.getText().toString(),
                        movieDirector.getText().toString(),
                        Integer.parseInt(movieYear.getText().toString()),
                        movieSynopsis.getText().toString());
                dismiss();
            }
        });
    }
    public void init(){
        movieTitle = findViewById(R.id.movie_title);
        movieDirector = findViewById(R.id.movie_director);
        movieYear = findViewById(R.id.movie_year);
        movieSynopsis = findViewById(R.id.movie_synopsis);
        submit = findViewById(R.id.submit);

    }
}
