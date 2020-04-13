package com.studioapp.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class EditareProfil extends AppCompatActivity {

    private Button btnSalvare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editare_profil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        btnSalvare = (Button) findViewById(R.id.buttonSalvareProfil);
        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salveazaProfil();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar1_menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings_back:
                kill_app();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void kill_app()
    {
        finish();
    }

    private void salveazaProfil()
    {


        EditText editText;

        editText = (EditText) findViewById(R.id.editNume);
        if (editText.getText().toString()!="")
            Profil.getInstance().setNumeUtilizator(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editPrenume);
        if (editText.getText().toString()!="")
            Profil.getInstance().setPrenumeUtilizator(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editAdresa);
        if (editText.getText().toString()!="")
            Profil.getInstance().setAdresa(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editEmail);
        if (editText.getText().toString()!="")
            Profil.getInstance().setAdresaEmail(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editTelefon);
        if (editText.getText().toString()!="")
            Profil.getInstance().setNrTelefon(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editLocDeMunca);
        if (editText.getText().toString()!="")
            Profil.getInstance().setLocDeMunca(editText.getText().toString());

        Profil.getInstance().updateProfil();

    }
}
