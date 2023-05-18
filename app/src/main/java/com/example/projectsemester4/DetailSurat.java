package com.example.projectsemester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailSurat extends AppCompatActivity {
    private TextView txJenisSurat;
    private TextView dtNamaDosen;
    private TextView isiNamaDosen;
    private TextView dtNamaMitra;
    private TextView isiNamaMitra;
    private TextView dtAlamatMitra;
    private TextView isiAlamatMitra;
    private TextView dtTanggalPenggunaan;
    private TextView isiTanggalPenggunaan;
    private TextView dtKeterangan;
    private TextView isiKeterangan;
    private TextView dtStatus;
    private TextView isiStatus;
    private TextView txDetailPengaju;
    private TextView txNamaPengaju;
    private TextView isiNamaPengaju;
    private TextView txNimPengaju;
    private TextView isiNimPengaju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_surat_activity);

        // Inisialisasi TextView dengan ID dari layout XML
        txJenisSurat = findViewById(R.id.tx_jenisSurat);
        dtNamaDosen = findViewById(R.id.dt_namaDosen);
        isiNamaDosen = findViewById(R.id.isi_namaDosen);
        dtNamaMitra = findViewById(R.id.dt_namaMitra);
        isiNamaMitra = findViewById(R.id.isi_namaMitra);
        dtAlamatMitra = findViewById(R.id.dt_alamatMitra);
        isiAlamatMitra = findViewById(R.id.isi_alamatMitra);
        dtTanggalPenggunaan = findViewById(R.id.dt_tanggalPenggunaan);
        isiTanggalPenggunaan = findViewById(R.id.isi_tanggalPenggunaan);
        dtKeterangan = findViewById(R.id.dt_keterangan);
        isiKeterangan = findViewById(R.id.isi_keterangan);
        dtStatus = findViewById(R.id.dt_status);
        isiStatus = findViewById(R.id.isi_status);
        txDetailPengaju = findViewById(R.id.tx_detailPengaju);
        txNamaPengaju = findViewById(R.id.tx_namaPengaju);
        isiNamaPengaju = findViewById(R.id.isi_namaPengaju);
        txNimPengaju = findViewById(R.id.tx_nimPengaju);
        isiNimPengaju = findViewById(R.id.isi_nimPengaju);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the intent that started this activity
        Intent intent = getIntent();

        // get the data from the intent
        String mataKuliah = intent.getStringExtra("mata_kuliah");
        String namaMhs = intent.getStringExtra("nama_mhs");

        txJenisSurat.setText(mataKuliah);
        isiNamaPengaju.setText(namaMhs);
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