package com.allexis.weatherapp.ui.module.forecastdetail;

import android.support.v4.content.res.ResourcesCompat;
import android.text.format.DateUtils;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.forecast.ForecastController;
import com.allexis.weatherapp.core.network.service.forecast.ForecastEvent;
import com.allexis.weatherapp.core.network.service.forecast.model.ForecastListElement;
import com.allexis.weatherapp.core.network.service.weather.WeatherController;
import com.allexis.weatherapp.core.network.service.weather.WeatherEvent;
import com.allexis.weatherapp.core.util.TemperatureUtil;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by allexis on 10/17/17.
 */

public class ForecastDetailPresenter implements ForecastDetailContract.Presenter {

    private ForecastDetailContract.View view;
    private WeatherController weatherController;
    private ForecastController forecastController;

    private int zipToSearch;

    public ForecastDetailPresenter(ForecastDetailContract.View view) {
        this.view = view;
        this.forecastController = new ForecastController();
        this.weatherController = new WeatherController();
    }

    @Override
    public void onStart() {
        EventDispatcher.register(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {
        EventDispatcher.unregister(this);
    }

    @Override
    public void getWeatherByZip(int zipCode) {
        this.weatherController.getWeather(zipCode);
    }

    @Override
    public void getWeatherByCityId(int cityId) {
        this.weatherController.getWeatherByCityId(cityId);
    }

    @Override
    public void getForecastByZip(int zipCode) {
        this.zipToSearch = zipCode;
        this.forecastController.getForecast(zipCode);
    }

    @Override
    public void getForecastByCityId(int cityId) {
        this.forecastController.getForecastByCityId(cityId);
    }

    @Override
    public void toggleSave() {
        view.showShortToast("Toggle");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent event) {
        if (event.isSuccessful()) {
            view.updateDetailWeather(event.getResponseObject());
        }
        //Error cases already processed at onForecastEvent
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onForecastEvent(ForecastEvent event) {
        if (event.isSuccessful()) {
            ForecastListElement forecastItem;
            List<Entry> temperatureAxisY = new ArrayList<>();
            List<Entry> humidityAxisY = new ArrayList<>();
            List<String> labelAxisX = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            String day;

            for (int i = 0; i < event.getResponseObject().getList().size(); i++) {
                forecastItem = event.getResponseObject().getList().get(i);
                calendar.setTimeInMillis(forecastItem.getDt() * DateUtils.SECOND_IN_MILLIS);
                day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);

                labelAxisX.add(day);
                temperatureAxisY.add(new Entry(i, Float.parseFloat(forecastItem.getMain().getTempStr())));
                humidityAxisY.add(new Entry(i, (float) forecastItem.getMain().getHumidity()));
            }

            LineDataSet temperatureDataSet = new LineDataSet(temperatureAxisY,
                    String.format(view.getContainerActivity().getString(R.string.forecast_title_temperature), TemperatureUtil.preferredTemp));
            temperatureDataSet.setColor(ResourcesCompat.getColor(view.getContainerActivity().getResources(), R.color.color_chart_line_temp, null));
            temperatureDataSet.setCircleColor(ResourcesCompat.getColor(view.getContainerActivity().getResources(), R.color.color_chart_circle_temp, null));
            view.updateDetailForecastTemperature(new LineData(temperatureDataSet), new IndexAxisValueFormatter(labelAxisX));

            LineDataSet humidityDataSet = new LineDataSet(humidityAxisY,
                    view.getContainerActivity().getString(R.string.forecast_title_humidity));
            humidityDataSet.setColor(ResourcesCompat.getColor(view.getContainerActivity().getResources(), R.color.color_chart_line_humidity, null));
            humidityDataSet.setCircleColor(ResourcesCompat.getColor(view.getContainerActivity().getResources(), R.color.color_chart_circle_humidity, null));
            view.updateDetailForecastHumidity(new LineData(humidityDataSet), new IndexAxisValueFormatter(labelAxisX));

            return;
        } else if (event.getCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            view.showLongToast(String.format(view.getContainerActivity().getString(R.string.city_not_found), String.valueOf(zipToSearch)));
        } else {
            view.showLongToast(view.getContainerActivity().getString(R.string.unable_to_process_request));
        }
        view.finish();
    }
}
