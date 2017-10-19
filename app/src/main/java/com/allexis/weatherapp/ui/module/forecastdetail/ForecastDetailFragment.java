package com.allexis.weatherapp.ui.module.forecastdetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.network.service.weather.WeatherResponse;
import com.allexis.weatherapp.core.util.AnimationConstants;
import com.allexis.weatherapp.ui.base.BaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
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
    private LineChart forecastRainLineChart;

    public ForecastDetailFragment() {
    }

    public static ForecastDetailFragment newInstanceForZip(int zipcCde) {
        ForecastDetailFragment fragment = new ForecastDetailFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_ZIP_CODE, zipcCde);
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

        this.forecastIv = v.findViewById(R.id.forecast_iv);
        this.forecastLocationTv = v.findViewById(R.id.forecast_location_tv);
        this.forecastDescriptionTv = v.findViewById(R.id.forecast_description_tv);
        this.forecastTempTv = v.findViewById(R.id.forecast_temp_tv);
        this.forecastTempMinTv = v.findViewById(R.id.forecast_temp_min_tv);
        this.forecastTempMaxTv = v.findViewById(R.id.forecast_temp_max_tv);
        this.forecastSunriseTv = v.findViewById(R.id.forecast_sunrise_tv);
        this.forecastRelativeTimeSunriseTv = v.findViewById(R.id.forecast_relative_time_sunrise_tv);
        this.forecastSunsetTv = v.findViewById(R.id.forecast_sunset_tv);
        this.forecastRelativeTimeSunsetTv = v.findViewById(R.id.forecast_relative_time_sunset_tv);
        this.forecastTemperatureLineChart = v.findViewById(R.id.forecast_temperature_line_chart);
        this.forecastRainLineChart = v.findViewById(R.id.forecast_rain_line_chart);

        this.forecastTemperatureLineChart.setTouchEnabled(false);
        this.forecastTemperatureLineChart.setDragEnabled(false);
        this.forecastTemperatureLineChart.setScaleEnabled(false);

        return v;
    }

    @Override
    protected void init() {
        this.presenter = new ForecastDetailPresenter(this);

        if (getArguments().containsKey(EXTRA_ZIP_CODE)) {
            presenter.getWeatherByZip(getArguments().getInt(EXTRA_ZIP_CODE));
            presenter.getForecastByZip(getArguments().getInt(EXTRA_ZIP_CODE));
        } else if (getArguments().containsKey(EXTRA_CITY_ID)) {
            presenter.getWeatherByCityId(getArguments().getInt(EXTRA_CITY_ID));
            presenter.getForecastByCityId(getArguments().getInt(EXTRA_CITY_ID));
        }
    }

    @Override
    public String getFragmentTag() {
        return ForecastDetailFragment.class.getCanonicalName();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updateDetailWeather(WeatherResponse weather) {
        if (!weather.getWeather().isEmpty()) {
            Picasso.with(getContainerActivity()).load(weather.getWeather().get(0).getIconUrl()).into(this.forecastIv);
            this.forecastDescriptionTv.setText(weather.getWeather().get(0).getDescription());
        }
        this.forecastLocationTv.setText(weather.getName() + ", " + weather.getSys().getCountry());
        this.forecastTempTv.setText(String.format("%s K", weather.getMain().getTempKelvin()));
        this.forecastTempMinTv.setText(String.format("min: %s K", weather.getMain().getTempMinKelvin()));
        this.forecastTempMaxTv.setText(String.format("max: %s K", weather.getMain().getTempMaxKelvin()));
        this.forecastSunriseTv.setText(String.format("Sunrise: %s", DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        this.forecastSunsetTv.setText(String.format("Sunset: %s", DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        this.forecastRelativeTimeSunriseTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
        this.forecastRelativeTimeSunsetTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
    }

    @Override
    public void updateDetailForecast(LineData forecast) {
        forecastTemperatureLineChart.setData(forecast);
        forecastTemperatureLineChart.animateX(AnimationConstants.LINE_CHART_X_ANIM_MS);
    }
}