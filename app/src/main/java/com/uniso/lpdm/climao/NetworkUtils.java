package com.uniso.lpdm.climao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    public static Retrofit getRetrofitInstance(String path) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
