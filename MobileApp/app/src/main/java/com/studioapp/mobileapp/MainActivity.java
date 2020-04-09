package com.studioapp.mobileapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {

            Thread.sleep(5000);
            Intent intetn = new Intent(this, Login.class);
            startActivity(intetn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}




