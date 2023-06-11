package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuratRequest {

    public SuratRequest(String kode_surat, String dosen, String prodi, String koordinator, String nama_mitra, String alamat_mitra, String tanggal_dibuat, String tanggal_pelaksanaan, String tanggal_selesai, String kebutuhan, String keterangan, String judul_ta) {
        this.kode_surat = kode_surat;
        this.dosen = dosen;
        this.prodi = prodi;
        this.koordinator = koordinator;
        this.nama_mitra = nama_mitra;
        this.alamat_mitra = alamat_mitra;
        this.tanggal_dibuat = tanggal_dibuat;
        this.tanggal_pelaksanaan = tanggal_pelaksanaan;
        this.tanggal_selesai = tanggal_selesai;
        this.kebutuhan = kebutuhan;
        this.keterangan = keterangan;
        this.judul_ta = judul_ta;
    }

    @SerializedName("kode_surat")
    private String kode_surat;
    @SerializedName("dosen")
    private String dosen;
    @SerializedName("prodi")
    private String prodi;
    @SerializedName("koordinator")
    private String koordinator;
    @SerializedName("nama_mitra")
    private String nama_mitra;
    @SerializedName("alamat_mitra")
    private String alamat_mitra;
    @SerializedName("tanggal_dibuat")
    private String tanggal_dibuat;
    @SerializedName("tanggal_pelaksanaan")
    private String tanggal_pelaksanaan;
    @SerializedName("tanggal_selesai")
    private String tanggal_selesai;
    @SerializedName("kebutuhan")
    private String kebutuhan;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("judul_ta")
    private String judul_ta;


    public String getKode_surat() {
        return kode_surat;
    }

    public void setKode_surat(String kode_surat) {
        this.kode_surat = kode_surat;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getKoordinator() {
        return koordinator;
    }

    public void setKoordinator(String koordinator) {
        this.koordinator = koordinator;
    }

    public String getNama_mitra() {
        return nama_mitra;
    }

    public void setNama_mitra(String nama_mitra) {
        this.nama_mitra = nama_mitra;
    }

    public String getAlamat_mitra() {
        return alamat_mitra;
    }

    public void setAlamat_mitra(String alamat_mitra) {
        this.alamat_mitra = alamat_mitra;
    }

    public String getTanggal_dibuat() {
        return tanggal_dibuat;
    }

    public void setTanggal_dibuat(String tanggal_dibuat) {
        this.tanggal_dibuat = tanggal_dibuat;
    }

    public String getTanggal_pelaksanaan() {
        return tanggal_pelaksanaan;
    }

    public void setTanggal_pelaksanaan(String tanggal_pelaksanaan) {
        this.tanggal_pelaksanaan = tanggal_pelaksanaan;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getKebutuhan() {
        return kebutuhan;
    }

    public void setKebutuhan(String kebutuhan) {
        this.kebutuhan = kebutuhan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJudul_ta() {
        return judul_ta;
    }

    public void setJudul_ta(String judul_ta) {
        this.judul_ta = judul_ta;
    }
}
