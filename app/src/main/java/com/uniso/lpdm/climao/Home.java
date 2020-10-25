package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.uniso.lpdm.climao.weather.WeatherByCity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView textView = (TextView) findViewById(R.id.test);
        textView.setText("new text!");

        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather("sorocaba");

        call.enqueue(new Callback<WeatherByCity>() {
            @Override
            public void onResponse(Call<WeatherByCity> call, Response<WeatherByCity> response) {
                textView.setText("Country: " + response.body().getSys().getCountry());
            }

            @Override
            public void onFailure(Call<WeatherByCity> call, Throwable t) {
                textView.setText(t.toString());
            }
        });
    }
}