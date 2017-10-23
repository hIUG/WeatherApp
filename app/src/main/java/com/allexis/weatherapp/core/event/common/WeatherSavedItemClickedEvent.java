package com.allexis.weatherapp.core.event.common;

import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;

/**
 * Created by allexis on 10/22/17.
 */

public class WeatherSavedItemClickedEvent {

    private WeatherResponse weather;

    public WeatherSavedItemClickedEvent(WeatherResponse weather) {
        this.weather = weather;
    }

    public WeatherResponse getWeather() {
        return weather;
    }
}
