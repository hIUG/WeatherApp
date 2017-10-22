package com.allexis.weatherapp.ui.module.home;

import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;
import com.allexis.weatherapp.ui.base.BaseContract;

/**
 * Created by allexis on 10/12/17.
 */

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void updateCurrentWeather(WeatherResponse responseObject);
    }

    interface Presenter extends BaseContract.BasePresenter {

    }
}
