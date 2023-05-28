package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailSuratRequest {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("kode_surat")
    private String kode_surat;
    @SerializedName("prodi_id")
    private int prodi_id;
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
    @SerializedName("judul_ta")
    private String judul_ta;
    @SerializedName("kebutuhan")
    private String kebutuhan;
    @SerializedName("alasan_penolakan")
    private String alasan_penolakan;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("softfike_scan")
    private String softfile_scan;
    @SerializedName("anggota")
    private List<AnggotaDetailSurat> anggota;
    @SerializedName("dosen")
    private DosenDetailSurat dosen;
    @SerializedName("koordinator")
    private KoordinatorDetailSurat koordinator;

    public DetailSuratRequest(String uuid, String kode_surat, int prodi_id, String nama_mitra, String alamat_mitra, String tanggal_dibuat, String tanggal_pelaksanaan, String tanggal_selesai, String judul_ta, String kebutuhan, String alasan_penolakan, String keterangan, String softfile_scan, List<AnggotaDetailSurat> anggota, DosenDetailSurat dosen, KoordinatorDetailSurat koordinator) {
        this.uuid = uuid;
        this.kode_surat = kode_surat;
        this.prodi_id = prodi_id;
        this.nama_mitra = nama_mitra;
        this.alamat_mitra = alamat_mitra;
        this.tanggal_dibuat = tanggal_dibuat;
        this.tanggal_pelaksanaan = tanggal_pelaksanaan;
        this.tanggal_selesai = tanggal_selesai;
        this.judul_ta = judul_ta;
        this.kebutuhan = kebutuhan;
        this.alasan_penolakan = alasan_penolakan;
        this.keterangan = keterangan;
        this.softfile_scan = softfile_scan;
        this.anggota = anggota;
        this.dosen = dosen;
        this.koordinator = koordinator;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKode_surat() {
        return kode_surat;
    }

    public void setKode_surat(String kode_surat) {
        this.kode_surat = kode_surat;
    }

    public int getProdi_id() {
        return prodi_id;
    }

    public void setProdi_id(int prodi_id) {
        this.prodi_id = prodi_id;
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

    public String getJudul_ta() {
        return judul_ta;
    }

    public void setJudul_ta(String judul_ta) {
        this.judul_ta = judul_ta;
    }

    public String getKebutuhan() {
        return kebutuhan;
    }

    public void setKebutuhan(String kebutuhan) {
        this.kebutuhan = kebutuhan;
    }

    public String getAlasan_penolakan() {
        return alasan_penolakan;
    }

    public void setAlasan_penolakan(String alasan_penolakan) {
        this.alasan_penolakan = alasan_penolakan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getSoftfile_scan() {
        return softfile_scan;
    }

    public void setSoftfile_scan(String softfile_scan) {
        this.softfile_scan = softfile_scan;
    }

    public List<AnggotaDetailSurat> getAnggota() {
        return anggota;
    }

    public void setAnggota(List<AnggotaDetailSurat> anggota) {
        this.anggota = anggota;
    }

    public DosenDetailSurat getDosen() {
        return dosen;
    }

    public void setDosen(DosenDetailSurat dosen) {
        this.dosen = dosen;
    }

    public KoordinatorDetailSurat getKoordinator() {
        return koordinator;
    }

    public void setKoordinator(KoordinatorDetailSurat koordinator) {
        this.koordinator = koordinator;
    }
}
