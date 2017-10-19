package com.allexis.weatherapp.core.network.service.forecast;

import com.allexis.weatherapp.core.event.NetworkEvent;

/**
 * Created by allexis on 10/14/17.
 */

public class ForecastEvent extends NetworkEvent<ForecastResponse> {

    public ForecastEvent(boolean successful) {
        super(successful);
    }

    public ForecastEvent(boolean successful, ForecastResponse responseObject) {
        super(successful, responseObject);
    }
}
