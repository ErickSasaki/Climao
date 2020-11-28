package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.seven_days_weather.Daily;
import com.uniso.lpdm.climao.seven_days_weather.Hourly;
import com.uniso.lpdm.climao.seven_days_weather.SevenDaysWeather;
import com.uniso.lpdm.climao.utils.Converter;
import com.uniso.lpdm.climao.utils.IconChange;
import com.uniso.lpdm.climao.utils.Translator;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlyWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);

        callResquest();
    }

    private void callResquest() {
        // Configura a request passando o parâmetro da cidade.
        Call<SevenDaysWeather> call = new RetrofitConfig().endpoints().getNextHoursWeather(-23.5, -47.46);

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<SevenDaysWeather>() {
            @Override
            public void onResponse(Call<SevenDaysWeather> call, Response<SevenDaysWeather> response) {
                // Apenas para facilitar o acesso do getDaily.
                Hourly[] hourly = response.body().getHourly();

                changeScreen(hourly);

            }

            @Override
            public void onFailure(Call<SevenDaysWeather> call, Throwable t) {
                Log.d("Teste2", "deu ruim! Erro: " + t.toString());
            }
        });
    }

    // Mudará os valores e icones da tela baseado na resposta da api.
    private void changeScreen(Hourly[] hourly) {

        TextView[] temperatureViews = {
                (TextView) findViewById(R.id.textView20),
                (TextView) findViewById(R.id.textView21),
                (TextView) findViewById(R.id.textView22),
                (TextView) findViewById(R.id.textView23),
                (TextView) findViewById(R.id.textView24),
                (TextView) findViewById(R.id.textView25),
                (TextView) findViewById(R.id.textView26),
                (TextView) findViewById(R.id.textView27),
                (TextView) findViewById(R.id.textView28),
                (TextView) findViewById(R.id.textView29),
                (TextView) findViewById(R.id.textView30),
                (TextView) findViewById(R.id.textView31),
        };

        ImageView[] icons = {
                (ImageView) findViewById(R.id.imageView6),
                (ImageView) findViewById(R.id.imageView7),
                (ImageView) findViewById(R.id.imageView8),
                (ImageView) findViewById(R.id.imageView9),
                (ImageView) findViewById(R.id.imageView10),
                (ImageView) findViewById(R.id.imageView11),
                (ImageView) findViewById(R.id.imageView12),
                (ImageView) findViewById(R.id.imageView13),
                (ImageView) findViewById(R.id.imageView14),
                (ImageView) findViewById(R.id.imageView15),
                (ImageView) findViewById(R.id.imageView16),
                (ImageView) findViewById(R.id.imageView17),
        };

        TextView[] hourViews = {
                (TextView) findViewById(R.id.textView40),
                (TextView) findViewById(R.id.textView34),
                (TextView) findViewById(R.id.textView35),
                (TextView) findViewById(R.id.textView36),
                (TextView) findViewById(R.id.textView44),
                (TextView) findViewById(R.id.textView38),
                (TextView) findViewById(R.id.textView39),
                (TextView) findViewById(R.id.textView37),
                (TextView) findViewById(R.id.textView33),
                (TextView) findViewById(R.id.textView42),
                (TextView) findViewById(R.id.textView43),
                (TextView) findViewById(R.id.textView41),
        };

        for (int i = 0; i < 24; i++) {

            int hour = Converter.timestampToHour(hourly[i].getDt());

            if (hour % 2 == 0) {

                int temp = Converter.kelvinToCelcius(hourly[i].getTemp());

                // Muda a cor do texto (vermelho quando temperatura acima de 20 e azul caso contrario).
                if (temp > 20) {
                    temperatureViews[i / 2].setTextColor(Color.parseColor("#E76158"));
                } else {
                    temperatureViews[i / 2].setTextColor(Color.parseColor("#26FCFC"));
                }

                temperatureViews[i / 2].setText(Integer.toString(Converter.kelvinToCelcius(hourly[i].getTemp())) + "ºC");

                hourViews[i / 2].setText(Integer.toString(hour) + "h");
                icons[i / 2].setImageResource(IconChange.miniIconsChange(hourly[i].getWeather()[0]));

            }

        }
    }
}