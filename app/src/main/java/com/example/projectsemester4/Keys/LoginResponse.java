package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LoginResponse {

    @SerializedName("token")
    private String token;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("nim")
    private String nim;
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;
    @SerializedName("prodi_id")
    private int prodi_id;
    @SerializedName("password")
    private String password;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("tanggal_lahir")
    private String tanggal_lahir;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProdi_id() {
        return prodi_id;
    }

    public void setProdi_id(int prodi_id) {
        this.prodi_id = prodi_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }
}
