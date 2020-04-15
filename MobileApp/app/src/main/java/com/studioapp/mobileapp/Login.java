package com.studioapp.mobileapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText Email;
    private EditText Parola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email=(EditText)findViewById(R.id.emailLogin);
        Parola=(EditText)findViewById(R.id.parolaLogin);
        btnLogin = (Button)findViewById(R.id.buttonSalvareProfil);
        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMeniu();
            }
        });*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Email.getText().toString(), Parola.getText().toString());
            }
        });



    }

    private void openMeniu(){
        //Intent intent = new Intent(this , Meniu.class);
        //startActivity(intent);
    }
    private void validate(String userEmail, String userParola)
    {
        if((userEmail.equals("admin")) && (userParola.equals("admin")))
        {
            Intent intent = new Intent(this, Meniu.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Email sau parola incorecta!Mai incercati o data!",Toast.LENGTH_SHORT).show();
        }
    }
}
