package com.allexis.weatherapp.core.network.model;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by allexis on 10/11/17.
 */

public abstract class GsonObject {

    private static final String TAG = GsonObject.class.getSimpleName();

    public static <T extends GsonObject> T fromJsonString(String json, Class<T> classOf) {
        return new Gson().fromJson(json, classOf);
    }

    public String toJsonString() {
        String jsonString = "";
        try {
            jsonString = new Gson().toJson(this);
        } catch (Exception e) {
            Log.e(TAG, "toJsonString: Unexpected exception when serializing to JSON", e);
        }
        return jsonString;
    }
}
