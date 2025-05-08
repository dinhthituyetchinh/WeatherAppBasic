package com.example.weatherappbasic;

public class DailyWeather {
    public String date;
    public float minTemp = Float.MAX_VALUE;
    public float maxTemp = Float.MIN_VALUE;
    public String icon;
    public String description;

    public void update(float temp, String icon, String description) {
        if (temp < minTemp) minTemp = temp;
        if (temp > maxTemp) maxTemp = temp;
        this.icon = icon;
        this.description = description;
    }
}

