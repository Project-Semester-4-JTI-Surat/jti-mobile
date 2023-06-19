package com.example.projectsemester4;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.Prodi;
import com.example.projectsemester4.Keys.UserService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;


public class TampilanLogin extends AppCompatActivity {
    private EditText etNim, etPassword;
    private Button btnLogin;
    boolean passwordVisible;
    private MyPreferences myPreferences;
    private ImageView TampilLogo;
    private Drawable drawable;
    private TextView tvSignUp;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_login);

        etNim = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginButton);

        TampilLogo = findViewById(R.id.TampilLogo);

        tvSignUp = findViewById(R.id.tv_signup);

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
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi saat teks "Sudah Punya Akun? Login Sekarang Juga" ditekan
                Intent intent = new Intent(TampilanLogin.this, TampilanRegister.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
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
                    etNim.setError("NIM tidak valid. (JTI menggunakan prefix 'E' diawal NIM dan 8 angka setelahnya).");
                    etNim.requestFocus();
                    return;
                } else {
                    // Meminta izin penyimpanan eksternal jika belum diberikan
//                    if (isExternalStoragePermissionGranted()) {
                        // Jika izin penyimpanan eksternal sudah diberikan
                        login();
//                    }
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
                        preferences.saveInt("prodi_id", prodi.getId());
                    }
                    preferences.saveString("no_hp", loginRequest.getNoHp());
                    preferences.saveString("token", loginResponse.getData().getToken());
                    ApiClient.setAuthToken(loginResponse.getData().getToken());

                    // Check if user wants to access Android storage
                    AlertDialog.Builder builder = new AlertDialog.Builder(TampilanLogin.this);
                    builder.setTitle("Permission Required");
                    builder.setMessage("Do you want to access Android storage?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User agrees to access Android storage
                            // Save data to CSV
//                          saveDataToCSV(loginRequest);
                            // Check if data already exists in CSV
                            if (checkDataExistsInCSV(loginRequest)) {
                                // Data already exists, overwrite it
                                updateDataInCSV(loginRequest);
                            } else {
                                // Data does not exist, save it to CSV
                                saveDataToCSV(loginRequest);
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    preferences.setLoggedInUser(TampilanLogin.this, loginRequest.getNim());
                                    preferences.setLoggedInStatus(true);
                                    startActivity(new Intent(TampilanLogin.this, MainActivity.class).putExtra("data", loginRequest.getNim()));
                                    finish();
                                }
                            }, 500);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User disagrees to access Android storage
                            // Do nothing
                            if (checkDataExistsInCSV(loginRequest)) {
                                // Data already exists, overwrite it
                                updateDataInCSV(loginRequest);
                            } else {
                                // Data does not exist, save it to CSV
                                saveDataToCSV(loginRequest);
                            }
                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // User cancels the dialog
                            // Do nothing
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(TampilanLogin.this, "NIM / Password Salah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(TampilanLogin.this, "NIM / Password Salah!" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean isExternalStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin penyimpanan eksternal diberikan
                login();
            } else {
                Toast.makeText(this, "Izin Penyimpanan Eksternal Ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean checkDataExistsInCSV(LoginRequest loginRequest) {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv"; // Path to your CSV file

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length >= 1 && nextLine[0].equals(loginRequest.getNim())) {
                    // Data already exists in CSV
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Data does not exist in CSV
        return false;
    }

    private void updateDataInCSV(LoginRequest loginRequest) {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv"; // Path to your CSV file

        try {
            // Read all data from CSV
            List<String[]> allData = new ArrayList<>();
            try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    allData.add(nextLine);
                }
            }

            // Update data for the matching NIM
            for (int i = 0; i < allData.size(); i++) {
                String[] rowData = allData.get(i);
                if (rowData.length >= 1 && rowData[0].equals(loginRequest.getNim())) {
                    rowData[1] = loginRequest.getNama();
                    rowData[2] = String.valueOf(loginRequest.getProdi().getId());
                    rowData[3] = loginRequest.getNoHp();
                    allData.set(i, rowData);
                    break;
                }
            }

            // Write updated data back to CSV
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, false))) {
                writer.writeAll(allData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveDataToCSV(LoginRequest loginRequest) {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv"; // Path to your CSV file
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true));
            String[] data = {loginRequest.getNim(), loginRequest.getNama(), String.valueOf(loginRequest.getProdi().getId()), loginRequest.getNoHp()};
            writer.writeNext(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}