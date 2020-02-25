package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerView extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        ArrayList<String> listOfNames = new ArrayList<>();
        androidx.recyclerview.widget.RecyclerView names = findViewById(R.id.nameRec);
        listOfNames.add("Lucky Abaasa");
        listOfNames.add("Kyasimire Jennifer");
        listOfNames.add("Amony Sandra");
        listOfNames.add("Kanoel Daphine");

        names.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, listOfNames);
        adapter.setClickListener(this);
        names.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, " " + adapter.getItem(position) , Toast.LENGTH_SHORT).show();
    }
}
