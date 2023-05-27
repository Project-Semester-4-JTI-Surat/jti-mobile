package com.example.projectsemester4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.Prodi;
import com.example.projectsemester4.Keys.UserService;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TampilanLogin extends AppCompatActivity {
    private EditText etNim, etPassword;
    private Button btnLogin;
    boolean passwordVisible;
    private MyPreferences myPreferences;
    private ImageView TampilLogo;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_login);

        etNim = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginButton);

        TampilLogo = findViewById(R.id.TampilLogo);

        myPreferences = new MyPreferences(this);
        // Mengatur status login ke false pada awalnya
        myPreferences.setLoggedInStatus(false);

        // Beralih ke MainActivity jika pengguna sudah login sebelumnya
        if (myPreferences.getLoggedInStatus()) {
            Intent intent = new Intent(TampilanLogin.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        etPassword.setOnEditorActionListener((v, actionId, event)-> {
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                login();
                return true;
            }
            return false;
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etNim.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(TampilanLogin.this, "Mohon Isi Semua Kolom", Toast.LENGTH_LONG).show();
                } else if (!etNim.getText().toString().trim().matches("E\\d{8}")) {
                    etNim.setError("NIM tidak valid. (JTI menggunakan prefix 'E' diawal NIM).");
                    etNim.requestFocus();
                    return;
                } else {
                    //proceed to login
                    login();
                }

            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= etPassword.getRight() - etPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = etPassword.getSelectionEnd();
                        if (passwordVisible) {
                            // Set drawable image
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.toggle_password, 0);
                            // For hide password
                            etPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            // Set drawable image
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.toggle_password_on, 0);
                            // For show password
                            etPassword.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        etPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNim(etNim.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService(TampilanLogin.this).userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TampilanLogin.this, "Berhasil Login", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();

                    // Save data to MyPreferences
                    MyPreferences preferences = new MyPreferences(TampilanLogin.this);
                    LoginRequest loginRequest = loginResponse.getData().getUser();
                    preferences.saveString("nim", loginRequest.getNim());
                    preferences.saveString("nama", loginRequest.getNama());
                    Prodi prodi = loginRequest.getProdi();
                    if (prodi != null) {
                        preferences.saveString("prodi", prodi.getKeterangan());
                    }
                    preferences.saveString("no_hp", loginRequest.getNoHp());
                    preferences.saveString("token", loginResponse.getData().getToken());

                    // Save data to CSV
                    saveDataToCSV(loginRequest);

                    ApiClient.setAuthToken(loginResponse.getData().getToken());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            preferences.setLoggedInUser(TampilanLogin.this, loginRequest.getNim());
                            preferences.setLoggedInStatus(true);
                            startActivity(new Intent(TampilanLogin.this, MainActivity.class).putExtra("data", loginRequest.getNim()));
                            finish();
                        }
                    }, 500);

                } else {
                    Toast.makeText(TampilanLogin.this, "NIM / Password Salah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(TampilanLogin.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void saveDataToCSV(LoginRequest loginRequest) {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv"; // Path to your CSV file
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true));
            String[] data = {loginRequest.getNim(), loginRequest.getNama(), loginRequest.getProdi().getKeterangan(), loginRequest.getNoHp()};
            writer.writeNext(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


