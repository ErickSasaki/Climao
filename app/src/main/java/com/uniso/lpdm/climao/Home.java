package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniso.lpdm.climao.weather.WeatherByCity;

import java.text.DecimalFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        final TextView graus = (TextView) findViewById(R.id.graus);
        final TextView cidade = (TextView) findViewById(R.id.cidade);
        final TextView umidade = (TextView) findViewById(R.id.umidade);
        final TextView clima = (TextView) findViewById(R.id.clima);
        final TextView vento = (TextView) findViewById(R.id.vento);

        // Faz a request e popula o textView
        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather("sorocaba");

        call.enqueue(new Callback<WeatherByCity>() {
            @Override
            public void onResponse(Call<WeatherByCity> call, Response<WeatherByCity> response) {
                graus.setText((int) (response.body().getMain().getTemp() - 273.15) + "°C");
                cidade.setText("em " + response.body().getName());

                umidade.setText("Umidade: " + response.body().getMain().getHumidity() + "%");
                clima.setText("Clima: " + response.body().getWeather()[0].getMain());
                vento.setText("Vento: " + decimalFormat.format(response.body().getWind().getSpeed() * 3.6) + "km/h");

            }

            @Override
            public void onFailure(Call<WeatherByCity> call, Throwable t) {
                graus.setText(t.toString());
            }
        });

        // Verifica a hora do dia para trocar o icone.
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        ImageView tempo = (ImageView) findViewById(R.id.tempo);
        if (hour >= 6 && hour <= 19 ) {
            tempo.setImageResource(R.drawable.sol_poucas_nuvens);
        } else {
            tempo.setImageResource(R.drawable.lua_poucas_nuvens);
        }
    }
}