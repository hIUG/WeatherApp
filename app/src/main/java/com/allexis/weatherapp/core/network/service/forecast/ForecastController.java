package com.allexis.weatherapp.core.network.service.forecast;

import com.allexis.weatherapp.WeatherApplication;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.common.NetworkController;
import com.allexis.weatherapp.core.network.service.forecast.model.ForecastResponse;

/**
 * Created by allexis on 10/14/17.
 */

public class ForecastController extends NetworkController<ForecastResponse> {

    private ForecastService service;

    @Override
    protected void initClient() {
        service = create(ForecastService.class);
    }

    public void getForecast(double latitude, double longitude) {
        execute(service.getForecast(WeatherApplication.getAPIkey(), latitude, longitude));
    }

    public void getForecast(int zipCode) {
        execute(service.getForecast(WeatherApplication.getAPIkey(), zipCode));
    }

    public void getForecastByCityId(int cityId) {
        execute(service.getForecastByCityId(WeatherApplication.getAPIkey(), cityId));
    }

    @Override
    public void processResponse(boolean successful, int responseCode, ForecastResponse response) {
        EventDispatcher.post(new ForecastEvent(successful, responseCode, response));
    }

    @Override
    public void processFailure() {
        EventDispatcher.post(new ForecastEvent(false));
    }

    @Override
    protected Class<ForecastResponse> getResponseClass() {
        return ForecastResponse.class;
    }
}
