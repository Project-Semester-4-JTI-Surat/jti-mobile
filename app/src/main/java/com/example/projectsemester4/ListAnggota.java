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
import android.util.Log;
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
import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.JenisSurat;
import com.example.projectsemester4.Keys.JenisSuratResponse;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.Prodi;
import com.example.projectsemester4.Keys.ProdiResponse;
import com.example.projectsemester4.Keys.SuratInsert;
import com.example.projectsemester4.Keys.UserService;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAnggota extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layoutList;
    private Button tambah;
    private Button simpan;
    private EditText nimAngggota;
    private EditText namaAnggota;
    private EditText tlpAnggota;
    private AppCompatSpinner spProdi;
    private ImageView hapus;
//    private List<String[]> csvDataList = new ArrayList<>();

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

        if (layoutList.getChildCount() == 0) {
            simpan.setEnabled(true);
        } else {
            simpan.setEnabled(false);
        }

//        // Mengecek apakah parameter csvDataList dikirimkan dari aktivitas sebelumnya
//        Intent intent = getIntent();
//        boolean isCSVDataAvailable = intent.hasExtra("csvDataList");
//
//        // Jika csvDataList ada, maka mendapatkan data dari intent ekstra
//        if (isCSVDataAvailable) {
//            String[][] csvDataArray = (String[][]) intent.getSerializableExtra("csvDataList");
//            csvDataList = Arrays.asList(csvDataArray);
//
//            // Membandingkan data dari getDataAnggota() dengan data dari file CSV
//            boolean shouldGetData = true;
//            for (String[] csvData : csvDataList) {
//                String nimCSV = csvData[0];
//                String namaCSV = csvData[1];
//                String prodiCSV = csvData[2];
//                String tlpCSV = csvData[3];
//
//                // Membandingkan data dari getDataAnggota() dengan data dari file CSV
//                if (nimAngggota.getText().toString().equals(nimCSV)
//                        && namaAnggota.getText().toString().equals(namaCSV)
//                        && spProdi.getSelectedItem().toString().equals(prodiCSV)
//                        && tlpAnggota.getText().toString().equals(tlpCSV)) {
//                    shouldGetData = false;
//                    break;
//                }
//            }
//
//            // Jika data sudah ada dalam file CSV, maka getDataAnggota() tidak dijalankan
//            if (shouldGetData) {
//                getDataAnggota();
//            }
//        } else {
//            // Jika csvDataList tidak ada, inisialisasi dengan list kosong
//            csvDataList = new ArrayList<>();
//        }

        getDataProdi();
    }

    private void getDataAnggota(){
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

                    LayoutInflater inflater = LayoutInflater.from(ListAnggota.this);
                    final View dataView = inflater.inflate(R.layout.row_add, layoutList, false);

                    nimAngggota = dataView.findViewById(R.id.nim_anggota);
                    namaAnggota = dataView.findViewById(R.id.nama_anggota);
                    tlpAnggota = dataView.findViewById(R.id.tlp_anggota);
                    spProdi = dataView.findViewById(R.id.sp_prodi);
                    hapus = dataView.findViewById(R.id.hapus);

                    // Tampilkan data pengguna pada tampilan yang sesuai
                    nimAngggota.setText(loginRequest.getNim());
                    namaAnggota.setText(loginRequest.getNama());
                    // Mengatur data prodi ke dalam spinner
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ListAnggota.this,
                            android.R.layout.simple_spinner_item, new String[]{loginRequest.getProdi().getKeterangan()});
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spProdi.setAdapter(adapter);
                    tlpAnggota.setText(loginRequest.getNoHp());

                    nimAngggota.setEnabled(false);
                    namaAnggota.setEnabled(false);
                    tlpAnggota.setEnabled(false);
                    spProdi.setEnabled(false);
                    hapus.setVisibility(dataView.GONE);

                    layoutList.addView(dataView);

                } else {
                    // Tangani respons gagal
                    Toast.makeText(ListAnggota.this, "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
                    Log.e("Data User", "Gagal mengambil data user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Tangani kegagalan koneksi ke server
                Toast.makeText(ListAnggota.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
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

                        LayoutInflater inflater = LayoutInflater.from(ListAnggota.this);
                        final View dataView = inflater.inflate(R.layout.row_add, layoutList, false);

                        spProdi = dataView.findViewById(R.id.sp_prodi);

                        // Mengatur adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ListAnggota.this,
                                android.R.layout.simple_spinner_item, keteranganList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spProdi.setAdapter(adapter);
                    } else {
                        // Tangani respons sukses tetapi pesan kesalahan dari API
                        String message = prodiResponse != null ? prodiResponse.getMessage() : "Unknown error";
                        Toast.makeText(ListAnggota.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani respons gagal dari API
                    Toast.makeText(ListAnggota.this, "Gagal Untuk Mengambil Data Jenis Surat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdiResponse> call, Throwable t) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                Toast.makeText(ListAnggota.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tambah:
                addView();
                simpan.setEnabled(true);
                getDataProdi();
                break;
            case R.id.btSimpan:
                simpanData();

                break;
        }
    }

    private void addView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dataView = inflater.inflate(R.layout.row_add, layoutList, false);

        nimAngggota = dataView.findViewById(R.id.nim_anggota);
        namaAnggota = dataView.findViewById(R.id.nama_anggota);
        tlpAnggota = dataView.findViewById(R.id.tlp_anggota);
        spProdi = dataView.findViewById(R.id.sp_prodi);
        hapus = dataView.findViewById(R.id.hapus);

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

    private void removeView(View view) {
        layoutList.removeView(view);
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
