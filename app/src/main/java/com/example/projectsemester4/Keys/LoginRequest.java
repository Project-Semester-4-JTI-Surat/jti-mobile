package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("nim")
    private String nim;

    @SerializedName("nama")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("tanggal_lahir")
    private String tanggalLahir;

    @SerializedName("prodi")
    private Prodi prodi;

    @SerializedName("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public Prodi getProdi() {
        return prodi;
    }
}
