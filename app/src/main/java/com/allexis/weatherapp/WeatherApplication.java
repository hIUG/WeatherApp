package com.allexis.weatherapp;

import android.app.Application;

import com.allexis.weatherapp.core.persist.CacheManager;

/**
 * Created by allexis on 10/14/17.
 * TODO: Implement caching expiry date
 * TODO: Hide current weather when scrolling down on home screen
 * TODO: Saving of last zip code searched
 * TODO: Saving and displaying list of saved locations (recyclerView)
 * TODO: Handle screen rotation
 * TODO: Subscribe to some broadcast receiver
 * TODO: Settings activity
 * TODO: Swipe left RV to remove saved location
 * TODO: Implement AlarmManager or JobScheduler to update saved location's weather
 * TODO: Notifications for special weather requests
 * TODO: Swipe down to refresh
 * TODO: Show saved locations on map
 *
 * DONE!!!
 * TODO: Saving locations and toggle buttons
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
        CacheManager.init(getApplicationContext());
    }
}
