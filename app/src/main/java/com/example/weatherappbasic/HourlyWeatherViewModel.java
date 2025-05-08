package com.example.weatherappbasic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HourlyWeatherViewModel {
    private final HourlyWeather weather;

    public HourlyWeatherViewModel(HourlyWeather weather) {
        this.weather = weather;
    }

    public String getHour() {
        Date date = new Date(weather.getDt() * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public String getTemp() {
        return String.format("%.0f°C", weather.getTemp());
    }

    public String getIconUrl() {
        String icon = weather.getWeather().get(0).getIcon();
        return "https://openweathermap.org/img/wn/" + icon + "@2x.png";
    }


}
