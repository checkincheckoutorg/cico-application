package com.example.checkincheckout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
    Handler h = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, NextActivity.class);
                startActivity(i);
                finish();
            }
        },2500);
    }
}