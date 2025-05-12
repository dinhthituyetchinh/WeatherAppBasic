package com.example.weatherappbasic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import Model.ForecastResponse;
import adapter.DailyWeatherAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        Log.d("WeatherListActivity", "Activity được mở"); // Log này để kiểm tra trước

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");

        if (city.isEmpty()) {
            city = "Ho Chi Minh";
            get7DaysData(city);
        } else {
            get7DaysData(city);
            //Log.d("Result", "Dữ liệu truyền qua: " + city);
        }


        TextView arrowBack = findViewById(R.id.arrow_back);

        // Đặt sự kiện OnClick cho mũi tên
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước đó
                onBackPressed();  // Hoặc sử dụng finish() nếu bạn muốn đóng Activity hiện tại
            }
        });
    }

    protected void get7DaysData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi api = retrofit.create(WeatherApi.class);

        Call<ForecastResponse> call = api.getFiveDayForecast(
                cityName,
                "a69f4dc026cb67689f378785c11ae611",
                "metric"
        );

        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, DailyWeather> dailyMap = groupByDay(response.body().list);
                    List<DailyWeather> dailyWeatherList = new ArrayList<>(dailyMap.values());

                    RecyclerView recyclerView = findViewById(R.id.recycler_daily_weather);
                    recyclerView.setLayoutManager(new LinearLayoutManager(WeatherListActivity.this));
                    recyclerView.setAdapter(new DailyWeatherAdapter(WeatherListActivity.this, dailyWeatherList));
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Log.e("Weather", "Error: " + t.getMessage());
            }
        });
    }

    private Map<String, DailyWeather> groupByDay(List<ForecastResponse.ForecastItem> forecastList) {
        Map<String, DailyWeather> dailyMap = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (ForecastResponse.ForecastItem item : forecastList) {
            String date = sdf.format(new Date(item.dt * 1000));
            DailyWeather day = dailyMap.getOrDefault(date, new DailyWeather());
            day.date = date;
            day.update(item.main.temp, item.weather.get(0).icon, item.weather.get(0).description);
            dailyMap.put(date, day);
        }
        return dailyMap;
    }

}