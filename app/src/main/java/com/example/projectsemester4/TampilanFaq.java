package com.example.projectsemester4;

import android.os.Bundle;
import android.view.MenuItem;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Mengatur fungsi tombol back pada appbar
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
