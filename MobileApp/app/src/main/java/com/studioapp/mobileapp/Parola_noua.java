package com.studioapp.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Parola_noua extends AppCompatActivity implements AlertaDialog.AlertaDialogListener {
    private Button Salveaza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parola_noua);
        Toast.makeText(getApplicationContext(), "Pentru a schimba parola", Toast.LENGTH_SHORT).show();
        Salveaza = (Button) findViewById(R.id.buttonSalveaza);
        Salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

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
        finish();
    }
}
