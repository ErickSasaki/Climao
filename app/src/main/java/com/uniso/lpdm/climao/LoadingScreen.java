package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;

import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.seven_days_weather.SevenDaysWeather;
import com.uniso.lpdm.climao.utils.Storage;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingScreen extends AppCompatActivity {

    private static int loadingTime = 3000;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    // Serão feitas requests na atividade de loading e salvos na classe storage, esses dados serão usados nas próximas atividades.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        // Configura a request passando o parâmetro da cidade.
        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather("sorocaba");

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<WeatherByCity>() {
            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
            @Override
            public void onResponse(Call<WeatherByCity> call, Response<WeatherByCity> response) {
                // Salva os dados no stotage.
                Storage.getInstance().setWeatherByCity(response.body());

                Float lat = response.body().getCoord().getLat();
                Float lon = response.body().getCoord().getLon();

                // Chama a próxima request que usa os dados de latitude e longitude.
                callNextDaysWeather(lat, lon);
                callHourlyWeather(lat, lon);
            }

            // Caso a request de erro mostra no log.
            @Override
            public void onFailure(Call<WeatherByCity> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });

        navigate();

    }

    private void callNextDaysWeather(Float lat, Float lon) {
        // Configura a request passando o parâmetro da cidade.
        Call<SevenDaysWeather> call = new RetrofitConfig().endpoints().getSevenDaysWeather(lat, lon);

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<SevenDaysWeather>() {

            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
            @Override
            public void onResponse(Call<SevenDaysWeather> call, Response<SevenDaysWeather> response) {
                Storage.getInstance().setDaily(response.body().getDaily());
            }

            // Caso a request de erro mostra no log.
            @Override
            public void onFailure(Call<SevenDaysWeather> call, Throwable t) {
                Log.d("error", t.toString());
            }

        });
    }

    private void callHourlyWeather(double lat, double lon) {
// Configura a request passando o parâmetro da cidade.
        Call<SevenDaysWeather> call = new RetrofitConfig().endpoints().getNextHoursWeather(lat, lon);

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<SevenDaysWeather>() {

            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
            @Override
            public void onResponse(Call<SevenDaysWeather> call, Response<SevenDaysWeather> response) {
                Storage.getInstance().setHourly(response.body().getHourly());
            }

            // Caso a request de erro mostra no log.
            @Override
            public void onFailure(Call<SevenDaysWeather> call, Throwable t) {
                Log.d("error", t.toString());
            }

        });
    }

    private void navigate() {
        // Após um tempo definido na variavel loadingTime muda para a atividade "home".
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent navigateToHome = new Intent(LoadingScreen.this, Home.class);
                startActivity(navigateToHome);
                finish();
            }
        }, loadingTime);
    }
}