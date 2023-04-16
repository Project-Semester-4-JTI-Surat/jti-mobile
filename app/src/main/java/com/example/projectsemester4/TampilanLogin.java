package com.example.projectsemester4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class TampilanLogin extends AppCompatActivity {

    EditText nim;
    EditText password;
    Button loginButton;
    boolean passwordVisible;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_login);

        nim = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        sharedPreferences = getSharedPreferences("login_data", Context.MODE_PRIVATE);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            // Set drawable image
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.toggle_password, 0);
                            // For hide password
                            password.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            // Set drawable image
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.toggle_password_on, 0);
                            // For show password
                            password.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nimValue = nim.getText().toString();
                String passwordValue = password.getText().toString();

                // Check if NIM and password are not empty
                if (!nimValue.isEmpty() && !passwordValue.isEmpty()) {
                    // Validate login credentials (You can replace this with your own logic)
                    if (nimValue.equals("e41210766") && passwordValue.equals("akbar123")) {
                        // Save login status to Shared Preferences
                        SharedPreferences sharedPreferences = getSharedPreferences("login_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("e41210766", nimValue); // Ganti "nama" dengan key yang sesuai
                        editor.putString("akbar123", passwordValue); // Ganti "nim" dengan key yang sesuai
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        // Redirect to home screen or main activity
                        // Replace this with your own intent
                        Toast.makeText(TampilanLogin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TampilanLogin.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TampilanLogin.this, "Invalid NIM or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TampilanLogin.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

