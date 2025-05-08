package com.example.weatherappbasic;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HourlyWeatherResponse {

    @SerializedName("list")
    private List<HourlyWeather> list;

    public List<HourlyWeather> getList() {
        return list;
    }
}
