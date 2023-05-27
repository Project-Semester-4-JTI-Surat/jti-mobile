package com.example.projectsemester4.Keys;

public class AnggotaDetailSurat {
    private String nama;
    private String nim;

    public AnggotaDetailSurat(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
