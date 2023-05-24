package com.example.projectsemester4;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.Prodi;
import com.example.projectsemester4.Keys.UserService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TampilanUbahProfil extends AppCompatActivity {
    private TextView tvNim, tvNama, tvProdi, tvNoHp;
    private EditText etNama;
    private EditText tampilProdi;
    private EditText etNoHp;
    private TextView tvUbahPassword;
    private Button btnSimpan;
//    private MyPreferences myPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_ubah_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inisialisasi view
        tvNim = findViewById(R.id.tampil_nim);
        tvNama = findViewById(R.id.tv_nama);
        tvProdi = findViewById(R.id.tv_prodi);
        tvNoHp = findViewById(R.id.tv_no_hp);
        etNama = findViewById(R.id.tampil_nama_user);
        tampilProdi = findViewById(R.id.tampil_prodi);
        etNoHp = findViewById(R.id.tampil_no_hp);
        tvUbahPassword = findViewById(R.id.ubah_password);
        btnSimpan = findViewById(R.id.loginButton);
//        myPreferences = new MyPreferences(this);

//        token = myPreferences.getToken();

        getDataUser();
        etNama.setEnabled(false);
        tampilProdi.setEnabled(false);
//        getDataProdi();

//        btnSimpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateUserProfile();
//            }
//        });

        tvUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilanUbahProfil.this, TampilanUbahPassword.class);
                startActivity(intent);
            }
        });
    }

//    private void getDataProdi() {
//        UserService apiService = ApiClient.getUserService(this);
//        Call<List<Prodi>> call = apiService.getDataProdi();
//        call.enqueue(new Callback<List<Prodi>>() {
//            @Override
//            public void onResponse(Call<List<Prodi>> call, Response<List<Prodi>> response) {
//                if (response.isSuccessful()) {
//                    List<Prodi> prodiList = response.body();
//                    if (prodiList != null) {
//                        // Buat adapter untuk Spinner
//                        ArrayAdapter<Prodi> adapter = new ArrayAdapter<>(TampilanUbahProfil.this, android.R.layout.simple_spinner_item, prodiList);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                        // Dapatkan referensi ke Spinner dari layout XML
//                        Spinner spProdi = findViewById(R.id.spProdi);
//
//                        // Set adapter ke Spinner
//                        spProdi.setAdapter(adapter);
//                    }
//                } else {
//                    Toast.makeText(TampilanUbahProfil.this, "Gagal mengambil data prodi", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Prodi>> call, Throwable t) {
//                Toast.makeText(TampilanUbahProfil.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void getDataUser() {
        // Mendapatkan token dari penyimpanan data (misalnya, SharedPreferences)
        MyPreferences preferences = new MyPreferences(this);
        String token = preferences.getString("token", "");

        // Membuat instance layanan dengan token yang diberikan
        UserService apiService = ApiClient.getUserService(this);
        Call<LoginResponse> call = apiService.getUserProfile("Bearer " + token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Tangani respons berhasil
                    LoginResponse loginResponse = response.body();
                    LoginRequest loginRequest = loginResponse.getData().getUser();

                    // Tampilkan data pengguna pada tampilan yang sesuai
                    tvNim.setText(loginRequest.getNim());
                    etNama.setText(loginRequest.getNama());
                    tampilProdi.setText(loginRequest.getProdi().getKeterangan());
//                    // Periksa apakah tilProdi tidak null sebelum mengakses EditText-nya
//                    if (tilProdi != null) {
//                        EditText etProdi = tilProdi.getEditText();
//                        if (etProdi != null) {
//                            etProdi.setText(loginRequest.getProdi().getKeterangan());
//                        }
//                    }
//                    // Dapatkan referensi ke Spinner dari layout XML
//                    Spinner spProdi = findViewById(R.id.spProdi);
//
//                    // Buat daftar string untuk menyimpan keterangan prodi
//                    List<String> prodiList = new ArrayList<>();
//
//                    Object prodiResponse = loginRequest.getProdi();
//
//                    if (prodiResponse instanceof List<?>) {
//                        List<Prodi> prodiListResponse = (List<Prodi>) prodiResponse;
//                        for (Prodi prodi : prodiListResponse) {
//                            prodiList.add(prodi.getKeterangan());
//                        }
//                    }
//
//                    // Buat adapter untuk Spinner
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TampilanUbahProfil.this, android.R.layout.simple_spinner_item, prodiList);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                    // Set adapter ke Spinner
//                    spProdi.setAdapter(adapter);
                    etNoHp.setText(loginRequest.getNoHp());

                } else {
                    // Tangani respons gagal
                    Toast.makeText(TampilanUbahProfil.this, "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
                    Log.e("Data User", "Gagal mengambil data user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Tangani kegagalan koneksi ke server
                Toast.makeText(TampilanUbahProfil.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }




//    private void updateUserProfile() {
//        String nama = etNama.getText().toString();
//        String noHp = etNoHp.getText().toString();
//
//        UserService apiService = ApiClient.getUserService(this);
//        Call<LoginResponse> call = apiService.updateUserProfile("Bearer " + token, new UserProfileUpdateRequest(nama, noHp));
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.isSuccessful()) {
//                    LoginResponse loginResponse = response.body();
//                    LoginRequest user = loginResponse.getUser();
//
//                    tvNama.setText("Nama: " + user.getNama());
//                    tvNoHp.setText("No. Hp: " + user.getNoHp());
//                    Toast.makeText(TampilanUbahProfil.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(TampilanUbahProfil.this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(TampilanUbahProfil.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


