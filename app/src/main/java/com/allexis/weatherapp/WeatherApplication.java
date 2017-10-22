package com.allexis.weatherapp;

import android.app.Application;

import com.allexis.weatherapp.core.persist.SharedPrefs;

/**
 * Created by allexis on 10/14/17.
 * TODO: 1) Saving locations and toggle buttons
 * TODO: 2) Saving of last zip code searched
 * TODO: 3) Saving and displaying list of saved locations
 * TODO: 4) Subscribe to some broadcast receiver
 * TODO: 5) Settings activity
 * TODO: 6) Implement AlarmManager or JobScheduler to update saved location's weather
 * TODO: 7) Notifications for special weather requests
 * TODO: 8) Swipe down to refresh
 * TODO: 9) Show saved locations on map
 *
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
        SharedPrefs.init(getApplicationContext());
    }
}
