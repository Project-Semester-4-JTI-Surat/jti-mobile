package com.example.projectsemester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailSurat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_surat_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the intent that started this activity
        Intent intent = getIntent();

        // get the data from the intent
        String mataKuliah = intent.getStringExtra("mata_kuliah");
        String namaMhs = intent.getStringExtra("nama_mhs");

        // set the data to the corresponding TextViews
        TextView tvMataKuliah = findViewById(R.id.tv_detail_mata_kuliah);
        TextView tvNamaMhs = findViewById(R.id.tv_detail_nama_mhs);

        tvMataKuliah.setText(mataKuliah);
        tvNamaMhs.setText(namaMhs);
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