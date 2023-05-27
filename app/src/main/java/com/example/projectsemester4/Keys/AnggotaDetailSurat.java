package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class AnggotaDetailSurat {
    @SerializedName("surat_id")
    private String surat_id;
    @SerializedName("nim")
    private String nim;
    @SerializedName("ketua")
    private String ketua;
    @SerializedName("individu")
    private String individu;
    @SerializedName("prodi_id")
    private int prodi_id;
    @SerializedName("nama")
    private String nama;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("prodi")
    private ProdiDetailSurat prodi;

    public AnggotaDetailSurat(String surat_id, String nim, String ketua, String individu, int prodi_id, String nama, String no_hp, ProdiDetailSurat prodi) {
        this.surat_id = surat_id;
        this.nim = nim;
        this.ketua = ketua;
        this.individu = individu;
        this.prodi_id = prodi_id;
        this.nama = nama;
        this.no_hp = no_hp;
        this.prodi = prodi;
    }

    public String getSurat_id() {
        return surat_id;
    }

    public void setSurat_id(String surat_id) {
        this.surat_id = surat_id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getKetua() {
        return ketua;
    }

    public void setKetua(String ketua) {
        this.ketua = ketua;
    }

    public String getIndividu() {
        return individu;
    }

    public void setIndividu(String individu) {
        this.individu = individu;
    }

    public int getProdi_id() {
        return prodi_id;
    }

    public void setProdi_id(int prodi_id) {
        this.prodi_id = prodi_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public ProdiDetailSurat getProdi() {
        return prodi;
    }

    public void setProdi(ProdiDetailSurat prodi) {
        this.prodi = prodi;
    }
}
