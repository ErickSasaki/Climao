package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniso.lpdm.climao.seven_days_weather.Daily;
import com.uniso.lpdm.climao.utils.Converter;
import com.uniso.lpdm.climao.utils.IconChange;
import com.uniso.lpdm.climao.utils.Storage;
import com.uniso.lpdm.climao.utils.Translator;

import java.sql.Timestamp;
import java.util.Date;

public class NextDaysWeather extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_days_weather);
        changeScreen();
    }

    // Chama a request e popula os campos/imagens.
    private void changeScreen() {

        TextView cityView = (TextView) findViewById(R.id.textView8);

        cityView.setText(Storage.getLocation());

        //TextViews do dia da semana.
        final TextView[] days = {
                (TextView) findViewById(R.id.textView9),
                (TextView) findViewById(R.id.textView10),
                (TextView) findViewById(R.id.textView11),
                (TextView) findViewById(R.id.textView12),
                (TextView) findViewById(R.id.textView13),
                (TextView) findViewById(R.id.textView14),
                (TextView) findViewById(R.id.textView15),
        };

        // TextViews com as temperaturas mínimas de cada dia.
        final TextView[] min = {
                (TextView) findViewById(R.id.textView16),
                (TextView) findViewById(R.id.textView17),
                (TextView) findViewById(R.id.textView18),
                (TextView) findViewById(R.id.textView19),
                (TextView) findViewById(R.id.textView20),
                (TextView) findViewById(R.id.textView21),
                (TextView) findViewById(R.id.textView22),
        };

        // TextViews com as temperaturas máximas de cada dia.
        final TextView[] max = {
                (TextView) findViewById(R.id.textView23),
                (TextView) findViewById(R.id.textView24),
                (TextView) findViewById(R.id.textView25),
                (TextView) findViewById(R.id.textView26),
                (TextView) findViewById(R.id.textView27),
                (TextView) findViewById(R.id.textView28),
                (TextView) findViewById(R.id.textView29),
        };

        final ImageView[] icons = {
                (ImageView) findViewById(R.id.imageView4),
                (ImageView) findViewById(R.id.imageView5),
                (ImageView) findViewById(R.id.imageView6),
                (ImageView) findViewById(R.id.imageView7),
                (ImageView) findViewById(R.id.imageView8),
                (ImageView) findViewById(R.id.imageView9),
                (ImageView) findViewById(R.id.imageView10),
        };

        Daily[] daily = Storage.getInstance().getDaily();

        // A variavel i começa em 1 pois o endpoint retorna o dia atual primeiro, e não precisamos dele aqui.
        for (int i = 1; i < daily.length; i++) {
            // Pega a data e coloca na variavel date
            Timestamp timestamp = new Timestamp(daily[i].getDt());
            Date date = new Date(timestamp.getTime() * 1000);

            // i - 1 pois o i do for está começando em 1.
            days[i - 1].setText(Translator.DaysOfTheWeekTranslator(date.getDay()));
            min[i - 1].setText(Integer.toString(Converter.kelvinToCelcius(daily[i].getTemp().getMin())) + 'º');
            max[i - 1].setText(Integer.toString(Converter.kelvinToCelcius(daily[i].getTemp().getMax())) + 'º');
            icons[i - 1].setImageResource(IconChange.miniIconsChange(daily[i].getWeather()[0]));
        }

    }

    public void navigateBack(View view) {
        Intent navigateBack = new Intent(NextDaysWeather.this, Home.class);
        startActivity(navigateBack);
    }

    public void navigateNext(View view) {
        Intent navigateBack = new Intent(NextDaysWeather.this, HourlyWeather.class);
        startActivity(navigateBack);
    }

}