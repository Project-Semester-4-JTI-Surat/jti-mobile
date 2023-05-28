package com.example.projectsemester4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsemester4.Keys.AnggotaDetailAdapter;
import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.DetailSuratRequest;
import com.example.projectsemester4.Keys.DetailSuratResponse;
import com.example.projectsemester4.Keys.DetailSuratService;
import com.example.projectsemester4.Keys.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSurat extends AppCompatActivity {
    private TextView txJenisSurat, txDetailPengaju;
    private TextView dtNamaDosen, dtNamaMitra, dtAlamatMitra, dtTanggalPelaksanaan, dtKeterangan, dtStatus;
    private TextView dtKoordinator, dtJudulTA, dtTanggalDibuat, dtTanggalSelesai, dtKebutuhan;
    private TextView isiNamaDosen, isiNamaMitra, isiAlamatMitra, isiTanggalPelaksanaan, isiKeterangan, isiStatus;
    private TextView isiKoordinator, isiJudulTA, isiTanggalDibuat, isiTanggalSelesai, isiKebutuhan;
    private RecyclerView recyclerView;
    private AnggotaDetailAdapter adapter;
    private String suratId;


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
        dtTanggalPelaksanaan = findViewById(R.id.dt_tanggalPelaksanaan);
        isiTanggalPelaksanaan = findViewById(R.id.isi_tanggalPelaksanaan);
        dtKeterangan = findViewById(R.id.dt_keterangan);
        isiKeterangan = findViewById(R.id.isi_keterangan);
        dtStatus = findViewById(R.id.dt_status);
        isiStatus = findViewById(R.id.isi_status);
        txDetailPengaju = findViewById(R.id.tx_detailPengaju);

        dtKoordinator = findViewById(R.id.dt_namaKoordinator);
        isiKoordinator = findViewById(R.id.isi_namaKoordinator);
        dtJudulTA = findViewById(R.id.dt_judulTA);
        isiJudulTA = findViewById(R.id.isi_judulTA);
        dtTanggalDibuat = findViewById(R.id.dt_tanggalDibuat);
        isiTanggalDibuat = findViewById(R.id.isi_tanggalDibuat);
        dtTanggalSelesai = findViewById(R.id.dt_tanggalSelesai);
        isiTanggalSelesai = findViewById(R.id.isi_tanggalSelesai);
        dtKebutuhan = findViewById(R.id.dt_kebutuhan);
        isiKebutuhan = findViewById(R.id.isi_kebutuhan);

        recyclerView = findViewById(R.id.recycle_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AnggotaDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        // get the intent that started this activity
        Intent intent = getIntent();
//
//        // get the data from the intent
        String mataKuliah = intent.getStringExtra("jenis_surat");

        txJenisSurat.setText(mataKuliah);
        suratId = getIntent().getStringExtra("surat_id");

        loadDetailSurat();
        System.out.println(suratId);
    }
    private String getToken() {
        MyPreferences preferences = new MyPreferences(this);
        return preferences.getString("token", "");
    }
    private void loadDetailSurat() {
        String token = getToken();
        DetailSuratService detailSuratService = ApiClient.getDetailSurats(this);
        Call<DetailSuratResponse> call = detailSuratService.getDetailSurat("Bearer " + token, suratId);
        call.enqueue(new Callback<DetailSuratResponse>() {
            @Override
            public void onResponse(Call<DetailSuratResponse> call, Response<DetailSuratResponse> response) {
                if (response.isSuccessful()) {
                    DetailSuratResponse detailSuratResponse = response.body();
                    if (detailSuratResponse != null && detailSuratResponse.isSuccess()) {
                        List<DetailSuratRequest> dataList = detailSuratResponse.getData();
                        if (dataList != null && !dataList.isEmpty()) {
                            DetailSuratRequest data = dataList.get(0);

                            // Set data detail surat ke komponen UI
//                            isiKoordinator.setText(data.getKoordinator());
//                            isiNamaDosen.setText(data.getJudul_ta());
                            if (data.getJudul_ta()==null){
                                isiJudulTA.setText("-");
                            }else{
                                isiJudulTA.setText(data.getJudul_ta());
                            }

                            isiNamaMitra.setText(data.getNama_mitra());
                            isiAlamatMitra.setText(data.getAlamat_mitra());
                            isiTanggalDibuat.setText(data.getTanggal_dibuat());
                            isiTanggalPelaksanaan.setText(data.getTanggal_pelaksanaan());
                            isiTanggalSelesai.setText(data.getTanggal_selesai());
                            isiKebutuhan.setText(data.getKebutuhan());
                            isiKeterangan.setText(data.getKeterangan());
                            Intent intent = getIntent();
                            String namaMhs = intent.getStringExtra("keterangan");
                            isiStatus.setText(namaMhs);

                            // Set data anggota pengaju surat ke adapter
                            adapter.setAnggotaList(data.getAnggota());
                        } else {
                            // Tampilkan pesan error jika data tidak ada
                            Toast.makeText(DetailSurat.this, "Data detail surat tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Tampilkan pesan error jika respon tidak berhasil
                        Toast.makeText(DetailSurat.this, "Gagal memuat detail surat", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tampilkan pesan error jika gagal memuat respon
                    Toast.makeText(DetailSurat.this, "Gagal memuat respon", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailSuratResponse> call, Throwable t) {
                // Tampilkan pesan error jika terjadi kesalahan jaringan
                Toast.makeText(DetailSurat.this, "Terjadi kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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