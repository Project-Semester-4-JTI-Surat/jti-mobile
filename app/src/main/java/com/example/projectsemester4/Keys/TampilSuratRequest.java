package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class TampilSuratRequest {
    @SerializedName("uuid")
    private String uuid;

    @SerializedName("kode_surat")
    private String kode_surat;

    @SerializedName("keterangan")
    private String keterangan;

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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
