package com.example.weatherappbasic;


import Model.ForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    @GET("forecast")
    Call<HourlyWeatherResponse> getHourlyForecast(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );


    @GET("forecast")
    Call<ForecastResponse> getFiveDayForecast(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );


}
