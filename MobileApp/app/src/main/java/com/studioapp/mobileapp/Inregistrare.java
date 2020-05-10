package com.studioapp.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Inregistrare extends AppCompatActivity {
    private Button Salveaza;
    private ImageButton Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrare);
        Toast.makeText(getApplicationContext(), "Inregistrare",Toast.LENGTH_SHORT).show();
        Salveaza=(Button)findViewById(R.id.buttonSalveaza);
        Back = findViewById(R.id.btn_back);
        Salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kill_app();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kill_app();
            }
        });
    }


    public void kill_app()
    {
        finish();
    }


}
