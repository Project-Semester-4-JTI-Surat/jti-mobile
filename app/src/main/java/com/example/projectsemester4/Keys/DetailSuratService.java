package com.example.projectsemester4.Keys;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DetailSuratService {
    @GET("/api/mahasiswa/surat/{id}")
    Call<DetailSuratResponse> getDetailSurat(@Header("Authorization") String token, @Path("id") String id);
}
