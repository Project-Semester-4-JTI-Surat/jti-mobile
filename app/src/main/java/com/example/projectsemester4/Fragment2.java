package com.example.projectsemester4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment {
    private RecyclerView recyclerView;
    public Fragment2() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        recyclerView = view.findViewById(R.id.semua_tampil_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new Fragment2.MyAdapter());

        return view;
    }
    private class MyAdapter extends RecyclerView.Adapter<Fragment2.MyAdapter.MyViewHolder> {

        @NonNull
        @Override
        public Fragment2.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_tampil_surat, parent, false);
            return new Fragment2.MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Fragment2.MyAdapter.MyViewHolder holder, int position) {
            holder.tvMataKuliah.setText("Mata Kuliah " + (position + 1));
            holder.tvNamaMhs.setText("Nama Mahasiswa " + (position + 1));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create an intent to start DetailActivity
                    Intent intent = new Intent(getActivity(), DetailSurat.class);

                    // put the data to the intent
                    intent.putExtra("mata_kuliah", holder.tvMataKuliah.getText().toString());
                    intent.putExtra("nama_mhs", holder.tvNamaMhs.getText().toString());

                    // start DetailActivity
                    startActivity(intent);
                }
            });
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