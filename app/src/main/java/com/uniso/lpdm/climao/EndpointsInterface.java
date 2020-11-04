package com.uniso.lpdm.climao;

import com.uniso.lpdm.climao.seven_days_weather.SevenDaysWeather;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndpointsInterface {
    // Endpoint que pega o clima atual pela cidade, "appid" é a chave de autenticação da api, "q" é a cidade.
    @GET("/data/2.5/weather?appid=62587636619b9bcf65a526bbedfe0b7d")
    Call<WeatherByCity> getWeather(@Query("q") String q);

    // Endpoint que pega o clima dos próximos 7 dias.
    @GET("/data/2.5/onecall?exclude=hourly,minutely&appid=b89957355275f5d504bb0b13eba1886d")
    Call<SevenDaysWeather> getSevenDaysWeather(@Query("lat") double lat, @Query("lon") double lon);
}
