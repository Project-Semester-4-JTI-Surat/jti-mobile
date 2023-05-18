package com.example.projectsemester4;

public class DataSurat {
    private String mataKuliah;
    private String namaMhs;

    public DataSurat(String mataKuliah, String namaMhs) {
        this.mataKuliah = mataKuliah;
        this.namaMhs = namaMhs;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getNamaMhs() {
        return namaMhs;
    }

    public void setNamaMhs(String namaMhs) {
        this.namaMhs = namaMhs;
    }
}

