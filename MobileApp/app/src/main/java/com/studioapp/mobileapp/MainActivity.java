package com.studioapp.mobileapp;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    startLogin();
                }
            }, 3000);
        }
        finally {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    finish();
                }
            }, 3000);
        }

    }


    public void startLogin()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

}




