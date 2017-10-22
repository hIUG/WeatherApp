package com.allexis.weatherapp.core.network.service.weather;

import com.allexis.weatherapp.WeatherApplication;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.NetworkController;

import retrofit2.Response;

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
        service.getWeather(WeatherApplication.getAPIkey(), latitude, longitude).enqueue(this);
    }

    public void getWeather(int zipCode) {
        service.getWeather(WeatherApplication.getAPIkey(), zipCode).enqueue(this);
    }

    public void getWeatherByCityId(int cityId) {
        service.getWeatherByCityId(WeatherApplication.getAPIkey(), cityId).enqueue(this);
    }

    @Override
    public void processResponse(Response<WeatherResponse> response) {
        EventDispatcher.post(new WeatherEvent(response.isSuccessful(), response.code(), response.body()));
    }

    @Override
    public void processFailure() {
        EventDispatcher.post(new WeatherEvent(false));
    }
}
