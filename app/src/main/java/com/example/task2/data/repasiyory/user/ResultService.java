package com.example.task2.data.repasiyory.user;

import com.example.task2.data.model.user.Results;
import com.example.task2.data.model.user.Root;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ResultService {
    @GET("/api")
    public  Call<Root> getResults();
}
