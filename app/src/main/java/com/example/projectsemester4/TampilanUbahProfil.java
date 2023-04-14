package com.example.projectsemester4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TampilanUbahProfil extends AppCompatActivity {
    private TextView tvNim;
    private TextView tvNama;
    private TextView tvProdi;
    private TextView tvNoHp;
    private TextView tvUbahPassword;
    private EditText etNama;
    private EditText etNoHp;
    private Button btnSimpan;

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
        tvUbahPassword = findViewById(R.id.ubah_password);
        etNama = findViewById(R.id.nama_user_profile);
        etNoHp = findViewById(R.id.no_hp);
        btnSimpan = findViewById(R.id.loginButton);

        // Mengatur onClickListener untuk tombol Simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data dari EditText
                String nama = etNama.getText().toString();
                String noHp = etNoHp.getText().toString();

                // Lakukan aksi sesuai dengan data yang diterima
                // Misalnya, simpan data ke database atau lakukan validasi
                // ...

                // Contoh aksi setelah data disimpan
                tvNama.setText("Nama: " + nama);
                tvNoHp.setText("No. Hp: " + noHp);
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
