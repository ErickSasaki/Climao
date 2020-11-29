package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.utils.IconChange;
import com.uniso.lpdm.climao.utils.MainMessages;
import com.uniso.lpdm.climao.utils.Translator;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import java.text.DecimalFormat;

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
                clima.setText("Clima: " + Translator.WeatherTranslator(response.body().getWeather()[0].getMain()));
                vento.setText("Vento: " + decimalFormat.format(response.body().getWind().getSpeed() * 3.6) + "km/h");

                ImageView tempo = (ImageView) findViewById(R.id.tempo);

                tempo.setImageResource(IconChange.mainIconsChange(response.body().getWeather()[0]));

                TextView messagesView[] = {
                    (TextView) findViewById(R.id.textView6),
                    (TextView) findViewById(R.id.textView5),
                    (TextView) findViewById(R.id.textView4),
                    (TextView) findViewById(R.id.textView46),
                };

                ImageView iconsView[] = {
                        (ImageView) findViewById(R.id.imageView25),
                        (ImageView) findViewById(R.id.imageView27),
                        (ImageView) findViewById(R.id.imageView28),
                        (ImageView) findViewById(R.id.imageView26),
                };

                String[] messages = MainMessages.getMessages(response.body().getWeather()[0], response.body().getMain());
                int[] icons = MainMessages.getMessageIcon(response.body().getWeather()[0], response.body().getMain());

                for (int i = 0; i < 4; i++) {
                    messagesView[i].setText(messages[i]);
                    iconsView[i].setImageResource(icons[i]);
                }

            }

            // Caso a request de erro define o textView para mostrar o erro.
            // Ainda falta implementar uma maneira melhor de tratar os erros.
            @Override
            public void onFailure(Call<WeatherByCity> call, Throwable t) {
                graus.setText(t.toString());
            }
        });
    }

    /**
     * Navega para próxima página.
     */
    public void navigate(View view) {
        Intent navigateToNextDaysWeather = new Intent(Home.this, NextDaysWeather.class);
        startActivity(navigateToNextDaysWeather);
    }
}