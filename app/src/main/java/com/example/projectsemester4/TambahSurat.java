package com.example.projectsemester4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsemester4.Keys.AnggotaAdapter;
import com.example.projectsemester4.Keys.AnggotaModel;
import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.SuratRequest;
import com.opencsv.CSVReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.FileReader;
import java.io.IOException;

public class TambahSurat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText kepada, alamat, tanggal_dibuat, tanggal_pelaksanaan, tanggal_selesai, keterangan;
    private Spinner spJenisSurat, spNamaDosen, sp_koordinator, kebutuhan;
    private TextView judulSurat, jsSurat, tv_koordinator, nmDosen, kpd, almt, tv_tanggal_dibuat, tv_tanggal_pelaksanaan, tv_tanggal_selesai, kbth;
    private Button tambahAnggotaButton, resetFormButton, ajukanButton;
    private int jumlahAnggota = 1;
    DatePickerDialog picker;
    private RecyclerView recyclerView;
    private AnggotaAdapter adapter;
    private List<AnggotaModel> anggotaList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_surat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spJenisSurat = findViewById(R.id.spJenisSurat);
        spNamaDosen = findViewById(R.id.spNamaDosen);
        kepada = findViewById(R.id.kepada);
        alamat = findViewById(R.id.alamat);
        tanggal_dibuat = findViewById(R.id.et_tanggal_dibuat);
        tanggal_pelaksanaan = findViewById(R.id.et_tanggal_pelaksanaan);
        tanggal_selesai = findViewById(R.id.et_tanggal_selesai);
        sp_koordinator = findViewById(R.id.sp_koordinator);
        kebutuhan = findViewById(R.id.kebutuhan);
        keterangan = findViewById(R.id.keterangan);

        judulSurat = findViewById(R.id.judulSurat);
        jsSurat = findViewById(R.id.jsSurat);
        nmDosen = findViewById(R.id.nmDosen);
        kpd = findViewById(R.id.kpd);
        almt = findViewById(R.id.almt);
        tv_koordinator = findViewById(R.id.tv_koordinator);
        tv_tanggal_dibuat = findViewById(R.id.tv_tanggal_dibuat);
        tv_tanggal_pelaksanaan = findViewById(R.id.tv_tanggal_pelaksanaan);
        tv_tanggal_selesai = findViewById(R.id.tv_tanggal_selesai);
        kbth = findViewById(R.id.kbth);

        tambahAnggotaButton = findViewById(R.id.tambahAnggotaButton);
        resetFormButton = findViewById(R.id.resetButton);
        ajukanButton = findViewById(R.id.ajukanButton);

        ArrayAdapter<CharSequence> adapterJS = ArrayAdapter.createFromResource(this,
                R.array.jenis_surat, android.R.layout.simple_spinner_item);
        adapterJS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenisSurat.setAdapter(adapterJS);

        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
                R.array.nama_dosen, android.R.layout.simple_spinner_item);
        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNamaDosen.setAdapter(adapterND);

        ArrayAdapter<CharSequence> adapterKoordinator = ArrayAdapter.createFromResource(this,
                R.array.nama_dosen, android.R.layout.simple_spinner_item);
        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_koordinator.setAdapter(adapterKoordinator);

        ArrayAdapter<CharSequence> adapterKebutuhan = ArrayAdapter.createFromResource(this,
                R.array.kebutuhan, android.R.layout.simple_spinner_item);
        adapterKebutuhan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kebutuhan.setAdapter(adapterKebutuhan);

        spJenisSurat.setOnItemSelectedListener(this);
        spNamaDosen.setOnItemSelectedListener(this);
        kebutuhan.setOnItemSelectedListener(this);
        sp_koordinator.setOnItemSelectedListener(this);

        tanggal_dibuat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TambahSurat.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                                tanggal_dibuat.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        tanggal_pelaksanaan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TambahSurat.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                                tanggal_pelaksanaan.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        tanggal_selesai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TambahSurat.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                                tanggal_selesai.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        ajukanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(kepada.getText().toString()) || TextUtils.isEmpty(alamat.getText().toString()) || TextUtils.isEmpty(sp_koordinator.getSelectedItem().toString())
                        || TextUtils.isEmpty(tanggal_dibuat.getText().toString().trim()) || TextUtils.isEmpty(tanggal_pelaksanaan.getText().toString().trim())
                        || TextUtils.isEmpty(spJenisSurat.getSelectedItem().toString()) || TextUtils.isEmpty(tanggal_selesai.getText().toString().trim())
                        || TextUtils.isEmpty(spNamaDosen.getSelectedItem().toString()) || TextUtils.isEmpty(kebutuhan.getSelectedItem().toString())
                        || TextUtils.isEmpty(keterangan.getText().toString()) || anggotaList==null) {
                    Toast.makeText(TambahSurat.this, "Mohon Isi Semua Kolom dan Tambahkan Anggota", Toast.LENGTH_LONG).show();
                } else {
                    //proceed to login
                    insertSurat();
                    resetForm();
                }

            }
        });

        tambahAnggotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListAnggota.class));
            }
        });
        resetForm();

        recyclerView = findViewById(R.id.recycle_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        anggotaList = new ArrayList<>();
        adapter = new AnggotaAdapter(anggotaList);
        recyclerView.setAdapter(adapter);
        loadCSVData();
    }

    private void loadCSVData() {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";

        try {
            FileReader fileReader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] nextLine = csvReader.readNext();
            if (nextLine == null) {
                // Tidak ada data dalam file CSV
                Toast.makeText(this, "Data Anggota tidak ada atau belum ditambahkan", Toast.LENGTH_SHORT).show();
            } else {
                while (nextLine != null) {
                    // Membaca data dari baris CSV
                    String nim = nextLine[0];
                    String nama = nextLine[1];
                    String prodi = nextLine[2];
                    String tlp = nextLine[3];

                    // Membuat objek AnggotaModel dari data CSV
                    AnggotaModel anggota = new AnggotaModel(nim, nama, prodi, tlp);

                    // Menambahkan objek AnggotaModel ke dalam list anggotaList
                    anggotaList.add(anggota);

                    nextLine = csvReader.readNext();
                }

                // Menampilkan data ke RecyclerView
                adapter.notifyDataSetChanged();
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetForm(){
    resetFormButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // mereset semua field ke nilai awal
            kepada.setText("");
            alamat.setText("");
            tanggal_dibuat.setText("");
            tanggal_pelaksanaan.setText("");
            tanggal_selesai.setText("");
            spJenisSurat.setSelection(0);
            sp_koordinator.setSelection(0);
            spNamaDosen.setSelection(0);
            kebutuhan.setSelection(0);
            keterangan.setText("");
        }
    });
}
    private void insertSurat() {
        String jenisSurat = spJenisSurat.getSelectedItem().toString();
        String namaDosen = spNamaDosen.getSelectedItem().toString();
        String koordinator = sp_koordinator.getSelectedItem().toString();
        String kepadaText = kepada.getText().toString().trim();
        String alamatText = alamat.getText().toString().trim();
        String tanggalDibuatText = tanggal_dibuat.getText().toString().trim();
        String tanggalPelaksanaanText = tanggal_pelaksanaan.getText().toString().trim();
        String tanggalSelesaiText = tanggal_selesai.getText().toString().trim();
        String kebutuhanText = kebutuhan.getSelectedItem().toString();
        String keteranganText = keterangan.getText().toString().trim();

        // Ambil daftar anggota dari adapter
        List<AnggotaModel> anggotaList = adapter.getAnggotaList();

        // Buat objek SuratRequest dengan data yang diperlukan
        SuratRequest suratRequest = new SuratRequest(
                jenisSurat,
                namaDosen,
                koordinator,
                kepadaText,
                alamatText,
                tanggalDibuatText,
                tanggalPelaksanaanText,
                tanggalSelesaiText,
                kebutuhanText,
                keteranganText,
                anggotaList
        );

        // Kirim permintaan HTTP menggunakan Retrofit
        Call<SuratRequest> call = ApiClient.getSuratInsert(TambahSurat.this).insertSurat(suratRequest);
        call.enqueue(new Callback<SuratRequest>() {
            @Override
            public void onResponse(Call<SuratRequest> call, Response<SuratRequest> response) {
                if (response.isSuccessful()) {
                    // Berhasil mengirim surat, lakukan tindakan yang diinginkan
                    Toast.makeText(TambahSurat.this, "Surat berhasil diajukan", Toast.LENGTH_SHORT).show();
                    String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";
                    File file = new File(csvFilePath);
                    if(file.exists()){
                        file.delete();
                    }
                    Intent intent = new Intent(TambahSurat.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Gagal mengirim surat, tangani kesalahan
                    Toast.makeText(TambahSurat.this, "Gagal mengirim surat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuratRequest> call, Throwable t) {
                // Gagal mengirim surat, tangani kesalahan
                Toast.makeText(TambahSurat.this, "Gagal mengirim surat", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void insertSurat() {
//
//        SuratRequest suratRequest = new SuratRequest();
//        suratRequest.setKepada(kepada.getText().toString());
//        suratRequest.setAlamat(alamat.getText().toString());
//        suratRequest.setTanggal(tanggal.getText().toString());
//        suratRequest.setJenisSurat(spJenisSurat.getSelectedItem().toString());
//        suratRequest.setNamaDosen(spNamaDosen.getSelectedItem().toString());
//        suratRequest.setKebutuhan(kebutuhan.getSelectedItem().toString());
//        suratRequest.setNimAnggota(nimAnggota.getText().toString());
//        suratRequest.setNamaAnggota(namaAnggota.getText().toString());
//        suratRequest.setTlpAnggota(tlpAnggota.getText().toString());
//        suratRequest.setProdiAnggota(prodiAnggota.getSelectedItem().toString());
//
//        Call<SuratRequest> InsertSuratResponseCall = ApiClient.getSuratInsert(TambahSurat.this).insertSurat(suratRequest);
//        InsertSuratResponseCall.enqueue(new Callback<SuratRequest>() {
//            @Override
//            public void onResponse(Call<SuratRequest> call, Response<SuratRequest> response) {
//
//                if(response.isSuccessful()) {
//                    String res = response.body().toString();
//String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";
//    File file = new File(csvFilePath);
//        if(file.exists()){
//        file.delete();
//    }
//                    Toast.makeText(TambahSurat.this, res, Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(TambahSurat.this, "Penambahan Data Gagal!", Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SuratRequest> call, Throwable t) {
//                Toast.makeText(TambahSurat.this, "Failed to add data to the server : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                Log.e("API Error", "Failed to add data to the server", t);
//            }
//        });
//
//    }


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
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
