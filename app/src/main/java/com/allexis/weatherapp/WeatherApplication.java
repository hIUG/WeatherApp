package com.allexis.weatherapp;

import android.app.Application;

/**
 * Created by allexis on 10/14/17.
 */

public class WeatherApplication extends Application {

    private static WeatherApplication instance;
    private static String APIkey;

    public static WeatherApplication getInstance() {
        return instance;
    }

    public static String getAPIkey() {
        return APIkey;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        APIkey = getApplicationContext().getString(R.string.open_weather_map_api_key);
    }
}
