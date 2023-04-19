package com.example.projectsemester4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TampilanUbahProfil extends AppCompatActivity {
    private TextView tvNim;
    private EditText tvNama;
    private TextInputLayout tvProdi;
    private EditText tvNoHp;
    private TextView tvUbahPassword;
    private Button btnSimpan;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_ubah_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inisialisasi view
        tvNim = findViewById(R.id.tampil_nim);
        tvNama = findViewById(R.id.tampil_nama_user);
        tvProdi = findViewById(R.id.tampil_prodi);
        tvNoHp = findViewById(R.id.tampil_no_hp);
        tvUbahPassword = findViewById(R.id.ubah_password);
        btnSimpan = findViewById(R.id.loginButton);

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Ambil data pengguna dari SharedPreferences
        String nim = sharedPreferences.getString("nim", "");
        if (!TextUtils.isEmpty(nim)) {
            LoginRequest loginRequest = new LoginRequest();
            Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        loginResponse.setNim(nim);
                        loginResponse.setNama(sharedPreferences.getString("nama", ""));
                        loginResponse.setProdi_id(sharedPreferences.getInt("prodi", 0));
                        loginResponse.setNo_hp(sharedPreferences.getString("no_hp", ""));

                        // Tampilkan data pengguna pada TextView dan EditText
                        tvNim.setText(loginResponse.getNim());
                        tvNama.setText(loginResponse.getNama());
                        tvProdi.getEditText().setText(String.valueOf(loginResponse.getProdi_id()));
                        tvNoHp.setText(loginResponse.getNo_hp());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(TampilanUbahProfil.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        // Mengatur onClickListener untuk tombol Simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Ambil data dari EditText
//                String nama = etNama.getText().toString();
//                String noHp = etNoHp.getText().toString();
//
//                // Lakukan aksi sesuai dengan data yang diterima
//                // Misalnya, simpan data ke database atau lakukan validasi
//                // ...
//
//                // Contoh aksi setelah data disimpan
//                tvNama.setText("Nama: " + nama);
//                tvNoHp.setText("No. Hp: " + noHp);
            }
        });
    }

    // Metode untuk mengatur aksi saat teks "Ubah Password?" diklik
    public void onTextViewClick(View view) {
        Intent intent = new Intent(TampilanUbahProfil.this, TampilanUbahPassword.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Mengatur fungsi tombol back pada appbar
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
