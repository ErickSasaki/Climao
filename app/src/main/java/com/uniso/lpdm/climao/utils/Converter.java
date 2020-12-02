package com.uniso.lpdm.climao.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.util.Calendar;

public class Converter {

    public static int kelvinToCelcius(double kelvinNumber) {
        return (int) (kelvinNumber - 273.15);
    }

    public static int timestampToHour(long time) {
        // Pega a data e coloca na variavel date
        Timestamp timestamp = new Timestamp(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime() * 1000);

        // Tentei diversas classes Java pra formatar o timestamp de uma forma decente.
        // O DateFormat chegou perto mas acredito que esteja com problemas em relação ao horario de verão.
        // Como o foco não é esse só coloquei um -3 na hora pra pegar o horario de brasilia.
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 3) {
            hour = 21 + hour;
        } else {
            hour = hour - 3;
        }

        return hour;
    }

}
