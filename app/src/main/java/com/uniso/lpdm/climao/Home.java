package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniso.lpdm.climao.utils.IconChange;
import com.uniso.lpdm.climao.utils.MainMessages;
import com.uniso.lpdm.climao.utils.Storage;
import com.uniso.lpdm.climao.utils.Translator;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import java.text.DecimalFormat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        WeatherByCity weather = Storage.getInstance().getWeatherByCity();

        // Classe de formatação de decimal.
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // Definido variaveis que irão conter as textViews que serão manipuladas na atividade.
        final TextView graus = (TextView) findViewById(R.id.graus);
        final TextView cidade = (TextView) findViewById(R.id.cidade);
        final TextView umidade = (TextView) findViewById(R.id.umidade);
        final TextView clima = (TextView) findViewById(R.id.clima);
        final TextView vento = (TextView) findViewById(R.id.vento);

        graus.setText((int)(weather.getMain().getTemp() - 273.15) + "°C");
        cidade.setText("em " + weather.getName());
        umidade.setText("Umidade: " + weather.getMain().getHumidity() + "%");
        clima.setText("Clima: " + Translator.WeatherTranslator(weather.getWeather()[0].getMain()));
        vento.setText("Vento: " + decimalFormat.format(weather.getWind().getSpeed() * 3.6) + "km/h");

        ImageView tempo = (ImageView) findViewById(R.id.tempo);

        tempo.setImageResource(IconChange.mainIconsChange(weather.getWeather()[0]));

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

        String[] messages = MainMessages.getMessages(weather.getWeather()[0], weather.getMain());
        int[] icons = MainMessages.getMessageIcon(weather.getWeather()[0], weather.getMain());

        for (int i = 0; i < 4; i++) {
            messagesView[i].setText(messages[i]);
            iconsView[i].setImageResource(icons[i]);
        }
    }

    /**
     * Navega para próxima página.
     */
    public void navigate(View view) {
        Intent navigateToNextDaysWeather = new Intent(Home.this, NextDaysWeather.class);
        startActivity(navigateToNextDaysWeather);
    }
}