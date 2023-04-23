package com.example.projectsemester4.Keys;

public class SuratRequest {
    String kode_surat, dosen_id, kode_koordinator, nama_mitra, alamat_mitra, keterangan, softfile_scan;
    String tanggal_dibuat, tanggal_pelaksanaan, tanggal_selesai;

    public SuratRequest(String kode_surat, String dosen_id, String kode_koordinator, String nama_mitra, String alamat_mitra, String keterangan, String softfile_scan, String tanggal_dibuat, String tanggal_pelaksanaan, String tanggal_selesai) {
        this.kode_surat = kode_surat;
        this.dosen_id = dosen_id;
        this.kode_koordinator = kode_koordinator;
        this.nama_mitra = nama_mitra;
        this.alamat_mitra = alamat_mitra;
        this.keterangan = keterangan;
        this.softfile_scan = softfile_scan;
        this.tanggal_dibuat = tanggal_dibuat;
        this.tanggal_pelaksanaan = tanggal_pelaksanaan;
        this.tanggal_selesai = tanggal_selesai;
    }

    public enum kebutuhan{
        Eksternal("Eksternal"),
        Internal("Internal");

        private String text;

        private kebutuhan(String text){
            this.text = text;
        }
        public String getText(){
            return text;
        }
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

    public String getKode_koordinator() {
        return kode_koordinator;
    }

    public void setKode_koordinator(String kode_koordinator) {
        this.kode_koordinator = kode_koordinator;
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
}
