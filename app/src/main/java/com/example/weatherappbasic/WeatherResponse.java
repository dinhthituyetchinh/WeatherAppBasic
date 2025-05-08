package com.example.weatherappbasic;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class WeatherResponse {

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("main")
    private Main main;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("rain")
    private Rain rain;

    @SerializedName("dt")
    private long dt; // thời gian hiện tại (Unix time)

    @SerializedName("name")
    private String name;

    @SerializedName("timezone")
    private int timezone;

    // ==== Getters ====
    public List<Weather> getWeather() { return weather; }
    public Main getMain() { return main; }
    public Wind getWind() { return wind; }
    public Clouds getClouds() { return clouds; }
    public Rain getRain() { return rain; }
    public long getDt() { return dt; }
    public String getName() { return name; }
    public int getTimezone() { return timezone; }

}
