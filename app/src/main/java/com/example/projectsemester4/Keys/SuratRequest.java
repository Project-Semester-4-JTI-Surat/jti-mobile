package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuratRequest {
//    private String jenisSurat;

//    private String kepada;
//    private String alamat;
//    private String tanggal;
//    private String kebutuhan;
//    private String keterangan;
//    private List<AnggotaModel> anggotaList;
    private List<AnggotaModel> anggotaList;

    @SerializedName("kode_surat")
    private String kode_surat;
    @SerializedName("dosen_id")
    private String dosen_id;
//    @SerializedName("status_id")
//    private int statusId;
//    @SerializedName("prodi_id")
//    private int prodiId;
//    @SerializedName("dosen_id")
//    private int dosenId;
    @SerializedName("koordinator_id")
    private String koordinator_id;
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


    // constructor dan getter/setter di sini
//    String kodeSurat, int statusId, int prodiId, int dosenId, String kodeKoordinator, String namaMitra, String alamatMitra, String tanggalDibuat, String tanggalPelaksanaan, String tanggalSelesai, String judulTa, String kebutuhan, String keterangan, List<String> namaAnggota, List<String> nimAnggota, List<String> nohpAnggota, List<Integer> prodiIdAnggota


    public SuratRequest(List<AnggotaModel> anggotaList, String kode_surat, String dosen_id, String koordinator_id, String nama_mitra, String alamat_mitra, String tanggal_dibuat, String tanggal_pelaksanaan, String tanggal_selesai, String kebutuhan, String keterangan, String judul_ta) {
        this.anggotaList = anggotaList;
        this.kode_surat = kode_surat;
        this.dosen_id = dosen_id;
        this.koordinator_id = koordinator_id;
        this.nama_mitra = nama_mitra;
        this.alamat_mitra = alamat_mitra;
        this.tanggal_dibuat = tanggal_dibuat;
        this.tanggal_pelaksanaan = tanggal_pelaksanaan;
        this.tanggal_selesai = tanggal_selesai;
        this.kebutuhan = kebutuhan;
        this.keterangan = keterangan;
        this.judul_ta = judul_ta;
    }

    public List<AnggotaModel> getAnggotaList() {
        return anggotaList;
    }

    public void setAnggotaList(List<AnggotaModel> anggotaList) {
        this.anggotaList = anggotaList;
    }

    public String getKode_surat() {
        return kode_surat;
    }

    public void setKode_surat(String kode_surat) {
        this.kode_surat = kode_surat;
    }

    public String getDosen_id() {
        return dosen_id;
    }

    public void setDosen_id(String dosen_id) {
        this.dosen_id = dosen_id;
    }

    public String getKoordinator_id() {
        return koordinator_id;
    }

    public void setKoordinator_id(String koordinator_id) {
        this.koordinator_id = koordinator_id;
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
