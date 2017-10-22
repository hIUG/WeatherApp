package com.allexis.weatherapp.ui.module.forecastdetail;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.lib.external.StringAxisValueFormatter;
import com.allexis.weatherapp.core.network.service.weather.WeatherResponse;
import com.allexis.weatherapp.core.util.AnimationConstants;
import com.allexis.weatherapp.ui.base.BaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static com.allexis.weatherapp.core.util.TemperatureUtil.preferredTemp;

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
    private LineChart forecastHumidityLineChart;
    private FloatingActionButton forecastSaveFab;

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
        this.forecastHumidityLineChart = v.findViewById(R.id.forecast_rain_line_chart);
        this.forecastSaveFab = v.findViewById(R.id.forecast_save_fab);

        this.forecastTemperatureLineChart.setTouchEnabled(false);
        this.forecastTemperatureLineChart.setDragEnabled(false);
        this.forecastTemperatureLineChart.setScaleEnabled(false);
        this.forecastTemperatureLineChart.setDescription(null);
        this.forecastHumidityLineChart.setTouchEnabled(false);
        this.forecastHumidityLineChart.setDragEnabled(false);
        this.forecastHumidityLineChart.setScaleEnabled(false);
        this.forecastHumidityLineChart.setDescription(null);
        this.forecastSaveFab.setOnClickListener(this);

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
        switch (v.getId()) {
            case R.id.forecast_save_fab:
                presenter.toggleSave();
        }
    }

    @Override
    public void updateDetailWeather(WeatherResponse weather) {
        if (!weather.getWeather().isEmpty()) {
            Picasso.with(getContainerActivity()).load(weather.getWeather().get(0).getIconUrl()).into(this.forecastIv);
            this.forecastDescriptionTv.setText(weather.getWeather().get(0).getDescription());
        }
        this.forecastLocationTv.setText(String.format(getString(R.string.location_txt), weather.getName(), weather.getSys().getCountry()));
        this.forecastTempTv.setText(String.format(getString(R.string.current_temp_txt), weather.getMain().getTempStr(), preferredTemp));
        this.forecastTempMinTv.setText(String.format(getString(R.string.min_temp_txt), weather.getMain().getTempMinStr(), preferredTemp));
        this.forecastTempMaxTv.setText(String.format(getString(R.string.max_temp_txt), weather.getMain().getTempMaxStr(), preferredTemp));
        this.forecastSunriseTv.setText(String.format(getString(R.string.sunrise_at_txt), DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        this.forecastSunsetTv.setText(String.format(getString(R.string.sunset_at_txt), DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        this.forecastRelativeTimeSunriseTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
        this.forecastRelativeTimeSunsetTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
    }

    @Override
    public void updateDetailForecastTemperature(LineData lineData, StringAxisValueFormatter formatter) {
        forecastTemperatureLineChart.setData(lineData);
        forecastTemperatureLineChart.getXAxis().setValueFormatter(formatter);
        forecastTemperatureLineChart.animateX(AnimationConstants.LINE_CHART_X_ANIM_MS);
    }

    @Override
    public void updateDetailForecastHumidity(LineData lineData, StringAxisValueFormatter formatter) {
        forecastHumidityLineChart.setData(lineData);
        forecastHumidityLineChart.getXAxis().setValueFormatter(formatter);
        forecastHumidityLineChart.animateX(AnimationConstants.LINE_CHART_X_ANIM_MS);
    }

    @Override
    public void finish() {
        getContainerActivity().onBackPressed();
    }
}
