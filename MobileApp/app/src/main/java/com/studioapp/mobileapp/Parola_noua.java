package com.studioapp.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Parola_noua extends AppCompatActivity implements AlertaDialog.AlertaDialogListener {
    private Button Salveaza;
    private ImageButton Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parola_noua);
        Toast.makeText(getApplicationContext(), "Pentru a schimba parola", Toast.LENGTH_SHORT).show();
        Salveaza = (Button) findViewById(R.id.buttonSalveaza);
        Back = findViewById(R.id.btn_back);
        Salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kill_app();
            }
        });
    }
    public void openDialog()
    {
        AlertaDialog dialog=new AlertaDialog();
        dialog.show(getSupportFragmentManager(),"exemplu");
    }
    @Override
    public void onYesClicked()
    {
        kill_app();
    }

    public void kill_app()
    {
        finish();
    }
}
