package com.allexis.weatherapp.core.persist;

import android.content.Context;
import android.content.SharedPreferences;

import com.allexis.weatherapp.core.persist.data.Temperature;

/**
 * Created by allexis on 10/19/17.
 *
 * Simple class to manage a singleton SharedPreferences instance. Put methods are rewrote to have a
 * single overridden version of put using method overloading instead of having several different calls
 * to putString(), putInt(), etc... there's just one put() method overloaded. Also, no need of calling
 * edit() and aplply() methods.
 *
 * This class is the caching manager for the app.
 * Cache managing is all done here and detached from the network module, so, changing caching impl
 * is easier.
 */

public class CacheManager {

    private static final String KEY_PREFERRED_TEMP = "KEY_SAVED_LOCATIONS";
    private static final String KEY_SAVED_LOCATIONS = "KEY_SAVED_LOCATIONS";

    private static CacheManager instance;
    private SharedPreferences sharedPrefs;

    private CacheManager(final Context context) {
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void init(final Context context) {
        if (instance == null) {
            instance = new CacheManager(context);
        }
    }

    public static CacheManager getInstance() {
        return instance;
    }

    public void put(final String key, final String value) {
        sharedPrefs.edit().putString(key, value).apply();
    }

    public void put(final String key, final boolean value) {
        sharedPrefs.edit().putBoolean(key, value).apply();
    }

    public void put(final String key, final int value) {
        sharedPrefs.edit().putInt(key, value).apply();
    }

    public void put(final String key, final long value) {
        sharedPrefs.edit().putLong(key, value).apply();
    }

    public void put(final String key, final float value) {
        sharedPrefs.edit().putFloat(key, value).apply();
    }

    public String getString(final String key, final String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }

    public int getInt(final String key, final int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    public long getLong(final String key, final long defaultValue) {
        return sharedPrefs.getLong(key, defaultValue);
    }

    public float getFloat(final String key, final float defaultValue) {
        return sharedPrefs.getFloat(key, defaultValue);
    }

    public void remove(final String key) {
        sharedPrefs.edit().remove(key).apply();
    }

    public String getPreferredTemp() {
        return sharedPrefs.getString(KEY_PREFERRED_TEMP, Temperature.TEMP_F);
    }

    public void setPreferredTemp(String preferredTemp) {
        put(KEY_PREFERRED_TEMP, preferredTemp);
    }

    public String getSavedLocations() {
        return sharedPrefs.getString(KEY_SAVED_LOCATIONS, "");
    }

    public void setSavedLocations(String savedLocations) {
        put(KEY_SAVED_LOCATIONS, savedLocations);
    }
}
