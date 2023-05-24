package com.example.projectsemester4.Keys;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TampilSurat {
    @GET("/api/mahasiswa/surat")
    Call<TampilSuratResponse> getTampilSurat(@Header("Authorization") String token);
}
