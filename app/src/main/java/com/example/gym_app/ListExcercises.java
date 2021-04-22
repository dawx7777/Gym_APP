package com.example.gym_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.gym_app.Adapter.RecyclerViewAdapter;
import com.example.gym_app.Model.Excercise;

import java.util.ArrayList;
import java.util.List;

public class ListExcercises extends AppCompatActivity {


    List<Excercise> excerciseList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list_excercises);
    initData();

    recyclerView=(RecyclerView) findViewById(R.id.list_ex);
    adapter=new RecyclerViewAdapter(excerciseList,getBaseContext());

    layoutManager=new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    }
    private void initData(){

        excerciseList.add(new Excercise(R.drawable.a,"Ćwiczenia barków z obciążeniem","Ćwiczenia barków wykonujemy wraz z obciążeniem  najlepiej użyc do tego hantli , lub w warunkach domowych butelki z wodą"));
        excerciseList.add(new Excercise(R.drawable.b,"Ćwiczenia brzucha","Leżac na podłodze unosimy nogi do kąta prostego, następnie wykonujemy brzuszki utrzymując równowagę ciała"));
        excerciseList.add(new Excercise(R.drawable.c,"Ćwiczenia barków ","Wykonujemy ćwiczenia na barki wraz z obciązeniem, prostujać ręce, przytrzymując chwilę na wyproscie"));
        excerciseList.add(new Excercise(R.drawable.d,"Przysiady z obciążeniem","Wykonujemy przysiady z ciężarem wraz z unoszeniem barków do góry"));
        excerciseList.add(new Excercise(R.drawable.e,"Wiosłowanie","Wiosłowanie wykonujemy przy lekkim skłonie, ważne aby plecy były wlini prostej. Hantle wiosłujemy do lini prostej"));
        excerciseList.add(new Excercise(R.drawable.f,"Pompka z podskokiem","Wykonujemy klasyczną pompkę wraz z podskokiem angażując stawy skokowe"));
        excerciseList.add(new Excercise(R.drawable.g,"Wykroki z obciązeniem na stopniu","W warukach domowych używając krzesła, wykonujemy wykroki nożne"));
        excerciseList.add(new Excercise(R.drawable.h,"Pompki z przedmiotem","Wykonujemy pompki, przy górnej pozycji wychylamy na przemian ręke w prawo i lewo"));
        excerciseList.add(new Excercise(R.drawable.i,"Pajacyki","Wykonujemy klasyczne pajacyki anagażujące całe ciało"));
        excerciseList.add(new Excercise(R.drawable.j,"Skręty boczne","W siadzie na podłodze wykoujemy skręty boczne wraz z obciążeniem"));
        excerciseList.add(new Excercise(R.drawable.k,"Wykroki tylne","Używając krzesła wykonujemy wykroki tylne angażujące mięśnie nóg"));
        excerciseList.add(new Excercise(R.drawable.l,"Deska","Wykonujemy klasyczną deskę co chwilę unosząc na przemian ręce"));
        excerciseList.add(new Excercise(R.drawable.m,"Pompki","Wykonujemy klasyczne pompki w określonym czasie"));
        excerciseList.add(new Excercise(R.drawable.n,"Unoszenie tłowia","Będąc w pozycji leżacej na brzuchu unosimy ręce i nogi"));
        excerciseList.add(new Excercise(R.drawable.o,"Podskoki","Wykonujemy podskoki do przysiadu"));
        excerciseList.add(new Excercise(R.drawable.p,"Wypychanie nóg","W pozycji podpartej wykonujemy wypchnięcie nóg do tylu i z powrotem do tłowia"));
        excerciseList.add(new Excercise(R.drawable.r,"Ćwiczenia brzucha","Wykonujemy ćwiczenia brzucha uginająć i prostująć nogi w powietrzu"));
        excerciseList.add(new Excercise(R.drawable.s,"Krązenia ramion","Wraz z ciężarem wykonujemy krązenia ramion dookoła świata"));
        excerciseList.add(new Excercise(R.drawable.t,"Przysiady","Wykonujemy klasyczne przysiady, trzymając w dłoniach ciężarek"));
        excerciseList.add(new Excercise(R.drawable.w,"Przysiad kung-fu","Wykonujemy klasyczny przysiad wraz z uniesieniem jednej z nóg na przemian w stylu kung-fu"));


    }
}