package com.allexis.weatherapp.ui.module.forecastdetail;

import com.allexis.weatherapp.core.lib.external.StringAxisValueFormatter;
import com.allexis.weatherapp.core.network.service.weather.WeatherResponse;
import com.allexis.weatherapp.ui.base.BaseContract;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by allexis on 10/17/17.
 */

public interface ForecastDetailContract {

    interface View extends BaseContract.BaseView {

        void updateDetailWeather(WeatherResponse responseObject);

        void updateDetailForecastTemperature(LineData lineData, StringAxisValueFormatter formatter);

        void updateDetailForecastHumidity(LineData lineData, StringAxisValueFormatter formatter);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void getWeatherByZip(int zipCode);

        void getWeatherByCityId(int cityId);

        void getForecastByZip(int zipCode);

        void getForecastByCityId(int cityId);

        void toggleSave();
    }
}
