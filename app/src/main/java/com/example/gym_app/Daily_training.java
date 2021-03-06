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
        btnStart.setText("POMI??");
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

        txtGetReady.setText("ZKO??CZONE !!!!");
        txtCountDown.setText("Gratulacje \n Wykona??es dzisiejsze ??wiczenia");


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


            list.add(new Excercise(R.drawable.a, "??wiczenia bark??w z obci????eniem", "??wiczenia bark??w wykonujemy wraz z obci????eniem  najlepiej u??yc do tego hantli , lub w warunkach domowych butelki z wod??"));
            list.add(new Excercise(R.drawable.b, "??wiczenia brzucha", "Le??ac na pod??odze unosimy nogi do k??ta prostego, nast??pnie wykonujemy brzuszki utrzymuj??c r??wnowag?? cia??a"));
            list.add(new Excercise(R.drawable.c, "??wiczenia bark??w ", "Wykonujemy ??wiczenia na barki wraz z obci??zeniem, prostuja?? r??ce, przytrzymuj??c chwil?? na wyproscie"));
            list.add(new Excercise(R.drawable.d, "Przysiady z obci????eniem", "Wykonujemy przysiady z ci????arem wraz z unoszeniem bark??w do g??ry"));
            list.add(new Excercise(R.drawable.e, "Wios??owanie", "Wios??owanie wykonujemy przy lekkim sk??onie, wa??ne aby plecy by??y wlini prostej. Hantle wios??ujemy do lini prostej"));
            list.add(new Excercise(R.drawable.f, "Pompka z podskokiem", "Wykonujemy klasyczn?? pompk?? wraz z podskokiem anga??uj??c stawy skokowe"));
            list.add(new Excercise(R.drawable.g, "Wykroki z obci??zeniem na stopniu", "W warukach domowych u??ywaj??c krzes??a, wykonujemy wykroki no??ne"));
            list.add(new Excercise(R.drawable.h, "Pompki z przedmiotem", "Wykonujemy pompki, przy g??rnej pozycji wychylamy na przemian r??ke w prawo i lewo"));
            list.add(new Excercise(R.drawable.i, "Pajacyki", "Wykonujemy klasyczne pajacyki anaga??uj??ce ca??e cia??o"));
            list.add(new Excercise(R.drawable.j, "Skr??ty boczne", "W siadzie na pod??odze wykoujemy skr??ty boczne wraz z obci????eniem"));
            list.add(new Excercise(R.drawable.k, "Wykroki tylne", "U??ywaj??c krzes??a wykonujemy wykroki tylne anga??uj??ce mi????nie n??g"));
            list.add(new Excercise(R.drawable.l, "Deska", "Wykonujemy klasyczn?? desk?? co chwil?? unosz??c na przemian r??ce"));
        /*
        list.add(new Excercise(R.drawable.m,"Pompki","Wykonujemy klasyczne pompki w okre??lonym czasie"));
        list.add(new Excercise(R.drawable.n,"Unoszenie t??owia","B??d??c w pozycji le??acej na brzuchu unosimy r??ce i nogi"));
        list.add(new Excercise(R.drawable.o,"Podskoki","Wykonujemy podskoki do przysiadu"));
        list.add(new Excercise(R.drawable.p,"Wypychanie n??g","W pozycji podpartej wykonujemy wypchni??cie n??g do tylu i z powrotem do t??owia"));
        list.add(new Excercise(R.drawable.r,"??wiczenia brzucha","Wykonujemy ??wiczenia brzucha uginaj???? i prostuj???? nogi w powietrzu"));
        list.add(new Excercise(R.drawable.s,"Kr??zenia ramion","Wraz z ci????arem wykonujemy kr??zenia ramion dooko??a ??wiata"));
        list.add(new Excercise(R.drawable.t,"Przysiady","Wykonujemy klasyczne przysiady, trzymaj??c w d??oniach ci????arek"));
        list.add(new Excercise(R.drawable.w,"Przysiad kung-fu","Wykonujemy klasyczny przysiad wraz z uniesieniem jednej z n??g na przemian w stylu kung-fu"));
*/

    }
}