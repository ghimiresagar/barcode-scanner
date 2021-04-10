package com.example.scanner;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide the initial heading bar
        getSupportActionBar().hide();
        // create a new handler to handle post delayed for splash screen
        Handler handler = new Handler();
        // delay the screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toHome = new Intent(MainActivity.this, Home.class);
                MainActivity.this.startActivity(toHome);
                MainActivity.this.finish();
            }
        }, 3000);
    }

}