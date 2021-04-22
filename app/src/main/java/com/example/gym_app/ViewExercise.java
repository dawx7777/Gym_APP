package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gym_app.Database.GymDB;
import com.example.gym_app.Utils.Common;
import com.google.android.material.button.MaterialButton;

import pl.droidsonroids.gif.GifImageView;

public class ViewExercise extends AppCompatActivity {
int image_id;
String name;
String opiss;

TextView timer;
    TextView title;
    TextView opis;
GifImageView detail_image;
MaterialButton btnStart;
GymDB gymDB;
boolean isRunning=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gymDB=new GymDB(this);
        timer=(TextView) findViewById(R.id.timer);
        title=(TextView) findViewById(R.id.text_title);
        opis=(TextView) findViewById(R.id.text_opis);
        detail_image=(GifImageView)findViewById(R.id.detail_image);
        btnStart=(MaterialButton) findViewById(R.id.btnStart);
       btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRunning) {
                    btnStart.setText("WYKONANE");


                    int timeLimit=0;
                    if(gymDB.getSettingMode()==0)
                        timeLimit= Common.TIME_LIMIT_EASY;
                    else if(gymDB.getSettingMode()==1)
                        timeLimit= Common.TIME_LIMIT_MEDIUM;
                   else if(gymDB.getSettingMode()==2)
                        timeLimit= Common.TIME_LIMIT_HARD;

                    new CountDownTimer(timeLimit, 1000) {


                        @Override
                        public void onTick(long l) {
                            timer.setText("" + l/ 1000);

                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(ViewExercise.this, "Zakończone!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.start();
                }
                else{
                    Toast.makeText(ViewExercise.this, "Zakończone!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                isRunning = !isRunning;
            }
        });


        timer.setText("");
        if(getIntent() != null){
            image_id=getIntent().getIntExtra("image_id",-1);
            name=getIntent().getStringExtra("name");
            opiss=getIntent().getStringExtra("opiss");


            detail_image.setImageResource(image_id);
            title.setText(name);
            opis.setText(opiss);


        }


    }
}