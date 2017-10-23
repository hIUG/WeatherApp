package com.allexis.weatherapp.core.network.service.weather;

import com.allexis.weatherapp.WeatherApplication;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.common.NetworkController;
import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;

/**
 * Created by allexis on 10/14/17.
 */

public class WeatherController extends NetworkController<WeatherResponse> {

    private WeatherService service;

    @Override
    protected void initClient() {
        service = create(WeatherService.class);
    }

    public void getWeather(double latitude, double longitude) {
        execute(service.getWeather(WeatherApplication.getAPIkey(), latitude, longitude));
    }

    public void getWeather(String zipCode) {
        execute(service.getWeather(WeatherApplication.getAPIkey(), zipCode));
    }

    public void getWeatherByCityId(int cityId) {
        execute(service.getWeatherByCityId(WeatherApplication.getAPIkey(), cityId));
    }

    public void getWeatherByCityName(String cityName) {
        execute(service.getWeatherByCityName(WeatherApplication.getAPIkey(), cityName));
    }

    @Override
    public void processResponse(boolean successful, int responseCode, WeatherResponse response) {
        EventDispatcher.post(new WeatherEvent(successful, responseCode, response));
    }

    @Override
    public void processFailure() {
        EventDispatcher.post(new WeatherEvent(false));
    }

    @Override
    protected Class<WeatherResponse> getResponseClass() {
        return WeatherResponse.class;
    }
}
