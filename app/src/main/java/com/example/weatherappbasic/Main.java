package com.example.weatherappbasic;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double temp;

    @SerializedName("feels_like")
    private double feelsLike;

    @SerializedName("humidity")
    private int humidity;

    public double getTemp() { return temp; }
    public double getFeelsLike() { return feelsLike; }
    public int getHumidity() { return humidity; }
}
