package com.example.projectsemester4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAnggota extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutList;
    private EditText nimAngggota, namaAnggota, tlpAnggota;
    private AppCompatSpinner spProdi;
    private Button tambah, simpan;
    private ImageView hapus;
//    private ArrayList<Anggota> anggotaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_anggota);

        layoutList = findViewById(R.id.layout_list);
        tambah = findViewById(R.id.tambah);
//        simpan = findViewById(R.id.btSimpan);

        tambah.setOnClickListener(this);
//        simpan.setOnClickListener(this);

//        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
//                R.array.prodi, android.R.layout.simple_spinner_item);
//        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spProdi.setAdapter(adapterND);
//        spProdi.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//            case R.id.tambah:
        addView();
//                break;
//
//            case R.id.btSimpan:
//
//                if (checkIfValidAndRead()){
//                    Intent intent = new Intent(ListAnggota.this, TambahSurat.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("list", anggotaList);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//                break;
//        }

    }

//    private boolean checkIfValidAndRead() {
//        anggotaList.clear();
//        boolean result = true;
//
//        for (int i=0;i<layoutList.getChildCount();i++){
//            View dataView = layoutList.getChildAt(i);
//
//            nimAngggota = (EditText) dataView.findViewById(R.id.nim_anggota);
//            namaAnggota = (EditText)  dataView.findViewById(R.id.nim_anggota);
//            tlpAnggota = (EditText) dataView.findViewById(R.id.tlp_anggota);
//            spProdi = (AppCompatSpinner) dataView.findViewById(R.id.sp_prodi);
//
//
//            Anggota anggota = new Anggota();
//            if (!nimAngggota.getText().toString().equals("")){
//                anggota.setAngggotaNim(nimAngggota.getText().toString());
//            }else {
//                result = false;
//                break;
//            }
//
//            if (!namaAnggota.getText().toString().equals("")){
//                anggota.setAnggotaNama(namaAnggota.getText().toString());
//            }else {
//                result = false;
//                break;
//            }
//
//            if (spProdi.getSelectedItemPosition()!=0){
//                ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
//                        R.array.prodi, android.R.layout.simple_spinner_item);
//                adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spProdi.setAdapter(adapterND);
//            }else {
//                result = false;
//                break;
//            }
//
//            if (!tlpAnggota.getText().toString().equals("")){
//                anggota.setAnggotaTlp(tlpAnggota.getText().toString());
//            }else {
//                result = false;
//                break;
//            }
//
//            anggotaList.add(anggota);
//
//        }
//
//        if (anggotaList.size()==0){
//            result = false;
//            Toast.makeText(this, "Tambahkan Data", Toast.LENGTH_SHORT).show();
//        }else if (!result){
//            Toast.makeText(this,"Isi semua kolom", Toast.LENGTH_SHORT).show();
//        }
//        return result;
//    }

    private void addView() {
        final View dataView = getLayoutInflater().inflate(R.layout.row_add, null,false);

        nimAngggota = (EditText) dataView.findViewById(R.id.nim_anggota);
        namaAnggota = (EditText)  dataView.findViewById(R.id.nama_anggota);
        tlpAnggota = (EditText) dataView.findViewById(R.id.tlp_anggota);
        spProdi = (AppCompatSpinner) dataView.findViewById(R.id.sp_prodi);
        hapus = (ImageView) dataView.findViewById(R.id.hapus);

        ArrayAdapter<CharSequence> adapterND = ArrayAdapter.createFromResource(this,
                R.array.prodi, android.R.layout.simple_spinner_item);
        adapterND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdi.setAdapter(adapterND);

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(dataView);
            }
        });
        layoutList.addView(dataView);

    }
    private void removeView(View view){
        layoutList.removeView(view);
    }
}