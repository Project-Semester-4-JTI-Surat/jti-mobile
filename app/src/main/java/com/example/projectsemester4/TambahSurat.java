package com.example.projectsemester4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsemester4.Keys.AnggotaAdapter;
import com.example.projectsemester4.Keys.AnggotaInsertResponse;
import com.example.projectsemester4.Keys.AnggotaModel;
import com.example.projectsemester4.Keys.AnggotaRequest;
import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.Dosen;
import com.example.projectsemester4.Keys.DosenResponse;
import com.example.projectsemester4.Keys.JenisSurat;
import com.example.projectsemester4.Keys.JenisSuratResponse;
import com.example.projectsemester4.Keys.Koordinator;
import com.example.projectsemester4.Keys.KoordinatorResponse;
import com.example.projectsemester4.Keys.LoginRequest;
import com.example.projectsemester4.Keys.LoginResponse;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.SuratInsert;
import com.example.projectsemester4.Keys.SuratRequest;
import com.example.projectsemester4.Keys.SuratResponse;
import com.example.projectsemester4.Keys.UserService;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.FileReader;
import java.io.IOException;

public class TambahSurat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText kepada, alamat, tanggal_dibuat, tanggal_pelaksanaan, tanggal_selesai, keterangan, et_judul_ta;
    private Spinner spJenisSurat, spNamaDosen, sp_koordinator, kebutuhan;
    private TextView judulSurat, jsSurat, tv_koordinator, nmDosen, kpd, almt, tv_tanggal_dibuat, tv_tanggal_pelaksanaan, tv_tanggal_selesai, kbth, tv_judul_ta;
    private Button tambahAnggotaButton, resetFormButton, ajukanButton;
    private int jumlahAnggota = 1;
    DatePickerDialog picker;
    private RecyclerView recyclerView;
    private AnggotaAdapter adapter;
    private List<AnggotaModel> anggotaList;
    private RelativeLayout layoutList;

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
        et_judul_ta = findViewById(R.id.et_judul_ta);
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
        tv_judul_ta = findViewById(R.id.tv_judul_ta);
        kbth = findViewById(R.id.kbth);

        tambahAnggotaButton = findViewById(R.id.tambahAnggotaButton);
        resetFormButton = findViewById(R.id.resetButton);
        ajukanButton = findViewById(R.id.ajukanButton);

        layoutList = findViewById(R.id.relativeLayout);

//        ArrayAdapter<CharSequence> adapterJS = ArrayAdapter.createFromResource(this,
//                R.array.jenis_surat, android.R.layout.simple_spinner_item);
//        adapterJS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spJenisSurat.setAdapter(adapterJS);

