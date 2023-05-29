package com.example.projectsemester4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsemester4.Keys.ApiClient;
import com.example.projectsemester4.Keys.MyPreferences;
import com.example.projectsemester4.Keys.TampilSurat;
import com.example.projectsemester4.Keys.TampilSuratRequest;
import com.example.projectsemester4.Keys.TampilSuratResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment1 extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.semua_tampil_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Buat instance adapter
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = getView().findViewById(R.id.swipe_refresh_layout1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataSurat();
            }
        });

        // Panggil method untuk mendapatkan data surat
        loadDataSurat();
    }

    private String getToken() {
        MyPreferences preferences = new MyPreferences(getContext());
        return preferences.getString("token", "");
    }

    private void loadDataSurat() {
        // Panggil endpoint API menggunakan Retrofit
        String token = getToken();

        // Panggil endpoint API menggunakan Retrofit
        TampilSurat tampilSuratService = ApiClient.getTampilSurats(getContext());
        Call<TampilSuratResponse> call = tampilSuratService.getTampilSurat("Bearer " + token);
        call.enqueue(new Callback<TampilSuratResponse>() {
            @Override
            public void onResponse(Call<TampilSuratResponse> call, Response<TampilSuratResponse> response) {
                if (response.isSuccessful()) {
                    TampilSuratResponse tampilSuratResponse = response.body();
                    if (tampilSuratResponse != null && tampilSuratResponse.isSuccess()) {
                        List<TampilSuratRequest> data = filterDataMenunggu(tampilSuratResponse.getData());
                        if (data != null) {
                            // Tampilkan data surat ke dalam RecyclerView
                            adapter.setData(data);


                            // Selesai melakukan refresh
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        // Gagal mendapatkan data surat
                        Toast.makeText(getContext(), "Gagal mendapatkan data surat", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Gagal mendapatkan respons dari server
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TampilSuratResponse> call, Throwable t) {
                // Gagal melakukan request
                Toast.makeText(getContext(), "Request gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                // Selesai memuat data, hentikan indikator refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private List<TampilSuratRequest> filterDataMenunggu(List<TampilSuratRequest> data) {
        List<TampilSuratRequest> filteredData = new ArrayList<>();
        for (TampilSuratRequest surat : data) {
            if (surat.getKeterangan().equals("Menunggu")) {
                filteredData.add(surat);
            }
        }
        return filteredData;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<TampilSuratRequest> data;

        public void setData(List<TampilSuratRequest> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_tampil_surat, parent, false);
            return new MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

            TampilSuratRequest surat = data.get(position);

            if (surat != null) {
                if(surat.getKode_surat().equals("PK")){
                    holder.tvMataKuliah.setText("Pengajuan PKL");
                }else if(surat.getKode_surat().equals("LL")){
                    holder.tvMataKuliah.setText("Lain Lain");
                }else if(surat.getKode_surat().equals("MK")){
                    holder.tvMataKuliah.setText("Mata Kuliah");
                }else if(surat.getKode_surat().equals("OBS")){
                    holder.tvMataKuliah.setText("Observasi Penelitian");
                }else if(surat.getKode_surat().equals("TA")){
                    holder.tvMataKuliah.setText("Tugas Akhir");
                }
                holder.tvNamaMhs.setText(surat.getKeterangan());

                // Cek kondisi surat.getKeterangan()
                if (surat.getKeterangan().equals("Menunggu")) {
                    // Ubah warna teks menjadi biru
                    holder.tvMataKuliah.setTextColor(Color.BLACK);
                    holder.tvNamaMhs.setTextColor(Color.BLUE);
                }else if (surat.getKeterangan().equals("Diproses")) {
                    // Ubah warna teks menjadi biru
                    holder.tvMataKuliah.setTextColor(Color.BLACK);
                    holder.tvNamaMhs.setTextColor(Color.rgb(217, 146, 76));
                }else if (surat.getKeterangan().equals("Dapat Diambil")) {
                    // Ubah warna teks menjadi biru
                    holder.tvMataKuliah.setTextColor(Color.BLACK);
                    holder.tvNamaMhs.setTextColor(Color.rgb(145, 204, 137));
                }else if (surat.getKeterangan().equals("Selesai")) {
                    // Ubah warna teks menjadi biru
                    holder.tvMataKuliah.setTextColor(Color.BLACK);
                    holder.tvNamaMhs.setTextColor(Color.GRAY);
                }else if (surat.getKeterangan().equals("Ditolak")) {
                    // Ubah warna teks menjadi biru
                    holder.tvMataKuliah.setTextColor(Color.BLACK);
                    holder.tvNamaMhs.setTextColor(Color.RED);
                } else {
                    // Kembalikan warna teks ke warna default
                    holder.tvMataKuliah.setTextColor(Color.BLACK);
                    holder.tvNamaMhs.setTextColor(Color.BLACK);
                }
            } else {
                holder.tvMataKuliah.setText("Data Tidak Ada");
                holder.tvNamaMhs.setText("Data Tidak Ada");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create an intent to start DetailActivity
                    Intent intent = new Intent(getActivity(), DetailSurat.class);

                    // put the data to the intent
                    intent.putExtra("jenis_surat", holder.tvMataKuliah.getText().toString());
                    intent.putExtra("keterangan", holder.tvNamaMhs.getText().toString());
                    intent.putExtra("surat_id",surat.getUuid());

                    // start DetailActivity
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvMataKuliah, tvNamaMhs;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMataKuliah = itemView.findViewById(R.id.tv_mata_kuliah);
                tvNamaMhs = itemView.findViewById(R.id.tv_nama_mhs);
            }
        }
    }
}
