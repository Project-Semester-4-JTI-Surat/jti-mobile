package com.example.projectsemester4.Keys;

public class AnggotaModel {
    private String nim;
    private String nama;
    private String prodi;
    private String tlp;

    public AnggotaModel(String nim, String nama, String prodi, String tlp) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
        this.tlp = tlp;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getTlp() {
        return tlp;
    }

    public void setTlp(String tlp) {
        this.tlp = tlp;
    }
}
