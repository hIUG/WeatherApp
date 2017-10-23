package com.allexis.weatherapp.core.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.WeatherApplication;

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

    //An identifier to save and retrieve the last requested and cached time for a response... to validate it's expiration time
    public static final String CACHE_EXPIRE_TIME_KEY_ID = "@";
    public static final long CACHE_EXPIRE_TIME_MS = DateUtils.MINUTE_IN_MILLIS * 1;

    public static String KEY_USERNAME;
    public static String KEY_PREFERRED_TEMP;
    public static String KEY_SAVED_LOCATIONS;
    public static String KEY_LAST_SEARCHED_ZIP_CODE;

    private static CacheManager instance;
    private SharedPreferences sharedPrefs;

    private CacheManager(final Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void init(final Context context) {
        if (instance == null) {
            instance = new CacheManager(context);

            KEY_USERNAME = context.getString(R.string.key_username);
            KEY_PREFERRED_TEMP = context.getString(R.string.key_preferred_unit_system);
            KEY_SAVED_LOCATIONS = context.getString(R.string.key_saved_locations);
            KEY_LAST_SEARCHED_ZIP_CODE = context.getString(R.string.key_last_searched_zip_code);
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

    public String getUsername() {
        return getString(KEY_USERNAME, "");
    }

    public String getPreferredTemp() {
        return getString(KEY_PREFERRED_TEMP, WeatherApplication.getInstance().getString(R.string.valid_unit_systems_f));
    }

    public void setPreferredTemp(String preferredTemp) {
        put(KEY_PREFERRED_TEMP, preferredTemp);
    }

    public String getSavedLocations() {
        return getString(KEY_SAVED_LOCATIONS, "");
    }

    public void setSavedLocations(String savedLocations) {
        put(KEY_SAVED_LOCATIONS, savedLocations);
    }

    public String getLastSearchedZipCode() {
        return getString(KEY_LAST_SEARCHED_ZIP_CODE, WeatherApplication.getInstance()
                .getApplicationContext().getString(R.string.dialog_search_zip_default));
    }

    public void setLastSearchedZipCode(String zipCode) {
        put(KEY_LAST_SEARCHED_ZIP_CODE, zipCode);
    }
}
