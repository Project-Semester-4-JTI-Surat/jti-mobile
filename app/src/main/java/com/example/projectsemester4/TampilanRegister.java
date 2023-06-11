package com.example.projectsemester4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.Prodi;
import com.example.projectsemester4.Keys.ProdiResponse;
import com.example.projectsemester4.Keys.RegisterRequest;
import com.example.projectsemester4.Keys.RegisterResponse;
import com.example.projectsemester4.Keys.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilanRegister extends AppCompatActivity {
    private EditText etEmail, etNim, etNama, etAlamat, etTanggalLahir, etNoHp, etPassword;
    private Spinner spProdi;
    private Button registerButton;
    private TextView tvSignIn;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_register);

        // Inisialisasi komponen UI
        etEmail = findViewById(R.id.et_email);
        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etTanggalLahir = findViewById(R.id.et_tanggal_lahir);
        etNoHp = findViewById(R.id.tampil_no_hp);
        etPassword = findViewById(R.id.et_password);
        spProdi = findViewById(R.id.sp_prodi);
        registerButton = findViewById(R.id.loginButton);
        tvSignIn = findViewById(R.id.tv_signin);

        // Implementasi logika dan event handling di sini
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi saat tombol Register ditekan
                registerUser();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi saat teks "Sudah Punya Akun? Login Sekarang Juga" ditekan
                Intent intent = new Intent(TampilanRegister.this, TampilanLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        etTanggalLahir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TampilanRegister.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                                etTanggalLahir.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        getDataProdi();
    }

    private void getDataProdi() {
        UserService apiService = ApiClient.getUserService(this);
        // Membuat panggilan ke API
        Call<ProdiResponse> call = apiService.getDataProdi();
        call.enqueue(new Callback<ProdiResponse>() {
            @Override
            public void onResponse(Call<ProdiResponse> call, Response<ProdiResponse> response) {
                if (response.isSuccessful()) {
                    ProdiResponse prodiResponse = response.body();
                    if (prodiResponse != null && prodiResponse.isSuccess()) {
                        List<Prodi> prodiList = prodiResponse.getData();

                        // Mengambil data keterangan saja
                        List<String> keteranganList = new ArrayList<>();
                        for (Prodi prodi : prodiList) {
                            if (!"-".equals(prodi.getKeterangan())) {
                                keteranganList.add(prodi.getKeterangan());
                            }
                        }

                        // Mengatur adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TampilanRegister.this,
                                android.R.layout.simple_spinner_item, keteranganList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spProdi.setAdapter(adapter);
                    } else {
                        // Tangani respons sukses tetapi pesan kesalahan dari API
                        String message = prodiResponse != null ? prodiResponse.getMessage() : "Unknown error";
                        Toast.makeText(TampilanRegister.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani respons gagal dari API
                    System.out.println("Error "+response.raw());
                    Toast.makeText(TampilanRegister.this, "Gagal Untuk Mengambil Data Prodi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdiResponse> call, Throwable t) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                Toast.makeText(TampilanRegister.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String nim = etNim.getText().toString().trim();
        String nama = etNama.getText().toString().trim();
        String prodiId = getSelectedProdiId();
        String alamat = etAlamat.getText().toString().trim();
        String tanggalLahir = etTanggalLahir.getText().toString().trim();
        String noHp = etNoHp.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi input
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(nim) || TextUtils.isEmpty(nama)
                || TextUtils.isEmpty(prodiId) || TextUtils.isEmpty(alamat)
                || TextUtils.isEmpty(tanggalLahir) || TextUtils.isEmpty(noHp)
                || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setNim(nim);
        registerRequest.setNama(nama);
        registerRequest.setProdi(prodiId);
        registerRequest.setAlamat(alamat);
        registerRequest.setTanggal_lahir(tanggalLahir);
        registerRequest.setNo_hp(noHp);
        registerRequest.setPassword(password);

        UserService apiService = ApiClient.getUserService(TampilanRegister.this);
        Call<RegisterResponse> call = apiService.userRegister(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse != null && registerResponse.isSuccess()) {
                        // Registrasi berhasil
                        Toast.makeText(TampilanRegister.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                        // Lakukan tindakan setelah berhasil mendaftar, misalnya pindah ke halaman login
                        Intent intent = new Intent(TampilanRegister.this, TampilanLogin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        // Registrasi gagal, tangani pesan kesalahan dari API
                        String message = registerResponse != null ? registerResponse.getMessage() : "Unknown error";
                        Toast.makeText(TampilanRegister.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani respons gagal dari API
                    System.err.println(response.toString());
                    Toast.makeText(TampilanRegister.this, "Gagal melakukan registrasi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                Toast.makeText(TampilanRegister.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getSelectedProdiId() {
        String selectedProdi = spProdi.getSelectedItem().toString();
        // Parsing prodi_id dari string yang dipilih
        String[] parts = selectedProdi.split("-");
        return parts[0];
    }
}

