package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gym_app.Database.GymDB;
import com.example.gym_app.Database.GymmmDB;
import com.example.gym_app.Model.Excercise;
import com.example.gym_app.Utils.Common;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class Daily_training extends AppCompatActivity {

    Button btnStart, btnKoniec;
    GifImageView ex_image;
    TextView txtGetReady,txtCountDown,txtTimer,ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    GymmmDB gymmmDB;
    GymDB gymDB;

    int ex_id=0,limit_time=0;

    List<Excercise> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        gymmmDB=new GymmmDB(this);
        gymDB=new GymDB(this);


        btnKoniec=(MaterialButton)findViewById(R.id.btnKoniec);
        btnStart=(MaterialButton)findViewById(R.id.btnStart);
        ex_image=(GifImageView)findViewById(R.id.ex_img);
        txtCountDown=(TextView)findViewById(R.id.txtCountDown);
        txtGetReady=(TextView)findViewById(R.id.txtGetReady);
        ex_name=(TextView)findViewById(R.id.ex_name);
        txtTimer=(TextView)findViewById(R.id.timer);

        layoutGetReady=(LinearLayout)findViewById(R.id.layout_get_ready);
        progressBar=(ProgressBar)findViewById(R.id.progress);

        progressBar.setMax(list.size());
        setExcerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnStart.getText().toString().toLowerCase().equals("start")) {

                    showGetReady();
                    btnStart.setText("wykonane");
                } else if (btnStart.getText().toString().toLowerCase().equals("wykonane")) {

                    if (gymDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if (gymDB.getSettingMode() == 1)
                        exercisesMediumyModeCountDown.cancel();
                    else if (gymDB.getSettingMode() == 2)
                        exercisesHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if (ex_id < list.size()) {

                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");

                    } else
                        showFinished();
                } else {

                    if (gymDB.getSettingMode() == 0)
                        exercisesEasyModeCountDown.cancel();
                    else if (gymDB.getSettingMode() == 1)
                        exercisesMediumyModeCountDown.cancel();
                    else if (gymDB.getSettingMode() == 2)


                        restTimeCountDown.cancel();

                    if(ex_id<list.size())
                        setExcerciseInformation(ex_id);
                    else showFinished();
                }
            }
        });

    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnStart.setText("POMIŃ");
        txtTimer.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
    restTimeCountDown.start();
        txtGetReady.setText("Resetuj czas");


    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("ZACZYNAMY");
        new CountDownTimer(6000,1000){

            @Override
            public void onTick(long l) {
                txtCountDown.setText(""+(l-1000)/1000);
            }

            @Override
            public void onFinish() {
                showExcercise();
            }
        }.start();
    }

    private void showExcercise() {

        if(ex_id <list.size()){
            ex_image.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);
            btnStart.setVisibility(View.VISIBLE);

            if(gymDB.getSettingMode()==0)
            exercisesEasyModeCountDown.start();
            else if(gymDB.getSettingMode()==1)
            exercisesMediumyModeCountDown.start();
            else if(gymDB.getSettingMode()==2)
            exercisesHardModeCountDown.start();

ex_image.setImageResource(list.get(ex_id).getImage_id());
ex_name.setText(list.get(ex_id).getName());
        }
        else
            showFinished();
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        btnKoniec.setVisibility(View.VISIBLE);

        txtGetReady.setText("ZKOŃCZONE !!!!");
        txtCountDown.setText("Gratulacje \n Wykonałes dzisiejsze ćwiczenia");


        gymmmDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());

        btnKoniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Daily_training.this,Dashboard.class);
                startActivity(intent);

            }
        });
    }

    CountDownTimer exercisesEasyModeCountDown=new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExcerciseInformation(ex_id);
                btnStart.setText("START");
            }
            else
            {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumyModeCountDown=new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExcerciseInformation(ex_id);
                btnStart.setText("START");
            }
            else
            {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountDown=new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExcerciseInformation(ex_id);
                btnStart.setText("START");
            }
            else
            {
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountDown=new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long l) {


            txtCountDown.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            setExcerciseInformation(ex_id);
            showExcercise();
        }
    };

    private void setExcerciseInformation(int id) {

        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("START");


        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.INVISIBLE);


    }

    public void initData(){


            list.add(new Excercise(R.drawable.a, "Ćwiczenia barków z obciążeniem", "Ćwiczenia barków wykonujemy wraz z obciążeniem  najlepiej użyc do tego hantli , lub w warunkach domowych butelki z wodą"));
            list.add(new Excercise(R.drawable.b, "Ćwiczenia brzucha", "Leżac na podłodze unosimy nogi do kąta prostego, następnie wykonujemy brzuszki utrzymując równowagę ciała"));
            list.add(new Excercise(R.drawable.c, "Ćwiczenia barków ", "Wykonujemy ćwiczenia na barki wraz z obciązeniem, prostujać ręce, przytrzymując chwilę na wyproscie"));
            list.add(new Excercise(R.drawable.d, "Przysiady z obciążeniem", "Wykonujemy przysiady z ciężarem wraz z unoszeniem barków do góry"));
            list.add(new Excercise(R.drawable.e, "Wiosłowanie", "Wiosłowanie wykonujemy przy lekkim skłonie, ważne aby plecy były wlini prostej. Hantle wiosłujemy do lini prostej"));
            list.add(new Excercise(R.drawable.f, "Pompka z podskokiem", "Wykonujemy klasyczną pompkę wraz z podskokiem angażując stawy skokowe"));
            list.add(new Excercise(R.drawable.g, "Wykroki z obciązeniem na stopniu", "W warukach domowych używając krzesła, wykonujemy wykroki nożne"));
            list.add(new Excercise(R.drawable.h, "Pompki z przedmiotem", "Wykonujemy pompki, przy górnej pozycji wychylamy na przemian ręke w prawo i lewo"));
            list.add(new Excercise(R.drawable.i, "Pajacyki", "Wykonujemy klasyczne pajacyki anagażujące całe ciało"));
            list.add(new Excercise(R.drawable.j, "Skręty boczne", "W siadzie na podłodze wykoujemy skręty boczne wraz z obciążeniem"));
            list.add(new Excercise(R.drawable.k, "Wykroki tylne", "Używając krzesła wykonujemy wykroki tylne angażujące mięśnie nóg"));
            list.add(new Excercise(R.drawable.l, "Deska", "Wykonujemy klasyczną deskę co chwilę unosząc na przemian ręce"));
        /*
        list.add(new Excercise(R.drawable.m,"Pompki","Wykonujemy klasyczne pompki w określonym czasie"));
        list.add(new Excercise(R.drawable.n,"Unoszenie tłowia","Będąc w pozycji leżacej na brzuchu unosimy ręce i nogi"));
        list.add(new Excercise(R.drawable.o,"Podskoki","Wykonujemy podskoki do przysiadu"));
        list.add(new Excercise(R.drawable.p,"Wypychanie nóg","W pozycji podpartej wykonujemy wypchnięcie nóg do tylu i z powrotem do tłowia"));
        list.add(new Excercise(R.drawable.r,"Ćwiczenia brzucha","Wykonujemy ćwiczenia brzucha uginająć i prostująć nogi w powietrzu"));
        list.add(new Excercise(R.drawable.s,"Krązenia ramion","Wraz z ciężarem wykonujemy krązenia ramion dookoła świata"));
        list.add(new Excercise(R.drawable.t,"Przysiady","Wykonujemy klasyczne przysiady, trzymając w dłoniach ciężarek"));
        list.add(new Excercise(R.drawable.w,"Przysiad kung-fu","Wykonujemy klasyczny przysiad wraz z uniesieniem jednej z nóg na przemian w stylu kung-fu"));
*/

    }
}