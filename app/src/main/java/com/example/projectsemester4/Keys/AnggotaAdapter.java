package com.example.projectsemester4.Keys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsemester4.R;

import java.util.List;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.AnggotaViewHolder> {
    private List<AnggotaModel> anggotaList;

    public AnggotaAdapter(List<AnggotaModel> anggotaList) {
        this.anggotaList = anggotaList;
    }

    @NonNull
    @Override
    public AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tampil_anggota, parent, false);
        return new AnggotaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnggotaViewHolder holder, int position) {
        AnggotaModel anggota = anggotaList.get(position);

        holder.nimTextView.setText(anggota.getNim());
        holder.namaTextView.setText(anggota.getNama());
        holder.prodiTextView.setText(anggota.getProdi());
        holder.tlpTextView.setText(anggota.getTlp());
    }

    @Override
    public int getItemCount() {
        return anggotaList.size();
    }

    public void clear() {
        anggotaList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<AnggotaModel> anggotaList) {
        this.anggotaList.addAll(anggotaList);
        notifyDataSetChanged();
    }
    public List<AnggotaModel> getAnggotaList() {
        return anggotaList;
    }

    public static class AnggotaViewHolder extends RecyclerView.ViewHolder {
        public TextView nimTextView, namaTextView, prodiTextView, tlpTextView;

        public AnggotaViewHolder(@NonNull View itemView) {
            super(itemView);
            nimTextView = itemView.findViewById(R.id.text_nim);
            namaTextView = itemView.findViewById(R.id.text_nama);
            prodiTextView = itemView.findViewById(R.id.text_prodi);
            tlpTextView = itemView.findViewById(R.id.text_tlp);
        }
    }
}

