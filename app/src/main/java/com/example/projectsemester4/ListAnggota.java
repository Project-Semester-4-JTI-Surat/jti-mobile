package com.example.projectsemester4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectsemester4.Keys.AnggotaAdapter;
import com.example.projectsemester4.Keys.AnggotaModel;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListAnggota extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutList;
    private EditText nimAngggota, namaAnggota, tlpAnggota;
    private AppCompatSpinner spProdi;
    private Button tambah, simpan;
    private ImageView hapus;
//    private ArrayList<Anggota> anggotaList = new ArrayList<>();
    private List<AnggotaModel> anggotaList; // list untuk menyimpan data anggota
    private RecyclerView recyclersView; // RecyclerView untuk menampilkan data
    private AnggotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_anggota);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutList = findViewById(R.id.layout_list);
        tambah = findViewById(R.id.tambah);
        simpan = findViewById(R.id.btSimpan);

        tambah.setOnClickListener(this);
        simpan.setOnClickListener(this);

        if (layoutList==null){
            simpan.setEnabled(true);
        }else{
            simpan.setEnabled(false);
        }
//        recyclersView = findViewById(R.id.recycle_data);
//        recyclersView.setLayoutManager(new LinearLayoutManager(this));
//        View anotherLayout = getLayoutInflater().inflate(R.layout.tambah_surat, null);

//        anggotaList = new ArrayList<>(); // inisialisasi list anggota
//        adapter = new AnggotaAdapter(anggotaList); // Inisialisasi adapter RecyclerView
//        simpan.setOnClickListener(this);

//        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
//                R.array.prodi, android.R.layout.simple_spinner_item);
//        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spProdi.setAdapter(adapterND);
//        spProdi.setOnClickListener(this);
    }
    public void simpanData() {
        // Mendapatkan path penyimpanan file .csv
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";

        try {
            // Membuat objek FileWriter untuk menulis ke file .csv
            FileWriter fileWriter = new FileWriter(csvFilePath, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            // Melakukan iterasi pada setiap view dalam LinearLayout
            for (int i = 0; i < layoutList.getChildCount(); i++) {
                View view = layoutList.getChildAt(i);

                // Mendapatkan referensi untuk setiap view dalam row_add.xml
                EditText nimEditText = view.findViewById(R.id.nim_anggota);
                EditText namaEditText = view.findViewById(R.id.nama_anggota);
                AppCompatSpinner prodiSpinner = view.findViewById(R.id.sp_prodi);
                EditText tlpEditText = view.findViewById(R.id.tlp_anggota);

                // Mendapatkan nilai dari setiap view
                String nim = nimEditText.getText().toString().trim();
                String nama = namaEditText.getText().toString().trim();
                String prodi = prodiSpinner.getSelectedItem().toString();
                String tlp = tlpEditText.getText().toString().trim();

                if (TextUtils.isEmpty(nim) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(prodi) || TextUtils.isEmpty(tlp)) {
                    Toast.makeText(ListAnggota.this, "Mohon isi semua kolom", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!nim.matches("E\\d{8}")) {
                    nimEditText.setError("NIM tidak valid. (JTI menggunakan prefix 'E' di awal NIM).");
                    nimEditText.requestFocus();
                    return;
                }

                // Menyimpan data ke file .csv
                String[] data = {nim, nama, prodi, tlp};
                csvWriter.writeNext(data);
            }

            // Menutup penulisan file .csv
            csvWriter.close();

            // Menampilkan pesan berhasil
            Toast.makeText(this, "Data berhasil disimpan ke file .csv", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListAnggota.this, TambahSurat.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearInputFields() {
        // Melakukan iterasi pada setiap view dalam LinearLayout
        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View view = layoutList.getChildAt(i);

            // Mendapatkan referensi untuk setiap view dalam row_add.xml
            EditText nimEditText = view.findViewById(R.id.nim_anggota);
            EditText namaEditText = view.findViewById(R.id.nama_anggota);
            EditText tlpEditText = view.findViewById(R.id.tlp_anggota);
            AppCompatSpinner prodiSpinner = view.findViewById(R.id.sp_prodi);

            // Mengosongkan nilai pada setiap view
            nimEditText.setText("");
            namaEditText.setText("");
            tlpEditText.setText("");
            prodiSpinner.setSelection(0);
        }
    }

//        // Membuat instance adapter RecyclerView dengan menggunakan ArrayList anggotaList
////        adapter = new AnggotaAdapter(anggotaList);
//        // Mendapatkan referensi RecyclerView dari layout TambahSurat.xml
//        RecyclerView recyclersView = findViewById(R.id.recycle_data);
//
//        // Set adapter ke RecyclerView
//        recyclersView.setAdapter(adapter);

    @Override
    public void onClick(View v) {
//        addView();
        switch (v.getId()) {
            case R.id.tambah:
                addView();
                simpan.setEnabled(true);
                break;
            case R.id.btSimpan:
                simpanData();
                break;
        }

    }

    private void addView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dataView = inflater.inflate(R.layout.row_add, layoutList,false);

        nimAngggota = (EditText) dataView.findViewById(R.id.nim_anggota);
        namaAnggota = (EditText)  dataView.findViewById(R.id.nama_anggota);
        tlpAnggota = (EditText) dataView.findViewById(R.id.tlp_anggota);
        spProdi = (AppCompatSpinner) dataView.findViewById(R.id.sp_prodi);
        hapus = (ImageView) dataView.findViewById(R.id.hapus);

        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this, R.array.prodi, android.R.layout.simple_spinner_item);
        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdi.setAdapter(adapterND);

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(dataView);
                simpan.setEnabled(false);
            }
        });
        layoutList.addView(dataView);

    }
    private void removeView(View view){
        layoutList.removeView(view);
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