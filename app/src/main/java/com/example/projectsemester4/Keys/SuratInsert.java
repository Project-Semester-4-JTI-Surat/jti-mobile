package com.example.projectsemester4.Keys;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SuratInsert {
    @POST("/api/mahasiswa/surat/insert")
    Call<SuratRequest> insertSurat(@Header("Authorization") String token, @Body SuratRequest suratRequest);

    @GET("/api/data/dosen")
    Call<DosenResponse> getDataDosen();
    @GET("/api/data/jenis_surat")
    Call<JenisSuratResponse> getDataJenisSurat();
    @GET("/api/data/koordinator")
    Call<KoordinatorResponse> getDataKoordinator();

}
