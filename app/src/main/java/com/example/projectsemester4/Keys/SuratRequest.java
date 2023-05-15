package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuratRequest {
    @SerializedName("kode_surat")
    private String kodeSurat;
    @SerializedName("status_id")
    private int statusId;
    @SerializedName("prodi_id")
    private int prodiId;
    @SerializedName("dosen_id")
    private int dosenId;
    @SerializedName("kode_koordinator")
    private String kodeKoordinator;
    @SerializedName("nama_mitra")
    private String namaMitra;
    @SerializedName("alamat_mitra")
    private String alamatMitra;
    @SerializedName("tanggal_dibuat")
    private String tanggalDibuat;
    @SerializedName("tanggal_pelaksanaan")
    private String tanggalPelaksanaan;
    @SerializedName("tanggal_selesai")
    private String tanggalSelesai;
    @SerializedName("judul_ta")
    private String judulTa;
    @SerializedName("kebutuhan")
    private String kebutuhan;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("nama_anggota")
    private List<String> namaAnggota;
    @SerializedName("nim_anggota")
    private List<String> nimAnggota;
    @SerializedName("nohp_anggota")
    private List<String> nohpAnggota;
    @SerializedName("prodi_id_anggota")
    private List<Integer> prodiIdAnggota;

    // constructor dan getter/setter di sini

    public SuratRequest(String kodeSurat, int statusId, int prodiId, int dosenId, String kodeKoordinator, String namaMitra, String alamatMitra, String tanggalDibuat, String tanggalPelaksanaan, String tanggalSelesai, String judulTa, String kebutuhan, String keterangan, List<String> namaAnggota, List<String> nimAnggota, List<String> nohpAnggota, List<Integer> prodiIdAnggota) {
        this.kodeSurat = kodeSurat;
        this.statusId = statusId;
        this.prodiId = prodiId;
        this.dosenId = dosenId;
        this.kodeKoordinator = kodeKoordinator;
        this.namaMitra = namaMitra;
        this.alamatMitra = alamatMitra;
        this.tanggalDibuat = tanggalDibuat;
        this.tanggalPelaksanaan = tanggalPelaksanaan;
        this.tanggalSelesai = tanggalSelesai;
        this.judulTa = judulTa;
        this.kebutuhan = kebutuhan;
        this.keterangan = keterangan;
        this.namaAnggota = namaAnggota;
        this.nimAnggota = nimAnggota;
        this.nohpAnggota = nohpAnggota;
        this.prodiIdAnggota = prodiIdAnggota;
    }

    public String getKodeSurat() {
        return kodeSurat;
    }

    public void setKodeSurat(String kodeSurat) {
        this.kodeSurat = kodeSurat;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getProdiId() {
        return prodiId;
    }

    public void setProdiId(int prodiId) {
        this.prodiId = prodiId;
    }

    public int getDosenId() {
        return dosenId;
    }

    public void setDosenId(int dosenId) {
        this.dosenId = dosenId;
    }

    public String getKodeKoordinator() {
        return kodeKoordinator;
    }

    public void setKodeKoordinator(String kodeKoordinator) {
        this.kodeKoordinator = kodeKoordinator;
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

    public String getTanggalPelaksanaan() {
        return tanggalPelaksanaan;
    }

    public void setTanggalPelaksanaan(String tanggalPelaksanaan) {
        this.tanggalPelaksanaan = tanggalPelaksanaan;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
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

    public List<String> getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(List<String> namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public List<String> getNimAnggota() {
        return nimAnggota;
    }

    public void setNimAnggota(List<String> nimAnggota) {
        this.nimAnggota = nimAnggota;
    }

    public List<String> getNohpAnggota() {
        return nohpAnggota;
    }

    public void setNohpAnggota(List<String> nohpAnggota) {
        this.nohpAnggota = nohpAnggota;
    }

    public List<Integer> getProdiIdAnggota() {
        return prodiIdAnggota;
    }

    public void setProdiIdAnggota(List<Integer> prodiIdAnggota) {
        this.prodiIdAnggota = prodiIdAnggota;
    }
}
