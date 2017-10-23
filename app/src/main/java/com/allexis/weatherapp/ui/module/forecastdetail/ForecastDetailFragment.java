package com.allexis.weatherapp.ui.module.forecastdetail;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;
import com.allexis.weatherapp.core.persist.CacheManager;
import com.allexis.weatherapp.core.util.AnimationConstants;
import com.allexis.weatherapp.ui.base.BaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by allexis on 10/14/17.
 */
public class ForecastDetailFragment extends BaseFragment<ForecastDetailPresenter> implements ForecastDetailContract.View {

    private static final String EXTRA_ZIP_CODE = "EXTRA_ZIP_CODE";
    private static final String EXTRA_CITY_ID = "EXTRA_CITY_ID";

    private ImageView forecastIv;
    private TextView forecastLocationTv;
    private TextView forecastDescriptionTv;
    private TextView forecastTempTv;
    private TextView forecastTempMinTv;
    private TextView forecastTempMaxTv;
    private TextView forecastSunriseTv;
    private TextView forecastRelativeTimeSunriseTv;
    private TextView forecastSunsetTv;
    private TextView forecastRelativeTimeSunsetTv;
    private LineChart forecastTemperatureLineChart;
    private LineChart forecastHumidityLineChart;
    private FloatingActionButton forecastSaveFab;

    public ForecastDetailFragment() {
    }

    public static ForecastDetailFragment newInstanceForZip(String zipcCde) {
        ForecastDetailFragment fragment = new ForecastDetailFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_ZIP_CODE, zipcCde);
        fragment.setArguments(args);

        return fragment;
    }

    public static ForecastDetailFragment newInstanceForCityId(int cityId) {
        ForecastDetailFragment fragment = new ForecastDetailFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_CITY_ID, cityId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forecast_detail, container, false);

        forecastIv = v.findViewById(R.id.forecast_iv);
        forecastLocationTv = v.findViewById(R.id.forecast_location_tv);
        forecastDescriptionTv = v.findViewById(R.id.forecast_description_tv);
        forecastTempTv = v.findViewById(R.id.forecast_temp_tv);
        forecastTempMinTv = v.findViewById(R.id.forecast_temp_min_tv);
        forecastTempMaxTv = v.findViewById(R.id.forecast_temp_max_tv);
        forecastSunriseTv = v.findViewById(R.id.forecast_sunrise_tv);
        forecastRelativeTimeSunriseTv = v.findViewById(R.id.forecast_relative_time_sunrise_tv);
        forecastSunsetTv = v.findViewById(R.id.forecast_sunset_tv);
        forecastRelativeTimeSunsetTv = v.findViewById(R.id.forecast_relative_time_sunset_tv);
        forecastTemperatureLineChart = v.findViewById(R.id.forecast_temperature_line_chart);
        forecastHumidityLineChart = v.findViewById(R.id.forecast_rain_line_chart);
        forecastSaveFab = v.findViewById(R.id.forecast_save_fab);

        forecastTemperatureLineChart.setTouchEnabled(false);
        forecastTemperatureLineChart.setDragEnabled(false);
        forecastTemperatureLineChart.setScaleEnabled(false);
        forecastTemperatureLineChart.setDescription(null);
        forecastHumidityLineChart.setTouchEnabled(false);
        forecastHumidityLineChart.setDragEnabled(false);
        forecastHumidityLineChart.setScaleEnabled(false);
        forecastHumidityLineChart.setDescription(null);
        forecastSaveFab.setOnClickListener(this);

        return v;
    }

    @Override
    protected void init() {
        this.presenter = new ForecastDetailPresenter(this);

        if (getArguments().containsKey(EXTRA_ZIP_CODE)) {
            /**
             * Bug on openweathermap: for ZIP code requests, weather and forecast API's are returning city ID=0
             * https://openweathermap.desk.com/customer/en/portal/questions/17167915-city-id-returning-zero-for-requests-by-zip-code?new=17167915
             * because of this... I'm calling forecast API by q param endpoint instead of zip to retrieve city ID from there
             **/
            presenter.getWeatherByZip(getArguments().getString(EXTRA_ZIP_CODE));
//            presenter.getForecastByZip(getArguments().getInt(EXTRA_ZIP_CODE));
        } else if (getArguments().containsKey(EXTRA_CITY_ID)) {
            presenter.getWeatherByCityId(getArguments().getInt(EXTRA_CITY_ID));
//            presenter.getForecastByCityId(getArguments().getInt(EXTRA_CITY_ID));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forecast_save_fab:
                presenter.toggleSave();
        }
    }

    @Override
    public void updateDetailWeather(WeatherResponse weather) {
        if (!weather.getWeather().isEmpty()) {
            Picasso.with(getContainerActivity()).load(weather.getWeather().get(0).getIconUrl()).into(forecastIv);
            forecastDescriptionTv.setText(weather.getWeather().get(0).getDescription());
        }
        forecastLocationTv.setText(String.format(getString(R.string.location_txt), weather.getName(), weather.getSys().getCountry()));
        forecastTempTv.setText(String.format(getString(R.string.current_temp_txt), weather.getMain().getTempStr(), CacheManager.getInstance().getPreferredTemp()));
        forecastTempMinTv.setText(String.format(getString(R.string.min_temp_txt), weather.getMain().getTempMinStr(), CacheManager.getInstance().getPreferredTemp()));
        forecastTempMaxTv.setText(String.format(getString(R.string.max_temp_txt), weather.getMain().getTempMaxStr(), CacheManager.getInstance().getPreferredTemp()));
        forecastSunriseTv.setText(String.format(getString(R.string.sunrise_at_txt), DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        forecastSunsetTv.setText(String.format(getString(R.string.sunset_at_txt), DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        forecastRelativeTimeSunriseTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
        forecastRelativeTimeSunsetTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
    }

    @Override
    public void updateDetailForecastTemperature(LineData lineData, IAxisValueFormatter formatter) {
        forecastTemperatureLineChart.setData(lineData);
        forecastTemperatureLineChart.getXAxis().setValueFormatter(formatter);
        forecastTemperatureLineChart.animateX(AnimationConstants.LINE_CHART_X_ANIM_MS);
    }

    @Override
    public void updateDetailForecastHumidity(LineData lineData, IAxisValueFormatter formatter) {
        forecastHumidityLineChart.setData(lineData);
        forecastHumidityLineChart.getXAxis().setValueFormatter(formatter);
        forecastHumidityLineChart.animateX(AnimationConstants.LINE_CHART_X_ANIM_MS);
    }

    @Override
    public void updateSavedIcon(boolean isSavedLocation) {
        if (isSavedLocation) {
            forecastSaveFab.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            forecastSaveFab.setImageResource(R.drawable.ic_favorite);
        }
    }

    @Override
    public void finish() {
        getContainerActivity().onBackPressed();
    }

    @Override
    public String getFragmentTag() {
        return ForecastDetailFragment.class.getCanonicalName();
    }
}
