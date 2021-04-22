package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class Dashboard extends AppCompatActivity {

    ImageButton btnCwiczenia, btnSetings, btnCalendar;
    ImageView btnTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        btnCalendar=(ImageButton)findViewById(R.id.btncalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Calendar.class);
                startActivity(intent);
            }
        });

        btnTraining=(ImageView)findViewById(R.id.btnTraining);
        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Daily_training.class);
                startActivity(intent);
            }
        });
    btnSetings=(ImageButton) findViewById(R.id.btsettings);
    btnSetings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Dashboard.this,Setting.class);
            startActivity(intent);
        }
    });

        btnCwiczenia=(ImageButton) findViewById(R.id.btncwiczenia);
        btnCwiczenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,ListExcercises.class);
                startActivity(intent);

            }
        });


    }

}

