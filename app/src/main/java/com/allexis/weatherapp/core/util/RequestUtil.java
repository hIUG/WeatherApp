package com.allexis.weatherapp.core.util;

import android.support.annotation.NonNull;

import com.allexis.weatherapp.core.persist.CacheManager;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

import static com.allexis.weatherapp.core.persist.CacheManager.CACHE_EXPIRE_TIME_MS;

/**
 * Created by allexis on 10/17/17.
 * Enums are not good performance on Android... better use @IntDef instead... just for the simplicity
 * of this example
 */

public final class RequestUtil {

    public static final String REQ_IDENTIFIER_HEADER = "Weather-Req-Id";
    public static final String REQ_IDENTIFIER_HEADER_COLON = REQ_IDENTIFIER_HEADER + ": ";
    public static final String REQ_WEATHER_BY_LAT_LON = "REQ_WEATHER_BY_LAT_LON";
    public static final String REQ_WEATHER_BY_ZIP = "REQ_WEATHER_BY_ZIP";
    public static final String REQ_WEATHER_BY_CITY_ID = "REQ_WEATHER_BY_CITY_ID";
    public static final String REQ_WEATHER_BY_CITY_NAME = "REQ_WEATHER_BY_CITY_NAME";
    public static final String REQ_FORECAST_BY_LAT_LON = "REQ_FORECAST_BY_LAT_LON";
    public static final String REQ_FORECAST_BY_ZIP = "REQ_FORECAST_BY_ZIP";
    public static final String REQ_FORECAST_BY_CITY_ID = "REQ_FORECAST_BY_CITY_ID";
    public static final String REQ_FORECAST_BY_CITY_NAME = "REQ_FORECAST_BY_CITY_NAME";
    public static final String REQ_GROUP_BY_CITIES_ID = "REQ_GROUP_BY_CITIES_ID";

    public static final Map<String, Boolean> req_cache_map_options;

    static {
        req_cache_map_options = new TreeMap<>();
        req_cache_map_options.put(REQ_WEATHER_BY_LAT_LON, true);
        req_cache_map_options.put(REQ_WEATHER_BY_ZIP, true);
        req_cache_map_options.put(REQ_WEATHER_BY_CITY_ID, true);
        req_cache_map_options.put(REQ_WEATHER_BY_CITY_NAME, true);
        req_cache_map_options.put(REQ_FORECAST_BY_LAT_LON, true);
        req_cache_map_options.put(REQ_FORECAST_BY_ZIP, true);
        req_cache_map_options.put(REQ_FORECAST_BY_CITY_ID, true);
        req_cache_map_options.put(REQ_FORECAST_BY_CITY_NAME, true);
        req_cache_map_options.put(REQ_GROUP_BY_CITIES_ID, true);
    }

    /**
     * No need of constructor on util classes
     */
    private RequestUtil() {
    }

    public static boolean shouldCacheResponse(@NonNull String reqId) {
        Boolean cacheResponseForReqId = req_cache_map_options.get(reqId);
        return cacheResponseForReqId != null && cacheResponseForReqId;
    }

    public static boolean shouldCacheResponse(@NonNull Request request) {
        String reqId = request.header(RequestUtil.REQ_IDENTIFIER_HEADER);
        return shouldCacheResponse(reqId);
    }

    public static boolean shouldCacheResponse(@NonNull Call call) {
        return shouldCacheResponse(call.request());
    }

    public static boolean shouldCacheResponse(@NonNull Response response) {
        return shouldCacheResponse(response.raw().request());
    }

    public static String getCacheSaveKey(@NonNull Call call) {
        return call.request().url().toString();
    }

    public static String getCacheSaveKey(@NonNull Response response) {
        return response.raw().request().url().toString();
    }

    public static String getExpireTimeCacheKey(@NonNull Call call) {
        return new StringBuilder(getCacheSaveKey(call) + CacheManager.CACHE_EXPIRE_TIME_KEY_ID).toString();
    }

    public static String getExpireTimeCacheKey(@NonNull Response response) {
        return new StringBuilder(getCacheSaveKey(response) + CacheManager.CACHE_EXPIRE_TIME_KEY_ID).toString();
    }

    public static boolean isCachedExpired(long cachedAt) {
        long timeDiff = Calendar.getInstance().getTimeInMillis() - cachedAt;
        return timeDiff >= CACHE_EXPIRE_TIME_MS;
    }
}
