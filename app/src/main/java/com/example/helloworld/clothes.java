package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class clothes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        MediaPlayer mediaPlayer = MediaPlayer.create(clothes.this,R.raw.theway);
        mediaPlayer.start();
    }
}
