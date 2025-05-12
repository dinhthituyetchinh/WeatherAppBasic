package com.example.weatherappbasic;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourlyWeather {
    @SerializedName("dt")
    private long dt;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Weather> weather;

    public long getDt() {
        return dt;
    }

    public double getTemp() {
        return main != null ? main.temp : 0.0;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public static class Main {
        @SerializedName("temp")
        double temp;
    }
}
