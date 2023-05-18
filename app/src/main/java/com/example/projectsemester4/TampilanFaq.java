package com.example.projectsemester4;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TampilanFaq extends AppCompatActivity {
    ArrayList<FaqModelClass> arrayList;
    RecyclerView recyclerView;
    FaqAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.faq_tampil);
        arrayList = new ArrayList<>();

        arrayList.add(new FaqModelClass(
                "Informasi apa saja yang tersimpan saat membuat akun JTI-Surat?",
                "Saat anda membuat akun JTI-Surat, kami menyimpan informasi yang diberikan, seperti Nama, Nim, Prodi, dan Password.",
                false));
        arrayList.add(new FaqModelClass(
                "Bisakah mendaftarkan akun dengan menggunakan data yang sudah didaftarkan sebelumnya??",
                "Pendaftaran akan gagal jika anda menggunakan data yang sama karena akun anda sudah terdaftar dalam database kami.",
                false));
        arrayList.add(new FaqModelClass(
                "Apa saja yang dapat dilakukan setelah terdaftar di JTI-Surat?",
                "Anda dapat melakukan transaksi pengajuan surat kepada Admin Jurusan Teknologi Informasi.",
                false));
        arrayList.add(new FaqModelClass(
                "Bagaimana jika terdapat kesalahan saat input data?",
                "Anda dapat mengubah kesalahan penginputan anda di menu 'Ubah Akun' di website JTI-Surat.",
                false));
        adapter = new FaqAdapter(arrayList, TampilanFaq.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Mengatur fungsi tombol back pada appbar
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
