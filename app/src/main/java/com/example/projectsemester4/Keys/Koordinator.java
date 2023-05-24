package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class Koordinator {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("nama")
    private String nama;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("kode_surat")
    private String kode_surat;
    @SerializedName("email")
    private String email;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getKode_surat() {
        return kode_surat;
    }

    public void setKode_surat(String kode_surat) {
        this.kode_surat = kode_surat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
