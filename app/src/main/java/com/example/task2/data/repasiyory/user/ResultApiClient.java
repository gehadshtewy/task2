package com.example.task2.data.repasiyory.user;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultApiClient {
    public static String BASE_URL="https://randomuser.me/";
    public static Retrofit getClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
