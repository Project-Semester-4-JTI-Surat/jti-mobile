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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.UserService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TampilanUbahProfil extends AppCompatActivity {
    private TextView tvNim,tvNama,tvProdi,tvNoHp;
    private EditText etNama;
    private TextInputLayout tilProdi;
    private EditText etNoHp;
    private TextView tvUbahPassword;
    private Button btnSimpan;
    private MyPreferences myPreferences;
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
        tilProdi = findViewById(R.id.tampil_prodi);
        etNoHp = findViewById(R.id.tampil_no_hp);
        tvUbahPassword = findViewById(R.id.ubah_password);
        btnSimpan = findViewById(R.id.loginButton);
        myPreferences = new MyPreferences(this);

        token = myPreferences.getString("token", "");

        getDataUser();
//        // Membuat instance dari UserService
//        UserService userService = ApiClient.getUserProfiles(this);
//
//        // Memanggil API untuk mendapatkan data user
//        Call<LoginResponse> call = userService.getUserProfile("Bearer " + token);
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.isSuccessful()) {
//                    LoginResponse user = response.body();
//
//                    // Menampilkan data user di TextView atau widget lainnya
//                    tvNim.setText(user.getNim());
//                    etNama.setText(user.getNama());
//                    tilProdi.getEditText().setText(user.getProdi_id());
//                    etNoHp.setText(user.getNo_hp());
//                } else {
//                    // Menampilkan pesan error jika terjadi kesalahan pada server
////                    Log.d(TAG, "onResponse: ");
//                    Toast.makeText(TampilanUbahProfil.this, "Error Response : " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                // Menampilkan pesan error jika terjadi kesalahan jaringan
//                Toast.makeText(TampilanUbahProfil.this, "Error Network : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

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

    // Method untuk mengambil data user dari server menggunakan Retrofit
    private void getDataUser() {
        UserService apiService = ApiClient.getRetrofit(this).create(UserService.class);
        Call<LoginResponse> call = apiService.getUserProfile("Bearer " + token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Mendapatkan data user dari response
                    String nim = response.body().getNim();
                    String nama = response.body().getNama();
                    String prodi = String.valueOf(response.body().getProdi_id());
                    String noHp = response.body().getNo_hp();

                    // Menampilkan data user ke dalam view
                    tvNim.setText(nim);
                    etNama.setText(nama);
                    tilProdi.getEditText().setText(prodi);
                    etNoHp.setText(noHp);
                } else {
                    // Menampilkan pesan error jika terjadi kesalahan pada server
                    Toast.makeText(TampilanUbahProfil.this, "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Menampilkan pesan error jika terjadi kesalahan pada jaringan
                Toast.makeText(TampilanUbahProfil.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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
