package com.allexis.weatherapp.core.network.service.weather;

import com.allexis.weatherapp.core.event.NetworkEvent;

/**
 * Created by allexis on 10/14/17.
 */

public class WeatherEvent extends NetworkEvent<WeatherResponse> {

    public WeatherEvent(boolean successful) {
        super(successful);
    }

    public WeatherEvent(boolean successful, WeatherResponse responseObject) {
        super(successful, responseObject);
    }
}
