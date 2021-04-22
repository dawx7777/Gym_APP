package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN=100000;

    Animation topAnim, bootomAnim, bttwo, btthree, lefttoright;
    ImageView image;
    TextView logo, tekst,btnexercise;
    View bgprogress, getBgprogressstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animacja
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bootomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        bttwo= AnimationUtils.loadAnimation(this,R.anim.bttwo);
        btthree= AnimationUtils.loadAnimation(this,R.anim.btthree);
        lefttoright= AnimationUtils.loadAnimation(this,R.anim.lefttoright);

        image=findViewById(R.id.imageView);
        logo=findViewById(R.id.logotext);
        tekst=findViewById(R.id.opistext);
        btnexercise=(TextView)findViewById(R.id.btnexercise);
        bgprogress=findViewById(R.id.bgprogress);
        getBgprogressstop=findViewById(R.id.bgprogressstop);

        image.setAnimation(topAnim);
        logo.setAnimation(bootomAnim);
        tekst.setAnimation(bootomAnim);
        btnexercise.setAnimation(btthree);
        bgprogress.setAnimation(bttwo);
        getBgprogressstop.setAnimation(lefttoright);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 Intent intent=new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

        btnexercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);

            }
        });
    }
}