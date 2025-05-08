package com.example.weatherappbasic;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourlyWeather {

    @SerializedName("dt")
    private long dt;

    @SerializedName("temp")
    private double temp;

    @SerializedName("weather")
    private List<Weather> weather;

    public long getDt() {
        return dt;
    }

    public double getTemp() {
        return temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}
