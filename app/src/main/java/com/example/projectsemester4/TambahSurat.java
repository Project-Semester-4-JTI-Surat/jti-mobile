package com.example.projectsemester4;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TambahSurat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText kepada, alamat, tanggal;
    Spinner spJenisSurat, spNamaDosen, kebutuhan;
    TextView judulSurat, jsSurat, nmDosen, kpd, almt, tgl, kbth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_surat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kepada = findViewById(R.id.kepada);
        alamat = findViewById(R.id.alamat);
        tanggal = findViewById(R.id.tanggal);
        spJenisSurat = findViewById(R.id.spJenisSurat);
        spNamaDosen = findViewById(R.id.spNamaDosen);
        kebutuhan = findViewById(R.id.kebutuhan);
        judulSurat = findViewById(R.id.judulSurat);
        jsSurat = findViewById(R.id.jsSurat);
        nmDosen = findViewById(R.id.nmDosen);
        kpd = findViewById(R.id.kpd);
        almt = findViewById(R.id.almt);
        tgl = findViewById(R.id.tgl);
        kbth = findViewById(R.id.kbth);

        ArrayAdapter<CharSequence> adapterJS = ArrayAdapter.createFromResource(this,
                R.array.jenis_surat, android.R.layout.simple_spinner_item);
        adapterJS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenisSurat.setAdapter(adapterJS);

        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
                R.array.nama_dosen, android.R.layout.simple_spinner_item);
        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNamaDosen.setAdapter(adapterND);

        ArrayAdapter<CharSequence> adapterKebutuhan = ArrayAdapter.createFromResource(this,
                R.array.kebutuhan, android.R.layout.simple_spinner_item);
        adapterKebutuhan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kebutuhan.setAdapter(adapterKebutuhan);

        spJenisSurat.setOnItemSelectedListener(this);
        spNamaDosen.setOnItemSelectedListener(this);
        kebutuhan.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
