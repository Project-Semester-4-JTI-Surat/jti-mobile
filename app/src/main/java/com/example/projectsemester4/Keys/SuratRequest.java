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

    @SerializedName("kode_surat")
    private String kodeSurat;
    private String namaDosen;
//    @SerializedName("status_id")
//    private int statusId;
//    @SerializedName("prodi_id")
//    private int prodiId;
//    @SerializedName("dosen_id")
//    private int dosenId;
//    @SerializedName("kode_koordinator")
//    private String kodeKoordinator;
    @SerializedName("nama_mitra")
    private String namaMitra;
    @SerializedName("alamat_mitra")
    private String alamatMitra;
    @SerializedName("tanggal_dibuat")
    private String tanggalDibuat;
//    @SerializedName("tanggal_pelaksanaan")
//    private String tanggalPelaksanaan;
//    @SerializedName("tanggal_selesai")
//    private String tanggalSelesai;
    @SerializedName("judul_ta")
    private String judulTa;
    @SerializedName("kebutuhan")
    private String kebutuhan;
    @SerializedName("keterangan")
    private String keterangan;
    private List<AnggotaModel> anggotaList;

    // constructor dan getter/setter di sini
//    String kodeSurat, int statusId, int prodiId, int dosenId, String kodeKoordinator, String namaMitra, String alamatMitra, String tanggalDibuat, String tanggalPelaksanaan, String tanggalSelesai, String judulTa, String kebutuhan, String keterangan, List<String> namaAnggota, List<String> nimAnggota, List<String> nohpAnggota, List<Integer> prodiIdAnggota


    public SuratRequest(String kodeSurat, String namaDosen, String namaMitra, String alamatMitra, String tanggalDibuat, String kebutuhan, String keterangan, List<AnggotaModel> anggotaList) {
        this.kodeSurat = kodeSurat;
        this.namaDosen = namaDosen;
        this.namaMitra = namaMitra;
        this.alamatMitra = alamatMitra;
        this.tanggalDibuat = tanggalDibuat;
//        this.judulTa = judulTa;
        this.kebutuhan = kebutuhan;
        this.keterangan = keterangan;
        this.anggotaList = anggotaList;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }

    public List<AnggotaModel> getAnggotaList() {
        return anggotaList;
    }

    public void setAnggotaList(List<AnggotaModel> anggotaList) {
        this.anggotaList = anggotaList;
    }

    public String getKodeSurat() {
        return kodeSurat;
    }

    public void setKodeSurat(String kodeSurat) {
        this.kodeSurat = kodeSurat;
    }

    public String getNamaMitra() {
        return namaMitra;
    }

    public void setNamaMitra(String namaMitra) {
        this.namaMitra = namaMitra;
    }

    public String getAlamatMitra() {
        return alamatMitra;
    }

    public void setAlamatMitra(String alamatMitra) {
        this.alamatMitra = alamatMitra;
    }

    public String getTanggalDibuat() {
        return tanggalDibuat;
    }

    public void setTanggalDibuat(String tanggalDibuat) {
        this.tanggalDibuat = tanggalDibuat;
    }

    public String getJudulTa() {
        return judulTa;
    }

    public void setJudulTa(String judulTa) {
        this.judulTa = judulTa;
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
}
