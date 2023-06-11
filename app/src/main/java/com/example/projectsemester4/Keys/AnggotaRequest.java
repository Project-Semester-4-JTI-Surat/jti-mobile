package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class AnggotaRequest {
    @SerializedName("nama_anggota")
    private String nama_anggota;
    @SerializedName("nim_anggota")
    private String nim_anggota;
    @SerializedName("nohp_anggota")
    private String nohp_anggota;
    @SerializedName("prodi_id_anggota")
    private String prodi_id_anggota;

    public AnggotaRequest(String nama_anggota, String nim_anggota, String nohp_anggota, String prodi_id_anggota) {
        this.nama_anggota = nama_anggota;
        this.nim_anggota = nim_anggota;
        this.nohp_anggota = nohp_anggota;
        this.prodi_id_anggota = prodi_id_anggota;
    }

    public String getNama_anggota() {
        return nama_anggota;
    }

    public void setNama_anggota(String nama_anggota) {
        this.nama_anggota = nama_anggota;
    }

    public String getNim_anggota() {
        return nim_anggota;
    }

    public void setNim_anggota(String nim_anggota) {
        this.nim_anggota = nim_anggota;
    }

    public String getNohp_anggota() {
        return nohp_anggota;
    }

    public void setNohp_anggota(String nohp_anggota) {
        this.nohp_anggota = nohp_anggota;
    }

    public String getProdi_id_anggota() {
        return prodi_id_anggota;
    }

    public void setProdi_id_anggota(String prodi_id_anggota) {
        this.prodi_id_anggota = prodi_id_anggota;
    }
}
