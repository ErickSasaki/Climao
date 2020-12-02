package com.uniso.lpdm.climao.utils;

public class Translator {

    /**
     * Transforma os dias da semana em forma de números em seus nomes abreviados.
     * @param DayOfTheWeekNumber Número (0 -> domingo até 6 -> sábado)
     * @return Retorna o nome abreviado (0 -> domingo, retorna "Dom")
     */
    public static String DaysOfTheWeekTranslator(int DayOfTheWeekNumber) {
        switch (DayOfTheWeekNumber) {
            case 0: {
                return "Dom";
            }
            case 1: {
                return "Seg";
            }
            case 2: {
                return "Ter";
            }
            case 3: {
                return "Qua";
            }
            case 4: {
                return "Qui";
            }
            case 5: {
                return "Sex";
            }
            case 6: {
                return "Sab";
            }
            default: {
                return "Dia da semana inválido!";
            }
        }
    }
    
    public static String WeatherTranslator(String weather) {

        switch (weather) {
            case "Thunderstorm": {
                return "Trovoadas";
            }
            case "Drizzle": {
                return "Chuviscando";
            }
            case "Rain": {
                return "Chuva";
            }
            case "Snow": {
                return "Neve";
            }
            case "Clear": {
                return "Céu Claro";
            }
            default: {
                return "Nublado";
            }
        }

    }

}
