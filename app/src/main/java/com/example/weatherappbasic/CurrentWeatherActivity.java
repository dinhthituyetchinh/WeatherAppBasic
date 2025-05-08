package com.example.weatherappbasic;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import adapter.HourlyWeatherAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherActivity extends AppCompatActivity {

    EditText editTextCity;
    TextView tvCity, tvTemp, mainWeather, tvHumidity, tvWind, tvClouds, textViewTime;
    RecyclerView recyclerViewToday;


    ImageView iconWeather;
    String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
//        Ánh xạ
        tvCity = findViewById((R.id.title_city));
        tvTemp = findViewById((R.id.temp));
        tvHumidity = findViewById((R.id.humidity));
        tvWind = findViewById((R.id.wind_speed));
        tvClouds = findViewById(R.id.clouds_all);
        mainWeather = findViewById((R.id.mainWeather));
        textViewTime = findViewById(R.id.dt);
        iconWeather = findViewById(R.id.iconWeather);

        recyclerViewToday = findViewById(R.id.rc_today);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManager);
//
        editTextCity = findViewById(R.id.search_bar);
        if (editTextCity != null) {
            cityName = editTextCity.getText().toString().trim();
            if (cityName.isEmpty()) {
                cityName = "Ho Chi Minh";
            }
            loadWeatherData();
            loadHourlyForecast();
        } else {
            Log.e("WeatherApp", "EditText not found.");
        }

        editTextCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    protected void loadWeatherData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi api = retrofit.create(WeatherApi.class);


        Call<WeatherResponse> call = api.getCurrentWeather(
                cityName,
                "",
                "metric"
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();

                    tvCity.setText(weatherResponse.getName());
                    tvTemp.setText(String.format("%.0f°C", weatherResponse.getMain().getTemp()));
                    tvHumidity.setText(weatherResponse.getMain().getHumidity() + "%");
                    tvWind.setText(weatherResponse.getWind().getSpeed() + " m/s");
                    tvClouds.setText(weatherResponse.getClouds().getAll() + "%");
                    mainWeather.setText(weatherResponse.getWeather().get(0).getMain());

                    Date date = new Date(weatherResponse.getDt() * 1000L);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd | hh:mm a", Locale.getDefault());
                    sdf.setTimeZone(TimeZone.getDefault());
                    String formattedTime = sdf.format(date);
                    textViewTime.setText(formattedTime);

                    if (weatherResponse != null && weatherResponse.getWeather() != null && !weatherResponse.getWeather().isEmpty()) {
                        String iconCode = weatherResponse.getWeather().get(0).getIcon();
                        String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                        Log.d("WeatherIcon", "Icon URL: " + iconUrl);

                        Glide.with(CurrentWeatherActivity.this)
                            .load(iconUrl)
                                .error(R.mipmap.sunny)
                                .placeholder(R.mipmap.sunny)
                            .into(iconWeather);
                    } else {
                        Log.d("WeatherIcon", "Weather response is null or empty");
                    }

//                    String iconCode = weatherResponse.getWeather().get(0).getIcon();
//                    Log.d("Weather_img", "Img: " + iconCode);
//                    String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
//                    Log.d("WeatherIcon", "Icon URL: " + iconUrl);
//                    Glide.with(CurrentWeatherActivity.this)
//                            .load(iconUrl)
//                            .into(iconWeather);

//                    Log.d("Weather", "City: " + weather.getName());
//                    Log.d("Weather", "Temp: " + weather.getMain().getTemp() + "°C");
//                    Log.d("Weather", "Description: " + weather.getWeather().get(0).getDescription());
                    Log.d("Weather_img", "Img: " + weatherResponse.getWeather().get(0).getIcon());
                } else {
                    Log.e("Weather", "Failed response");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("Weather", "Error: " + t.getMessage());
            }
        });
    }
    private void loadHourlyForecast() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi api = retrofit.create(WeatherApi.class);

        Call<HourlyWeatherResponse> call = api.getHourlyForecast(
                cityName,
                "a69f4dc026cb67689f378785c11ae611",
                "metric"
        );

        call.enqueue(new Callback<HourlyWeatherResponse>() {
            @Override
            public void onResponse(Call<HourlyWeatherResponse> call, Response<HourlyWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HourlyWeather> hourlyList = response.body().getList();
                    HourlyWeatherAdapter adapter = new HourlyWeatherAdapter(CurrentWeatherActivity.this, hourlyList.subList(0, 12));
                    recyclerViewToday.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HourlyWeatherResponse> call, Throwable t) {
                Log.e("HourlyForecast", "Error: " + t.getMessage());
            }
        });
    }


}