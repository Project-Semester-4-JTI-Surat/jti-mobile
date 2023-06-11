package com.example.projectsemester4.Keys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectsemester4.R;

import java.util.ArrayList;
import java.util.List;
public class AnggotaDetailAdapter extends RecyclerView.Adapter<AnggotaDetailAdapter.AnggotaViewHolder> {
    private Context context;
    private List<AnggotaDetailSurat> anggotaList;

    public AnggotaDetailAdapter(Context context) {
        this.context = context;
        anggotaList = new ArrayList<>();
    }

    public void setAnggotaList(List<AnggotaDetailSurat> anggotaList) {
        this.anggotaList = anggotaList;
        notifyDataSetChanged();
    }
    public List<AnggotaDetailSurat> getAnggotaList() {
        return anggotaList;
    }


    @NonNull
    @Override
    public AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_anggota_detail, parent, false);
        return new AnggotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnggotaViewHolder holder, int position) {
        AnggotaDetailSurat anggota = anggotaList.get(position);
        holder.tvNama.setText(anggota.getNama());
        holder.tvNim.setText(anggota.getNim());
        holder.tvProdi.setText(anggota.getProdi().getKeterangan());
        holder.tvNoHp.setText(anggota.getNo_hp());

        if (anggotaList.size() > 0) {
            holder.txtAnggota.setText("Anggota " + (position + 1));
        } else {
            holder.txtAnggota.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return anggotaList.size();
    }

    public static class AnggotaViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama;
        public TextView tvNim;
        public TextView tvProdi;
        public TextView tvNoHp;
        public TextView txtAnggota;

        public AnggotaViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNim = itemView.findViewById(R.id.tv_nim);
            tvProdi = itemView.findViewById(R.id.tv_prodi);
            txtAnggota = itemView.findViewById(R.id.txt_anggota);
            tvNoHp = itemView.findViewById(R.id.tv_nohp);
        }
    }
}

