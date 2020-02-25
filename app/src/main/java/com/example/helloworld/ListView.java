package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ArrayList<String> listOfNames = new ArrayList<>();
        android.widget.ListView names = findViewById(R.id.namelist);
        listOfNames.add("Lucky Abaasa");
        listOfNames.add("Kyasimire Jennifer");
        listOfNames.add("Amony Sandra");
        listOfNames.add("Kanoel Daphine");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfNames);
        names.setAdapter(arrayAdapter);

    }
}
