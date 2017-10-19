package com.allexis.weatherapp.ui.module.forecastdetail;

import com.allexis.weatherapp.core.network.service.weather.WeatherResponse;
import com.allexis.weatherapp.ui.base.BaseContract;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by allexis on 10/17/17.
 */

public interface ForecastDetailContract {

    interface View extends BaseContract.BaseView {

        void updateDetailWeather(WeatherResponse responseObject);

        void updateDetailForecast(LineData lineData);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void getWeatherByZip(int zipCode);

        void getWeatherByCityId(int cityId);

        void getForecastByZip(int zipCode);

        void getForecastByCityId(int cityId);
    }
}
