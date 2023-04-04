package com.example.projectsemester4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class TampilanLogin extends AppCompatActivity{
    EditText nim;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_login);
        nim = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nim.getText().toString().equals("user") && password.getText().toString().equals("user")) {
                    Toast.makeText(TampilanLogin.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TampilanLogin.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TampilanLogin.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

