package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherappbasic.DailyWeather;
import com.example.weatherappbasic.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    private final List<DailyWeather> weatherList;
    private final Context context;

    public DailyWeatherAdapter(Context context, List<DailyWeather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daily_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyWeather weather = weatherList.get(position);

        holder.date.setText(formatDate(weather.date));
        holder.temp.setText(String.format("%.0f°C / %.0f°C", weather.minTemp, weather.maxTemp));
        holder.description.setText(translateDescription(weather.description));

        String iconUrl = "https://openweathermap.org/img/wn/" + weather.icon + "@2x.png";
        Glide.with(context).load(iconUrl).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, temp, description;
        ImageView icon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.text_date);
            temp = itemView.findViewById(R.id.text_temp);
            description = itemView.findViewById(R.id.text_description);
            icon = itemView.findViewById(R.id.image_icon);
        }
    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(dateString);

            Calendar cal = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                return "Hôm nay";
            }

            String[] weekdays = {"Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"};
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            return weekdays[dayOfWeek - 1];
        } catch (Exception e) {
            return dateString;
        }
    }

    private String translateDescription(String english) {
        switch (english.toLowerCase()) {
            case "clear sky": return "Trời quang";
            case "few clouds": return "Ít mây";
            case "scattered clouds": return "Mây rải rác";
            case "broken clouds": return "Mây từng đám";
            case "overcast clouds": return "Mây u ám";
            case "light rain": return "Mưa nhẹ";
            case "moderate rain":
            case "rain": return "Mưa";
            case "shower rain": return "Mưa rào";
            case "thunderstorm": return "Dông";
            case "snow": return "Tuyết";
            case "mist": return "Sương mù";
            default: return english;
        }
    }
}

