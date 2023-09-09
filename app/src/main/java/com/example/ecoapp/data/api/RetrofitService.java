package com.example.ecoapp.data.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    private Context ctx;
    public String BASE_URL = "https://ecoappserver.up.railway.app";

    public RetrofitService() {
    }

    public RetrofitService(Context ctx) {
        this.ctx = ctx;
    }

    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }


    public Retrofit create() {
        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(getHttpLoggingInterceptor());

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client.build())
                .build();
    }

    public Retrofit getInstance() {
        if (retrofit == null) retrofit = create();
        return retrofit;
    }
}