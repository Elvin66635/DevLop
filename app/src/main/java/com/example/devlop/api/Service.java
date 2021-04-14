package com.example.devlop.api;

import com.example.devlop.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("society")
    Call<List<News>> getNews();


}
