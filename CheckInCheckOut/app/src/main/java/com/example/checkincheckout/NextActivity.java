package com.example.checkincheckout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class NextActivity extends AppCompatActivity {
    Handler h = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        h.postDelayed(() -> {
            Intent i = new Intent(NextActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        },2500);
    }
}