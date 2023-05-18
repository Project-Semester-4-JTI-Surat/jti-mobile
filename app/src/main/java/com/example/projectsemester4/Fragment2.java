package com.example.projectsemester4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    private RecyclerView recyclerView;
    private List<DataSurat> dataList;
    private List<DataSurat> filteredDataList;
    private MyAdapter adapter;
    private MainViewModel mainModel;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        recyclerView = view.findViewById(R.id.semua_tampil_2);

        dataList = new ArrayList<>();
        filteredDataList = new ArrayList<>();
        mainModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
//        mainModel.initDataList();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainModel.getFilteredDataSurat().observe(getViewLifecycleOwner(), dataSurat -> {
            adapter = new MyAdapter(dataSurat);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        });
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<DataSurat> dataList;

        public MyAdapter(List<DataSurat> dataList) {
            this.dataList = dataList;
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
            DataSurat data = dataList.get(position);
            holder.tvMataKuliah.setText(data.getMataKuliah());
            holder.tvNamaMhs.setText(data.getNamaMhs());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create an intent to start DetailActivity
                    Intent intent = new Intent(getActivity(), DetailSurat.class);

                    // put the data to the intent
                    intent.putExtra("mata_kuliah", data.getMataKuliah());
                    intent.putExtra("nama_mhs", data.getNamaMhs());

                    // start DetailActivity
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
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