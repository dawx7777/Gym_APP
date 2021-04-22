package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gym_app.Database.GymDB;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Date;

public class Setting extends AppCompatActivity {

    MaterialButton btnSave;
    RadioButton rdiEasy,rdiMedium, rdiHard;
    RadioGroup rdiGroup;
    ToggleButton switchAlarm;
    TimePicker timePicker;
    GymDB gymDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        btnSave=(MaterialButton)findViewById(R.id.Save);
        rdiEasy=(RadioButton) findViewById(R.id.rdiEasy);
        rdiMedium=(RadioButton) findViewById(R.id.rdiMedium);
        rdiHard=(RadioButton) findViewById(R.id.rdiHard);
        rdiGroup=(RadioGroup) findViewById(R.id.rdiGroup);
        switchAlarm=(ToggleButton) findViewById(R.id.switchAlarm);
        timePicker=(TimePicker) findViewById(R.id.timePicker);
        gymDB=new GymDB(this);

        int mode=gymDB.getSettingMode();
      
        setRadioButton(mode);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                saveAlarm(switchAlarm.isChecked());
                saveWorkoutMode();
                Toast.makeText(Setting.this,"Zapisane",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void createNotificationChanel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name="GymChanel";
            String description="Chanel do ćwiczen";
            int importance=NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel=new NotificationChannel("Channel",name,importance);
            chanel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chanel);
        }
    }



    public void saveAlarm(boolean checked) {
        if(checked){
          createNotificationChanel();

            Intent intent;
            PendingIntent pendingIntent;
            intent =new Intent(Setting.this,AlarmNotificationReceiver.class);
            pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);

            Calendar calendar=Calendar.getInstance();
            Date toDay=Calendar.getInstance().getTime();
            calendar.set(toDay.getYear(),toDay.getMonth(),toDay.getDay(),timePicker.getHour(),timePicker.getMinute());
            AlarmManager manager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            Log.d("DEBUG","Alarm will wake at:" +timePicker.getHour()+":"+timePicker.getMinute());
        }
        else {
            Intent intent = new Intent(Setting.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            AlarmManager manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

            manager.cancel(pendingIntent);

        }

    }

    private void saveWorkoutMode() {
        int selectedID=rdiGroup.getCheckedRadioButtonId();
        if(selectedID==rdiEasy.getId())
            gymDB.saveSettingMode(0);
        else if(selectedID==rdiMedium.getId())
            gymDB.saveSettingMode(1);
        else if(selectedID==rdiHard.getId())
            gymDB.saveSettingMode(2);
    }

    private void setRadioButton(int mode) {

        if(mode==0)
            rdiGroup.check(R.id.rdiEasy);
        else if(mode==1)
            rdiGroup.check(R.id.rdiMedium);
        else if(mode==2)
            rdiGroup.check(R.id.rdiHard);

    }
}