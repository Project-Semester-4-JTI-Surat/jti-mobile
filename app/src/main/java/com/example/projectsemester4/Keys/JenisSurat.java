package com.example.projectsemester4.Keys;

import com.google.gson.annotations.SerializedName;

public class JenisSurat {
    @SerializedName("kode")
    private String kode;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("template")
    private String template;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
