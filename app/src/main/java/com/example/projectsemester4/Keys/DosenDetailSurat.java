package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class DosenDetailSurat {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("nip")
    private String nip;
    @SerializedName("nama")
    private String nama;
    @SerializedName("prodi_id")
    private String prodi_id;

    public DosenDetailSurat(String uuid, String nip, String nama, String prodi_id) {
        this.uuid = uuid;
        this.nip = nip;
        this.nama = nama;
        this.prodi_id = prodi_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi_id() {
        return prodi_id;
    }

    public void setProdi_id(String prodi_id) {
        this.prodi_id = prodi_id;
    }
}
