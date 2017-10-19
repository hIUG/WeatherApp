package com.allexis.weatherapp.ui.module.forecastdetail;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.forecast.ForecastController;
import com.allexis.weatherapp.core.network.service.forecast.ForecastEvent;
import com.allexis.weatherapp.core.network.service.weather.WeatherController;
import com.allexis.weatherapp.core.network.service.weather.WeatherEvent;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allexis on 10/17/17.
 */

public class ForecastDetailPresenter implements ForecastDetailContract.Presenter {

    ForecastDetailContract.View view;
    WeatherController weatherController;
    ForecastController forecastController;

    public ForecastDetailPresenter(ForecastDetailContract.View view) {
        this.view = view;
        this.forecastController = new ForecastController();
        this.weatherController = new WeatherController();
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
        this.forecastController.getForecast(zipCode);
    }

    @Override
    public void getForecastByCityId(int cityId) {
        this.forecastController.getForecastByCityId(cityId);
    }

    @Subscribe
    public void onWeatherEvent(WeatherEvent event) {
        if (event.isSuccessful()) {
            view.updateDetailWeather(event.getResponseObject());
        } else if (event.getResponseObject() == null) {
            view.showLongToast(view.getContainerActivity().getString(R.string.unable_to_process_request));
        } else {
            view.showLongToast(view.getContainerActivity().getString(R.string.network_issues_weather));
        }
    }

    @Subscribe
    public void onForecastEvent(ForecastEvent event) {
        if (event.isSuccessful()) {
            List<Entry> entries = new ArrayList<Entry>();
            for (int i = 0; i < 6; i++) {
                entries.add(new Entry(i, i));
            }
            LineDataSet dataSet = new LineDataSet(entries, "5 day / 3 hour forecast");
            dataSet.setColor(R.color.colorBlueIcon);
            dataSet.setValueTextColor(R.color.colorPrimary);
            view.updateDetailForecast(new LineData(dataSet));
        } else if (event.getResponseObject() == null) {
            view.showLongToast(view.getContainerActivity().getString(R.string.unable_to_process_request));
        } else {
            view.showLongToast(view.getContainerActivity().getString(R.string.network_issues_weather));
        }
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
}
