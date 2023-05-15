package com.example.projectsemester4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.SuratRequest;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahSurat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText kepada, alamat, tanggal, keterangan;
    private Spinner spJenisSurat, spNamaDosen, kebutuhan;
    private TextView judulSurat, jsSurat, nmDosen, kpd, almt, tgl, kbth;
    private RadioGroup pilihan;
    private RadioButton pilihan1, pilihan2;
    private TextView anggotaKe, txNim, txNamaAnggota, txProdi, txTlpAnggota;
    private EditText nimAnggota, namaAnggota, tlpAnggota;
    private Spinner prodiAnggota;
    private Button tambahAnggotaButton, resetFormButton, ajukanButton;
    private int jumlahAnggota = 1;
    DatePickerDialog picker;
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
        keterangan = findViewById(R.id.keterangan);
        judulSurat = findViewById(R.id.judulSurat);
        jsSurat = findViewById(R.id.jsSurat);
        nmDosen = findViewById(R.id.nmDosen);
        kpd = findViewById(R.id.kpd);
        almt = findViewById(R.id.almt);
        tgl = findViewById(R.id.tgl);
        kbth = findViewById(R.id.kbth);

        pilihan = findViewById(R.id.pilihan);
        pilihan1 = findViewById(R.id.pilihan1);
        pilihan2 = findViewById(R.id.pilihan2);
        anggotaKe = findViewById(R.id.anggotaKe);
        txNim = findViewById(R.id.txNim);
        txNamaAnggota = findViewById(R.id.txNamaAnggota);
        txProdi = findViewById(R.id.txProdi);
        txTlpAnggota = findViewById(R.id.txTlpAnggota);
        nimAnggota = findViewById(R.id.nimAnggota);
        namaAnggota = findViewById(R.id.namaAnggota);
        prodiAnggota = findViewById(R.id.prodiAnggota);
        tlpAnggota = findViewById(R.id.tlpAnggota);
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

        ArrayAdapter<CharSequence> adapterKebutuhan = ArrayAdapter.createFromResource(this,
                R.array.kebutuhan, android.R.layout.simple_spinner_item);
        adapterKebutuhan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kebutuhan.setAdapter(adapterKebutuhan);

        spJenisSurat.setOnItemSelectedListener(this);
        spNamaDosen.setOnItemSelectedListener(this);
        kebutuhan.setOnItemSelectedListener(this);

        tanggal.setOnClickListener(new View.OnClickListener(){
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
                                tanggal.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        ajukanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(kepada.getText().toString()) || TextUtils.isEmpty(alamat.getText().toString())
                        || TextUtils.isEmpty(tanggal.getText().toString().trim()) || TextUtils.isEmpty(spJenisSurat.getSelectedItem().toString())
                        || TextUtils.isEmpty(spNamaDosen.getSelectedItem().toString()) || TextUtils.isEmpty(kebutuhan.getSelectedItem().toString())
                        || TextUtils.isEmpty(keterangan.getText().toString()) || TextUtils.isEmpty(nimAnggota.getText().toString())
                        || TextUtils.isEmpty(namaAnggota.getText().toString())|| TextUtils.isEmpty(prodiAnggota.getSelectedItem().toString())
                        || TextUtils.isEmpty(tlpAnggota.getText().toString())) {
                    Toast.makeText(TambahSurat.this, "Mohon Isi Semua Kolom", Toast.LENGTH_LONG).show();
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
                // Mengaktifkan RadioButton pilihan2 dan menonaktifkan pilihan1
                pilihan1.setEnabled(false);
                pilihan2.setEnabled(true);

                // Menambahkan tampilan form anggota baru
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.tambah_surat, null);
                LinearLayout anggotaLayout = findViewById(R.id.LinearLayout2);
                anggotaLayout.addView(view, anggotaLayout.getChildCount());

                // Menambahkan nomor urut anggota
                jumlahAnggota++;
                TextView anggotaKe = findViewById(R.id.anggotaKe);
                anggotaKe.setVisibility(View.VISIBLE);
                anggotaKe.setText("Anggota Ke-" + jumlahAnggota);


                // Mengambil referensi dari elemen form anggota yang baru ditambahkan
                TextView txNim = view.findViewById(R.id.txNim);
                TextView txNamaAnggota = view.findViewById(R.id.txNamaAnggota);
                TextView txProdi = view.findViewById(R.id.txProdi);
                TextView txTlpAnggota = view.findViewById(R.id.txTlpAnggota);
                EditText nimAnggota = view.findViewById(R.id.nimAnggota);
                EditText namaAnggota = view.findViewById(R.id.namaAnggota);
                EditText tlpAnggota = view.findViewById(R.id.tlpAnggota);
                Spinner prodiAnggota = view.findViewById(R.id.prodiAnggota);

                // Mengatur adapter untuk Spinner prodiAnggotaBaru
                ArrayAdapter<CharSequence> adapterProdi = ArrayAdapter.createFromResource(TambahSurat.this,
                        R.array.prodi, android.R.layout.simple_spinner_item);
                adapterProdi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                prodiAnggota.setAdapter(adapterProdi);
            }
        });


        resetForm();
    }
public void resetForm(){
    resetFormButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // mereset semua field ke nilai awal
            anggotaKe.setText("Anggota Ke-1");
            kepada.setText("");
            alamat.setText("");
            tanggal.setText("");
            spJenisSurat.setSelection(0);
            spNamaDosen.setSelection(0);
            kebutuhan.setSelection(0);
            prodiAnggota.setSelection(0);
            tlpAnggota.setText("");
            pilihan1.setChecked(false);
            pilihan1.setEnabled(true);
            pilihan2.setChecked(false);
            pilihan2.setEnabled(true);
            LinearLayout layout = findViewById(R.id.LinearLayout2);
            int count = layout.getChildCount();
            for (int i = count - 1; i >= 5; i--) {
                View view = layout.getChildAt(i);
                layout.removeView(view);
            }
        }
    });
}
    private void insertSurat() {

        SuratRequest suratRequest = new SuratRequest(

        );
        suratRequest.setKepada(kepada.getText().toString());
        suratRequest.setAlamat(alamat.getText().toString());
        suratRequest.setTanggal(tanggal.getText().toString());
        suratRequest.setJenisSurat(spJenisSurat.getSelectedItem().toString());
        suratRequest.setNamaDosen(spNamaDosen.getSelectedItem().toString());
        suratRequest.setKebutuhan(kebutuhan.getSelectedItem().toString());
        suratRequest.setNimAnggota(nimAnggota.getText().toString());
        suratRequest.setNamaAnggota(namaAnggota.getText().toString());
        suratRequest.setTlpAnggota(tlpAnggota.getText().toString());
        suratRequest.setProdiAnggota(prodiAnggota.getSelectedItem().toString());

        Call<SuratRequest> InsertSuratResponseCall = ApiClient.getSuratInsert(TambahSurat.this).insertSurat(suratRequest);
        InsertSuratResponseCall.enqueue(new Callback<SuratRequest>() {
            @Override
            public void onResponse(Call<SuratRequest> call, Response<SuratRequest> response) {

                if(response.isSuccessful()) {
                    String res = response.body().toString();
                    Toast.makeText(TambahSurat.this, res, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TambahSurat.this, "Penambahan Data Gagal!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<SuratRequest> call, Throwable t) {
                Toast.makeText(TambahSurat.this, "Failed to add data to the server : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Error", "Failed to add data to the server", t);
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
