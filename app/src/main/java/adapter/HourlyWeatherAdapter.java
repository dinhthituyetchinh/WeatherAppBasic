package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherappbasic.HourlyWeather;
import com.example.weatherappbasic.HourlyWeatherViewModel;
import com.example.weatherappbasic.databinding.ListItemWeatherTodayBinding;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private final List<HourlyWeather> hourlyList;
    private final Context context;

    public HourlyWeatherAdapter(Context context, List<HourlyWeather> hourlyList) {
        this.context = context;
        this.hourlyList = hourlyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemWeatherTodayBinding binding = ListItemWeatherTodayBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(hourlyList.get(position));


//        Glide.with(context)
//                .load(viewModel.getIconUrl())
//                .into(holder.binding.imageWeatherToday);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ListItemWeatherTodayBinding binding;

        ViewHolder(ListItemWeatherTodayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(HourlyWeather weather) {
            binding.setWeatherVm(new HourlyWeatherViewModel(weather));
            binding.executePendingBindings();
        }
    }

    @BindingAdapter("ImageUrl")
    public static void loadImg(ImageView imageView, String url)
    {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}

