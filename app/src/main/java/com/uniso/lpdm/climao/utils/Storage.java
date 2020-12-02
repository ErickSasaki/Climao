package com.uniso.lpdm.climao.utils;

import com.uniso.lpdm.climao.seven_days_weather.Daily;
import com.uniso.lpdm.climao.seven_days_weather.Hourly;
import com.uniso.lpdm.climao.weather.WeatherByCity;

// Classe Singleton usada para armazenar os dados da request, assim a request é feita apenas 1 vez no inicio do app
// e os dados ficarão salvos nessa classe sem precisar fazer a request novamente.
public class Storage {

    private static WeatherByCity weatherByCity;

    private static Daily[] daily;

    private static Hourly[] hourly;

    private static Storage storageInstance = null;

    private static String location = null;

    // Retorna a instancia unica de Storage.
    public static Storage getInstance() {
        if (storageInstance == null) {
            storageInstance = new Storage();
        }
        return storageInstance;
    }

    public static WeatherByCity getWeatherByCity() {
        return weatherByCity;
    }

    public static void setWeatherByCity(WeatherByCity weatherByCity) {
        Storage.weatherByCity = weatherByCity;
    }

    public static Daily[] getDaily() {
        return daily;
    }

    public static void setDaily(Daily[] daily) {
        Storage.daily = daily;
    }

    public static Hourly[] getHourly() {
        return hourly;
    }

    public static void setHourly(Hourly[] hourly) {
        Storage.hourly = hourly;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Storage.location = location;
    }
}
