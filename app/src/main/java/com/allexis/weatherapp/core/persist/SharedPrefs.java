package com.allexis.weatherapp.core.persist;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by allexis on 10/19/17.
 *
 * Simple class to manage a singleton SharedPreferences instance. Put methods are rewrote to have a
 * single overridden version of put using method overloading instead of having several different calls
 * to putString(), putInt(), etc... there's just one put() method overloaded. Also, no need of calling
 * edit() and aplply() methods.
 *
 * Methods to get() values are not rewrote since there would be no code improvements.
 */

public class SharedPrefs {

    private static SharedPrefs instance;
    private SharedPreferences sharedPrefs;

    private SharedPrefs(final Context context) {
        sharedPrefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void init(final Context context) {
        if (instance == null) {
            instance = new SharedPrefs(context);
        }
    }

    public static SharedPrefs getInstance() {
        return instance;
    }

    public static SharedPreferences getPrefs() {
        return instance.sharedPrefs;
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

    public void remove(final String key) {
        sharedPrefs.edit().remove(key).apply();
    }
}
