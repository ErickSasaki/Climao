package com.uniso.lpdm.climao.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.uniso.lpdm.climao.R;
import com.uniso.lpdm.climao.weather.Main;
import com.uniso.lpdm.climao.weather.Weather;

import java.util.Calendar;

public class IconChange {

    /**
     * Recebe o retorno da api "Weather" e retorna o icone correspondente ao clima.
     * @param weather Objeto contendo informações do clima.
     * @return Icone
     */
    public static int miniIconsChange(Weather weather) {
        String main = weather.getMain();
        String description = weather.getDescription();
        Boolean sol = true;

        // Usa a classe Calendar pra pegar a Hora atual.
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Caso seja noite define sol = false, usado para trocar icone de sol para lua.
        if (hour < 6 && hour > 19 ) {
            sol = false;
        }

        if (main.equals("Thunderstorm")) {
            return R.drawable.trovao;
        }

        if (main.equals("Rain") || main.equals("Drizzle")) {
            return R.drawable.chuva;
        }

        if (main.equals("Snow")) {
            return R.drawable.neve;
        }

        if (main.equals("Clear") && sol) {
            return R.drawable.sol;
        }

        if (main.equals("Clear")) {
            return R.drawable.lua;
        }

        if (main.equals("Clouds")) {

            if (description.equals("few clouds")) {
                if (sol) {
                    return R.drawable.sol_nuvens;
                } else {
                    return R.drawable.lua_nuvens;
                }
            }

            else if (description.equals("scattered clouds")) {
                if (sol) {
                    return R.drawable.sol_nuvem;
                } else {
                    return R.drawable.lua_nuvem;
                }
            }

        }

        // Caso não seja nenhum dos alteriores retornará nuvem.
        return R.drawable.nuvem;
    }

    /**
     * Recebe o retorno da api e retorna o icone correspondente ao clima.
     * @param weather Objeto contendo informações do clima.
     * @param main Objeto contendo informações da temperatura e umidade.
     * @return Icone
     */
    public static int mainIconsChange(Weather weather) {
        String weatherMain = weather.getMain();
        String description = weather.getDescription();
        Boolean sol = true;

        // Usa a classe Calendar pra pegar a Hora atual.
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Caso seja noite define sol = false, usado para trocar icone de sol para lua.
        if (hour < 6 && hour > 19 ) {
            sol = false;
        }

        if (weatherMain.equals("Thunderstorm") && sol) {
            return R.drawable.main_trovao_sol;
        }

        if (weatherMain.equals("Thunderstorm")) {
            return R.drawable.main_trovao_lua;
        }

        if ((weatherMain.equals("Rain") || weatherMain.equals("Drizzle")) && sol) {
            return R.drawable.main_chuva_sol;
        }

        if (weatherMain.equals("Rain") || weatherMain.equals("Drizzle")) {
            return R.drawable.main_chuva_lua;
        }

        if (weatherMain.equals("Snow") && sol) {
            return R.drawable.main_nuvem_sol;
        }

        if (weatherMain.equals("Snow")) {
            return R.drawable.main_nuvem_lua;
        }

        if (weatherMain.equals("Clear") && sol) {
            return R.drawable.main_sol;
        }

        if (weatherMain.equals("Clear")) {
            return R.drawable.main_lua;
        }

        if (weatherMain.equals("Clouds")) {

            if (description.equals("few clouds")) {
                if (sol) {
                    return R.drawable.main_nuvens_sol;
                } else {
                    return R.drawable.main_nuvens_lua;
                }
            }

            else if (description.equals("scattered clouds")) {
                if (sol) {
                    return R.drawable.main_nuvem_sol;
                } else {
                    return R.drawable.main_nuvem_lua;
                }
            }

        }

        // Caso não seja nenhum dos alteriores retornará nuvem.
        return R.drawable.main_nuvem_lua;
    }

}
