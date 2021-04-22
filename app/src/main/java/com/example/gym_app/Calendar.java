package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.WindowManager;
import android.widget.CalendarView;

import com.example.gym_app.Custom.WorkoutDoneDecorator;
import com.example.gym_app.Database.GymDB;
import com.example.gym_app.Database.GymmmDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Calendar extends AppCompatActivity {

    MaterialCalendarView calendarView;
    HashSet<CalendarDay> list=new HashSet<>();
    GymmmDB gymmmDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gymmmDB=new GymmmDB(this);
        calendarView=(MaterialCalendarView)findViewById(R.id.calendar);

        List<String> workoutDay=gymmmDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList=new HashSet<>();
        for(String value:workoutDay)
        convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        calendarView.addDecorator(new WorkoutDoneDecorator(convertedList));


    }
}