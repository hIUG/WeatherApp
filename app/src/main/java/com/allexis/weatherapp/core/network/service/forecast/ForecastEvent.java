package com.allexis.weatherapp.core.network.service.forecast;

import com.allexis.weatherapp.core.event.NetworkEvent;
import com.allexis.weatherapp.core.network.service.forecast.model.ForecastResponse;

/**
 * Created by allexis on 10/14/17.
 */

public class ForecastEvent extends NetworkEvent<ForecastResponse> {

    public ForecastEvent(boolean successful) {
        this(successful, 0, null);
    }

    public ForecastEvent(boolean successful, ForecastResponse responseObject) {
        this(successful, 0, responseObject);
    }

    public ForecastEvent(boolean successful, int code, ForecastResponse responseObject) {
        super(successful, code, responseObject);
    }
}
