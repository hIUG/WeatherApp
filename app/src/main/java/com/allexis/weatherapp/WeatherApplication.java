package com.allexis.weatherapp;

import android.app.Application;

import com.allexis.weatherapp.core.persist.CacheManager;

/**
 * Created by allexis on 10/14/17.
 * TODO: Handle screen rotation to support landscape... also tablets
 * TODO: Swipe left RV to remove saved location
 * TODO: Implement AlarmManager or JobScheduler to update saved location's weather
 * TODO: Notifications for special weather requests
 * TODO: Swipe down to refresh
 * TODO: Show saved locations on map
 *
 * DONE!!!
 * TODO: Implement caching expiry date
 * TODO: Hide current weather when scrolling down on home screen (animation)
 * TODO: Saving locations and toggle buttons
 * TODO: Saving and displaying list of saved locations (recyclerView)
 * TODO: Settings activity
 * TODO: Saving of last zip code searched
 * TODO: Change zip code search impl to String instead of Int
 * TODO: Subscribe to some broadcast receiver
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
