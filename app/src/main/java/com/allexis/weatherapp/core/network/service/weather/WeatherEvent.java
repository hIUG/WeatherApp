package com.allexis.weatherapp.core.network.service.weather;

import com.allexis.weatherapp.core.event.NetworkEvent;

/**
 * Created by allexis on 10/14/17.
 */

public class WeatherEvent extends NetworkEvent<WeatherResponse> {

    public WeatherEvent(boolean successful) {
        this(successful, 0, null);
    }

    public WeatherEvent(boolean successful, WeatherResponse responseObject) {
        this(successful, 0, responseObject);
    }

    public WeatherEvent(boolean successful, int code, WeatherResponse body) {
        super(successful, code, body);
    }
}