//        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
//                R.array.nama_dosen, android.R.layout.simple_spinner_item);
//        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spNamaDosen.setAdapter(adapterND);
//
//        ArrayAdapter<CharSequence> adapterKoordinator = ArrayAdapter.createFromResource(this,
//                R.array.nama_dosen, android.R.layout.simple_spinner_item);
//        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_koordinator.setAdapter(adapterKoordinator);
//
//        ArrayAdapter<CharSequence> adapterKebutuhan = ArrayAdapter.createFromResource(this,
//                R.array.kebutuhan, android.R.layout.simple_spinner_item);
//        adapterKebutuhan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        kebutuhan.setAdapter(adapterKebutuhan);

        spJenisSurat.setOnItemSelectedListener(this);
        spNamaDosen.setOnItemSelectedListener(this);
        kebutuhan.setOnItemSelectedListener(this);
        sp_koordinator.setOnItemSelectedListener(this);

        tanggal_dibuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TambahSurat.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tanggal_dibuat.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        tanggal_pelaksanaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TambahSurat.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tanggal_pelaksanaan.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        tanggal_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //date picker dialog
                picker = new DatePickerDialog(TambahSurat.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tanggal_selesai.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        ajukanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(kepada.getText().toString()) || TextUtils.isEmpty(alamat.getText().toString()) || TextUtils.isEmpty(tanggal_dibuat.getText().toString().trim()) || TextUtils.isEmpty(tanggal_pelaksanaan.getText().toString().trim())
                        || TextUtils.isEmpty(spJenisSurat.getSelectedItem().toString()) || TextUtils.isEmpty(tanggal_selesai.getText().toString().trim())
                        || TextUtils.isEmpty(kebutuhan.getSelectedItem().toString()) || TextUtils.isEmpty(keterangan.getText().toString()) || anggotaList.isEmpty()) {
                    Toast.makeText(TambahSurat.this, "Mohon Isi Semua Kolom dan Tambahkan Anggota", Toast.LENGTH_LONG).show();
                } else {
                    //proceed to ajukan
                    if (TextUtils.isEmpty(et_judul_ta.getText().toString())){
                        et_judul_ta.setText("-");
                    }
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

        spJenisSurat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedJenisSurat = (String) parent.getItemAtPosition(position);

                if (selectedJenisSurat.equals("Mata Kuliah")) {
                    // Jika "Mata Kuliah" dipilih, maka sp_koordinator boleh kosong atau disabled
                    sp_koordinator.setSelection(0); // Menghapus pilihan yang ada
                    sp_koordinator.setEnabled(false);
                    spNamaDosen.setEnabled(true);
                    ArrayAdapter<String> koordinatorAdapter = new ArrayAdapter<>(TambahSurat.this, android.R.layout.simple_spinner_item, new String[]{"-"});
                    sp_koordinator.setAdapter(koordinatorAdapter);
                    getDataDosen();
                } else {
                    // Jika selain "Mata Kuliah" dipilih, maka spDosen boleh kosong atau disabled
                    spNamaDosen.setSelection(0); // Menghapus pilihan yang ada
                    spNamaDosen.setEnabled(false);
                    sp_koordinator.setEnabled(true);
                    getDataKoordinator();
                    ArrayAdapter<String> dosenAdapter = new ArrayAdapter<>(TambahSurat.this, android.R.layout.simple_spinner_item, new String[]{"-"});
                    spNamaDosen.setAdapter(dosenAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak ada tindakan yang diambil saat tidak ada item yang dipilih
            }
        });

        resetForm();

        recyclerView = findViewById(R.id.recycle_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        anggotaList = new ArrayList<>();
        adapter = new AnggotaAdapter(anggotaList, this);
        recyclerView.setAdapter(adapter);

        loadCSVData();

        getDataJenisSurat();
//        getDataDosen();
//        getDataKoordinator();

    }

    private void getDataJenisSurat() {
        SuratInsert apiService = ApiClient.getSuratInsert(this);
        // Membuat panggilan ke API
        Call<JenisSuratResponse> call = apiService.getDataJenisSurat();
        call.enqueue(new Callback<JenisSuratResponse>() {
            @Override
            public void onResponse(Call<JenisSuratResponse> call, Response<JenisSuratResponse> response) {
                if (response.isSuccessful()) {
                    JenisSuratResponse jenisSuratResponse = response.body();
                    if (jenisSuratResponse != null && jenisSuratResponse.isSuccess()) {
                        List<JenisSurat> jenisSuratList = jenisSuratResponse.getData();

                        // Mengambil data keterangan saja
                        List<String> keteranganList = new ArrayList<>();
                        for (JenisSurat jenisSurat : jenisSuratList) {
                            if (!"-".equals(jenisSurat.getKeterangan())) {
                                keteranganList.add(jenisSurat.getKeterangan());
                            }
                        }

                        // Mengatur adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahSurat.this,
                                android.R.layout.simple_spinner_item, keteranganList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spJenisSurat.setAdapter(adapter);
                    } else {
                        // Tangani respons sukses tetapi pesan kesalahan dari API
                        String message = jenisSuratResponse != null ? jenisSuratResponse.getMessage() : "Unknown error";
                        Toast.makeText(TambahSurat.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani respons gagal dari API
                    Toast.makeText(TambahSurat.this, "Gagal Untuk Mengambil Data Jenis Surat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JenisSuratResponse> call, Throwable t) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                Toast.makeText(TambahSurat.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDataDosen() {
        SuratInsert apiService = ApiClient.getSuratInsert(this);
        // Membuat panggilan ke API
        Call<DosenResponse> call = apiService.getDataDosen();
        call.enqueue(new Callback<DosenResponse>() {
            @Override
            public void onResponse(Call<DosenResponse> call, Response<DosenResponse> response) {
                if (response.isSuccessful()) {
                    DosenResponse dosenResponse = response.body();
                    if (dosenResponse != null && dosenResponse.isSuccess()) {
                        List<Dosen> dosenSuratList = dosenResponse.getData();

                        // Mengambil data keterangan saja
                        List<String> NamaList = new ArrayList<>();
                        for (Dosen dosen : dosenSuratList) {
                            if (!"-".equals(dosen.getNama())) {
                                NamaList.add(dosen.getNama());
                            }
                        }

                        // Mengatur adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahSurat.this,
                                android.R.layout.simple_spinner_item, NamaList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spNamaDosen.setAdapter(adapter);
                    } else {
                        // Tangani respons sukses tetapi pesan kesalahan dari API
                        String message = dosenResponse != null ? dosenResponse.getMessage() : "Unknown error";
                        Toast.makeText(TambahSurat.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani respons gagal dari API
                    Toast.makeText(TambahSurat.this, "Gagal Untuk Mengambil Data Dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DosenResponse> call, Throwable t) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                Toast.makeText(TambahSurat.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDataKoordinator() {
        SuratInsert apiService = ApiClient.getSuratInsert(this);
        // Membuat panggilan ke API
        Call<KoordinatorResponse> call = apiService.getDataKoordinator();
        call.enqueue(new Callback<KoordinatorResponse>() {
            @Override
            public void onResponse(Call<KoordinatorResponse> call, Response<KoordinatorResponse> response) {
                if (response.isSuccessful()) {
                    KoordinatorResponse koordinatorResponse = response.body();
                    if (koordinatorResponse != null && koordinatorResponse.isSuccess()) {
                        List<Koordinator> koordinatorSuratList = koordinatorResponse.getData();

                        // Mengambil data keterangan saja
                        List<String> NamaList = new ArrayList<>();
                        for (Koordinator koordinator : koordinatorSuratList) {
                            if (!"-".equals(koordinator.getNama())) {
                                NamaList.add(koordinator.getNama());
                            }
                        }

                        // Mengatur adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahSurat.this,
                                android.R.layout.simple_spinner_item, NamaList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_koordinator.setAdapter(adapter);
                    } else {
                        // Tangani respons sukses tetapi pesan kesalahan dari API
                        String message = koordinatorResponse != null ? koordinatorResponse.getMessage() : "Unknown error";
                        Toast.makeText(TambahSurat.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani respons gagal dari API
                    Toast.makeText(TambahSurat.this, "Gagal Untuk Mengambil Data Dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KoordinatorResponse> call, Throwable t) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                Toast.makeText(TambahSurat.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadCSVData() {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";

        try {
            FileReader fileReader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] nextLine = csvReader.readNext();
            if (nextLine == null) {
                Toast.makeText(this, "Data Anggota tidak ada atau belum ditambahkan", Toast.LENGTH_SHORT).show();
            } else {
                int rowNumber = 0; // Menyimpan nomor baris CSV
                while (nextLine != null) {
                    String nim = nextLine[0];
                    String nama = nextLine[1];
                    String prodi = nextLine[2];
                    String tlp = nextLine[3];

                    // Membuat objek AnggotaModel dari data CSV
                    AnggotaModel anggota = new AnggotaModel(nim, nama, prodi, tlp);

                    // Menambahkan objek AnggotaModel ke dalam list anggotaList
                    anggotaList.add(anggota);

                    // Menambahkan item ke dalam layoutList
                    LayoutInflater inflater = LayoutInflater.from(TambahSurat.this);
                    final View dataView = inflater.inflate(R.layout.list_item_tampil_anggota, layoutList, false);

                    TextView nimTextView = dataView.findViewById(R.id.text_nim);
                    nimTextView.setText(nim);

                    TextView namaTextView = dataView.findViewById(R.id.text_nama);
                    namaTextView.setText(nama);

                    TextView prodiTextView = dataView.findViewById(R.id.text_prodi);
                    prodiTextView.setText(prodi);

                    TextView tlpTextView = dataView.findViewById(R.id.text_tlp);
                    tlpTextView.setText(tlp);

                    ImageView hapusImageView = dataView.findViewById(R.id.hapus);
                    hapusImageView.setTag(rowNumber);
                    hapusImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Menghapus data dari baris CSV sesuai dengan nomor baris
                            int clickedRow = (int) v.getTag();
                            hapusDataDariCSV(clickedRow);

                            // Menghapus tampilan item dari RecyclerView
                            anggotaList.remove(clickedRow);
                            recyclerView.getAdapter().notifyItemRemoved(clickedRow);
                            recyclerView.getAdapter().notifyItemRangeChanged(clickedRow, anggotaList.size());
                        }
                    });
//                    layoutList.addView(dataView);
//                    recyclerView.addView(dataView);
                    nextLine = csvReader.readNext();
                    rowNumber++;
                }

                adapter.notifyDataSetChanged();
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSementara() {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";

        try {
            FileReader fileReader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] nextLine = csvReader.readNext();

            csvReader.close();

            System.out.println("List Anggota => " + anggotaList.size());
            System.out.println("List Anggota => " + anggotaList.get(1).getNama_anggota());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hapusDataDariCSV(int rowNumber) {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";
        List<String[]> csvDataList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReader(fileReader);

            String[] nextLine;
            int currentRow = 0; // Menyimpan nomor baris saat membaca CSV
            while ((nextLine = csvReader.readNext()) != null) {
                // Jika nomor baris saat membaca CSV sama dengan nomor baris yang ingin dihapus
                if (currentRow == rowNumber) {
                    // Skip baris ini untuk menghapusnya
                    currentRow++;
                    continue;
                }

                csvDataList.add(nextLine);
                currentRow++;
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(csvFilePath);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            csvWriter.writeAll(csvDataList);
            csvWriter.flush();
            csvWriter.close();

            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat menghapus data", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetForm() {
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
                et_judul_ta.setText("");
            }
        });
    }

    public interface ProdiIdCallback {
        void onProdiIdReceived(int prodiId);
    }

    private int getProdiID(ProdiIdCallback callback) {
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

                    // Tampilkan data pengguna pada tampilan yang sesuai
                    int prodiId = loginRequest.getProdi().getId();

                    // Panggil callback dengan nilai prodi ID
                    callback.onProdiIdReceived(prodiId);

                } else {
                    // Tangani respons gagal

                    Log.e("Data User", "Gagal mengambil data user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Tangani kegagalan koneksi ke server

            }
        });
        return 0;
    }
    private void deleteCSVFile() {
        String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";
        File csvFile = new File(csvFilePath);

        if (csvFile.exists()) {
            boolean deleted = csvFile.delete();
            if (deleted) {
                Toast.makeText(this, "File .csv berhasil dihapus", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Gagal menghapus file .csv", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File .csv tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertSurat() {
        MyPreferences preferences = new MyPreferences(this);
        String token = preferences.getString("token", "");

        String jenisSurat = spJenisSurat.getSelectedItem().toString();
        String namaDosen = spNamaDosen.getSelectedItem().toString();
        String koordinator = sp_koordinator.getSelectedItem().toString();
        String kepadaText = kepada.getText().toString().trim();
        String prodi = preferences.getString("prodi", "");
        String alamatText = alamat.getText().toString().trim();
        String tanggalDibuatText = tanggal_dibuat.getText().toString().trim();
        String tanggalPelaksanaanText = tanggal_pelaksanaan.getText().toString().trim();
        String tanggalSelesaiText = tanggal_selesai.getText().toString().trim();
        String kebutuhanText = kebutuhan.getSelectedItem().toString();
        String keteranganText = keterangan.getText().toString().trim();
        String judulTAText = et_judul_ta.getText().toString().trim();
        SuratRequest suratRequest = new SuratRequest(
//                anggotaList,
                jenisSurat,
                namaDosen,
                prodi,
                koordinator,
                kepadaText,
                alamatText,
                tanggalDibuatText,
                tanggalPelaksanaanText,
                tanggalSelesaiText,
                kebutuhanText,
                keteranganText,
                judulTAText
        );

        // Kirim permintaan HTTP menggunakan Retrofit
        Call<SuratResponse> call = ApiClient.getSuratInsert(TambahSurat.this).insertSurat("Bearer " + token, suratRequest);
        call.enqueue(new Callback<SuratResponse>() {
            @Override
            public void onResponse(Call<SuratResponse> call, Response<SuratResponse> response) {
                if (response.isSuccessful()) {
                    // Berhasil mengirim surat, lakukan tindakan yang diinginkan
                    SuratResponse suratResponse = response.body();

                    System.out.println("ID Surat => " + suratResponse.getData());
                    Toast.makeText(TambahSurat.this, "Surat berhasil diajukan", Toast.LENGTH_SHORT).show();
                    String csvFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";
                    BufferedReader bufferedReader = null;
//                    try {
//                        bufferedReader = new BufferedReader(new FileReader(csvFilePath));
//                        String input;
//                        String sample;
//                        int count = 0;
//                        while((input = bufferedReader.readLine()) != null)
//                        {
//                            count++;
//                            sample = input
//                        }
//                        System.out.println("Count : "+count);
//                        for (int i = 0; i <= count; i++){
//
//                        }
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException(e);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                    String csvFilePath2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/data.csv";
                    String line = "";
                    String splitBy = ",";
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(csvFilePath2));
                        while ((line = br.readLine()) != null) {
                            String[] anggota = line.split(splitBy, -1);
                            String nama_anggota = anggota[1].replace("\"", "");
                            String nim_anggota = anggota[0].replace("\"", "");
                            String no_hp = anggota[3].replace("\"", "");
                            String nim = anggota[2].replace("\"", "");

                            AnggotaRequest anggotaRequest = new AnggotaRequest(nama_anggota, nim_anggota, no_hp, nim);
                            Call<AnggotaInsertResponse> tambahAnggota = ApiClient.getSuratInsert(TambahSurat.this).insertAnggota("Bearer " + token, anggotaRequest, suratResponse.getData());
                            tambahAnggota.enqueue(new Callback<AnggotaInsertResponse>() {
                                @Override
                                public void onResponse(Call<AnggotaInsertResponse> call, Response<AnggotaInsertResponse> response) {
                                    AnggotaInsertResponse anggotaInsertResponse = response.body();
                                    if (response.isSuccessful()) {
                                        System.out.println("Upload...");
                                    } else {
                                        System.out.println(response.raw());
                                    }
                                }

                                @Override
                                public void onFailure(Call<AnggotaInsertResponse> call, Throwable t) {
                                    System.out.println(t.getMessage());
                                }
                            });
                        }

                        br.close();
                        deleteCSVFile();
                        Intent intent = new Intent(TambahSurat.this, MainActivity.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    try {
//                        FileReader fileReader = new FileReader(csvFilePath);
//                        CSVReader csvReader = new CSVReader(fileReader);
//                        String[] nextLine = csvReader.readNext();
//                        String input[];
//                        int count = 0;
//                        while ((input = csvReader.readNext()) != null){
//                            count++;
//                        }
//                        System.out.println(count);
////                        while (nextLine != null) {
////                            String nim = nextLine[0];
////                            String nama = nextLine[1];
////                            String prodi = nextLine[2];
////                            String tlp = nextLine[3];
////                            AnggotaRequest anggotaRequest = new AnggotaRequest(
////                                    nama, nim, tlp, prodi
////                            );
////                            Call<AnggotaInsertResponse> tambahAnggota = ApiClient.getSuratInsert(TambahSurat.this).insertAnggota(
////                                    "Bearer " + token, anggotaRequest, suratResponse.getData()
////                            );
////                            tambahAnggota.enqueue(new Callback<AnggotaInsertResponse>() {
////                                @Override
////                                public void onResponse(Call<AnggotaInsertResponse> call, Response<AnggotaInsertResponse> response) {
////                                    if (response.isSuccessful()){
////                                        System.out.println("Upload...");
////                                    }
////                                }
////
////                                @Override
////                                public void onFailure(Call<AnggotaInsertResponse> call, Throwable t) {
////
////                                }
////                            });
////                        }
//                        csvReader.close();
////                        File file = new File(csvFilePath);
////                        if (file.exists()) {
////                            file.delete();
////                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Intent intent = new Intent(TambahSurat.this, MainActivity.class);
//                    startActivity(intent);
                } else {
                    // Gagal mengirim surat, tangani kesalahan
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().toString());
                    System.out.println("error " + response.raw());
                    System.out.println("Debug : " + jenisSurat + " - " +
                            namaDosen + " - " +
                            prodi + " - " +
                            koordinator + " - " +
                            kepadaText + " - " +
                            alamatText + " - " +
                            tanggalDibuatText + " - " +
                            tanggalPelaksanaanText + " - " +
                            tanggalSelesaiText + " - " +
                            kebutuhanText + " - " +
                            keteranganText + " - " +
                            judulTAText);
                    Toast.makeText(TambahSurat.this, "Gagal mengirim surat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuratResponse> call, Throwable t) {
                // Gagal mengirim surat, tangani kesalahan
                Toast.makeText(TambahSurat.this, "Gagal mengirim surat" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
