package com.example.projectsemester4;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Fragment0 extends Fragment {
    private RecyclerView recyclerView;

    public Fragment0() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_0, container, false);

        recyclerView = view.findViewById(R.id.semua_tampil);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter());

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_tampil_surat, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tvMataKuliah.setText("Mata Kuliah " + (position + 1));
            holder.tvNamaMhs.setText("Nama Mahasiswa " + (position + 1));
        }

        @Override
        public int getItemCount() {
            return 10; //jumlah item pada RecyclerView
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
