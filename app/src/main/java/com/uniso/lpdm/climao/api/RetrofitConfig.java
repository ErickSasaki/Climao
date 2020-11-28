package com.uniso.lpdm.climao.api;

import com.uniso.lpdm.climao.api.EndpointsInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Classe de configuração do Retrofit pra fazer requests.
public class RetrofitConfig {

    private Retrofit retrofit;

    // Definido a configuração base do retrofit com a url da api.
    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Define os endpoints que serão usados na request usando a interface EndpointsInterface.
    public EndpointsInterface endpoints() {
        return this.retrofit.create(EndpointsInterface.class);
    }

}
