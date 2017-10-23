package com.allexis.weatherapp.ui.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.event.common.WeatherSavedItemClickedEvent;
import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;
import com.allexis.weatherapp.core.persist.CacheManager;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by allexis on 10/22/17.
 */

public class HomeSavedLocationsAdapter extends RecyclerView.Adapter<HomeSavedLocationsAdapter.ViewHolder> {

    private final List<WeatherResponse> savedLocations;

    public HomeSavedLocationsAdapter(List<WeatherResponse> savedLocations) {
        this.savedLocations = savedLocations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_location, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WeatherResponse weather = savedLocations.get(position);
        Context c = holder.itemSavedLocationIv.getContext();

        if (!weather.getWeather().isEmpty()) {
            Picasso.with(c).load(weather.getWeather().get(0).getIconUrl()).into(holder.itemSavedLocationIv);
            holder.itemSavedLocationDescTv.setText(weather.getWeather().get(0).getDescription());
        }
        holder.itemSavedLocationLocationTv.setText(String.format(c.getString(R.string.location_txt), weather.getName(), weather.getSys().getCountry()));
        holder.itemSavedLocationTempTv.setText(String.format(c.getString(R.string.current_temp_txt), weather.getMain().getTempStr(), CacheManager.getInstance().getPreferredTemp()));
        holder.itemSavedLocationTempMinTv.setText(String.format(c.getString(R.string.min_temp_txt), weather.getMain().getTempMinStr(), CacheManager.getInstance().getPreferredTemp()));
        holder.itemSavedLocationTempMaxTv.setText(String.format(c.getString(R.string.max_temp_txt), weather.getMain().getTempMaxStr(), CacheManager.getInstance().getPreferredTemp()));
        holder.itemSavedLocationSunriseTv.setText(String.format(c.getString(R.string.sunrise_at_txt), DateUtils.formatDateTime(c,
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        holder.itemSavedLocationSunsetTv.setText(String.format(c.getString(R.string.sunset_at_txt), DateUtils.formatDateTime(c,
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        holder.itemSavedLocationRelativeTimeSunriseTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
        holder.itemSavedLocationRelativeTimeSunsetTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
        holder.itemSavedLocationContainerRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDispatcher.post(new WeatherSavedItemClickedEvent(weather));
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedLocations.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout itemSavedLocationContainerRl;
        private ImageView itemSavedLocationIv;
        private TextView itemSavedLocationLocationTv;
        private TextView itemSavedLocationTempTv;
        private TextView itemSavedLocationDescTv;
        private TextView itemSavedLocationTempMinTv;
        private TextView itemSavedLocationTempMaxTv;
        private TextView itemSavedLocationSunriseTv;
        private TextView itemSavedLocationSunsetTv;
        private TextView itemSavedLocationRelativeTimeSunriseTv;
        private TextView itemSavedLocationRelativeTimeSunsetTv;

        public ViewHolder(View itemView) {
            super(itemView);

            itemSavedLocationContainerRl = itemView.findViewById(R.id.item_saved_location_container_rl);
            itemSavedLocationIv = itemView.findViewById(R.id.item_saved_location_iv);
            itemSavedLocationLocationTv = itemView.findViewById(R.id.item_saved_location_location_tv);
            itemSavedLocationTempTv = itemView.findViewById(R.id.item_saved_location_temp_tv);
            itemSavedLocationDescTv = itemView.findViewById(R.id.item_saved_location_desc_tv);
            itemSavedLocationTempMinTv = itemView.findViewById(R.id.item_saved_location_temp_min);
            itemSavedLocationTempMaxTv = itemView.findViewById(R.id.item_saved_location_temp_max_tv);
            itemSavedLocationSunriseTv = itemView.findViewById(R.id.item_saved_location_sunrise_tv);
            itemSavedLocationRelativeTimeSunriseTv = itemView.findViewById(R.id.item_saved_location_relative_time_sunrise_tv);
            itemSavedLocationSunsetTv = itemView.findViewById(R.id.item_saved_location_sunset_tv);
            itemSavedLocationRelativeTimeSunsetTv = itemView.findViewById(R.id.item_saved_location_relative_time_sunset_tv);
        }
    }
}
