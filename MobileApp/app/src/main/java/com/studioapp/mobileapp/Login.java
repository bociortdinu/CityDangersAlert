package com.studioapp.mobileapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText Email;
    private EditText Parola;
    private TextView Inregistrare;
    private TextView Uita_parola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email=(EditText)findViewById(R.id.emailLogin);
        Parola=(EditText)findViewById(R.id.parolaLogin);
        btnLogin = (Button)findViewById(R.id.buttonSalveaza);
        Inregistrare=(TextView)findViewById(R.id.id_inreg);
        Uita_parola=(TextView)findViewById(R.id.id_uitat);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMeniu();
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(Email.getText().toString(), Parola.getText().toString()))
                    finish();
            }
        });
        Inregistrare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Inregistrare.class);
                startActivity(intent);
            }
        });
        Uita_parola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Parola_noua.class);
                startActivity(intent);
            }
        });

    }


    private boolean validate(String userEmail, String userParola)
    {
        if((userEmail.equals("bociortdinu@gmail.com")) && (userParola.equals("admin")))
        {
            startMeniuActivity();
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "Email sau parola incorecta!Mai incercati o data!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    private void startMeniuActivity()
    {
        Intent intent = new Intent(this,Meniu.class);
        startActivity(intent);
        try {
            Profil.getInstance().extractProfilData(Email.getText().toString());    // daca emailul cu care te-ai logat se potriveste cu cel din baza de date atunci extrag datele
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
