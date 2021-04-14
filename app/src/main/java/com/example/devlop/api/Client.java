package com.example.devlop.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL = "http://t90375cv.beget.tech/public/api/news/russia/";
    public static Retrofit retrofit = null;
    static Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy'T'HH:mm:ss")
            .create();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
