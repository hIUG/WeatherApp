package com.allexis.weatherapp.core.network.service.forecast;

import com.allexis.weatherapp.WeatherApplication;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.NetworkController;

import retrofit2.Response;

/**
 * Created by allexis on 10/14/17.
 */

public class ForecastController extends NetworkController<ForecastResponse> {

    ForecastService service;

    @Override
    protected void initClient() {
        service = create(ForecastService.class);
    }

    public void getForecast(double latitude, double longitude) {
        service.getForecast(WeatherApplication.getAPIkey(), latitude, longitude).enqueue(this);
    }

    public void getForecast(int zipCode) {
        service.getForecast(WeatherApplication.getAPIkey(), zipCode).enqueue(this);
    }

    public void getForecastByCityId(int cityId) {
        service.getForecastByCityId(WeatherApplication.getAPIkey(), cityId).enqueue(this);
    }

    @Override
    public void processResponse(Response<ForecastResponse> response) {
        EventDispatcher.post(new ForecastEvent(response.isSuccessful(), response.code(), response.body()));
    }

    @Override
    public void processFailure() {
        EventDispatcher.post(new ForecastEvent(false));
    }
}
