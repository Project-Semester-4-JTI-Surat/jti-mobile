package com.example.projectsemester4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TampilanFaq extends AppCompatActivity {
    ArrayList<FaqModelClass> arrayList;
    RecyclerView recyclerView;
    FaqAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        recyclerView = findViewById(R.id.faq_tampil);
        arrayList = new ArrayList<>();

        arrayList.add(new FaqModelClass("judul 1", "deskripsi 1", false));
        arrayList.add(new FaqModelClass("judul 1", "deskripsi 1", false));
        arrayList.add(new FaqModelClass("judul 1", "deskripsi 1", false));

        adapter = new FaqAdapter(arrayList, TampilanFaq.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}
