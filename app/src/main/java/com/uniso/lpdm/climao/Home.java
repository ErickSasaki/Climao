package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import java.text.DecimalFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Classe de formatação de decimal.
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // Definido variaveis que irão conter as textViews que serão manipuladas na atividade.
        final TextView graus = (TextView) findViewById(R.id.graus);
        final TextView cidade = (TextView) findViewById(R.id.cidade);
        final TextView umidade = (TextView) findViewById(R.id.umidade);
        final TextView clima = (TextView) findViewById(R.id.clima);
        final TextView vento = (TextView) findViewById(R.id.vento);

        // Configura a request passando o parâmetro da cidade.
        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather("sorocaba");

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<WeatherByCity>() {
            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
            @Override
            public void onResponse(Call<WeatherByCity> call, Response<WeatherByCity> response) {
                graus.setText((int) (response.body().getMain().getTemp() - 273.15) + "°C");
                cidade.setText("em " + response.body().getName());
                umidade.setText("Umidade: " + response.body().getMain().getHumidity() + "%");
                clima.setText("Clima: " + response.body().getWeather()[0].getMain());
                vento.setText("Vento: " + decimalFormat.format(response.body().getWind().getSpeed() * 3.6) + "km/h");
            }

            // Caso a request de erro define o textView para mostrar o erro.
            // Ainda falta implementar uma maneira melhor de tratar os erros.
            @Override
            public void onFailure(Call<WeatherByCity> call, Throwable t) {
                graus.setText(t.toString());
            }
        });

        // Usa a classe Calendar pra pegar a Hora atual.
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Pega a imagem do icone principal.
        ImageView tempo = (ImageView) findViewById(R.id.tempo);

        // Caso a hora esteja entre 6 e 19 irá mostrar o icone de sol, caso contrário o icone de lua.
        // Ainda falta implementar os icones de tempo.
        if (hour >= 6 && hour <= 19 ) {
            tempo.setImageResource(R.drawable.sol_poucas_nuvens);
        } else {
            tempo.setImageResource(R.drawable.lua_poucas_nuvens);
        }
    }
}