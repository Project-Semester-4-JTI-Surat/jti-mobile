package com.example.projectsemester4.Keys;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(final Context context){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        MyPreferences preferences = new MyPreferences(context);
                        String token = preferences.getString("token", "");
                        if (!token.isEmpty()) {
                            request = request.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.18.175:8000/")
                .client(client)
                .build();

        return retrofit;
    }

    public static UserService getUserService(final Context context){
        UserService userService = getRetrofit(context).create(UserService.class);

        return userService;
    }
    public static UserService2 getUserService2(final Context context){
        UserService2 userService2 = getRetrofit(context).create(UserService2.class);

        return userService2;
    }

    public static SuratInsert getSuratInsert(final Context context){
        SuratInsert suratInsert = getRetrofit(context).create(SuratInsert.class);

        return suratInsert;
    }
    public static Call<LoginResponse> getUserProfile(Context context) {
        MyPreferences preferences = new MyPreferences(context);
        String token = preferences.getString("token", "");
        UserService userService = getRetrofit(context).create(UserService.class);
        return userService.getUserProfile("Bearer " + token);
    }


}

