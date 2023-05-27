package com.example.projectsemester4.Keys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsemester4.R;
import com.example.projectsemester4.TambahSurat;

import java.util.List;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.AnggotaViewHolder> {
    private List<AnggotaModel> anggotaList;
    private TambahSurat tambahSurat;

    public AnggotaAdapter(List<AnggotaModel> anggotaList,TambahSurat tambahSurat) {
        this.anggotaList = anggotaList;
        this.tambahSurat = tambahSurat;
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

        holder.hapusImageView.setTag(position);
        holder.hapusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = (int) v.getTag();
                tambahSurat.hapusDataDariCSV(clickedPosition);

                anggotaList.remove(clickedPosition);
                notifyItemRemoved(clickedPosition);
                notifyItemRangeChanged(clickedPosition, anggotaList.size());
            }
        });
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
        public ImageView hapusImageView;

        public AnggotaViewHolder(@NonNull View itemView) {
            super(itemView);
            nimTextView = itemView.findViewById(R.id.text_nim);
            namaTextView = itemView.findViewById(R.id.text_nama);
            prodiTextView = itemView.findViewById(R.id.text_prodi);
            tlpTextView = itemView.findViewById(R.id.text_tlp);
            hapusImageView = itemView.findViewById(R.id.hapus);
        }
    }
}

