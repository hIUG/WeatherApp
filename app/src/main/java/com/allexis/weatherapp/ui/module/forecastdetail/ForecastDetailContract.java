package com.allexis.weatherapp.ui.module.forecastdetail;

import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;
import com.allexis.weatherapp.ui.base.BaseContract;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by allexis on 10/17/17.
 */

public interface ForecastDetailContract {

    interface View extends BaseContract.BaseView {

        void updateDetailWeather(WeatherResponse responseObject);

        void updateDetailForecastTemperature(LineData lineData, IAxisValueFormatter formatter);

        void updateDetailForecastHumidity(LineData lineData, IAxisValueFormatter formatter);

        void updateSavedIcon(boolean isSavedLocation);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void getWeatherByZip(String zipCode);

        void getWeatherByCityId(int cityId);

        void getWeatherByCityName(String cityName);

        void getForecastByZip(String zipCode);

        void getForecastByCityId(int cityId);

        void getForecastByCityName(String cityName);

        void toggleSave();
    }
}
