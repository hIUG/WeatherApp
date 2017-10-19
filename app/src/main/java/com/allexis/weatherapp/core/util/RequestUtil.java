package com.allexis.weatherapp.core.util;

import java.util.Map;
import java.util.TreeMap;

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
    public static final String REQ_FORECAST_BY_LAT_LON = "REQ_FORECAST_BY_LAT_LON";
    public static final String REQ_FORECAST_BY_ZIP = "REQ_FORECAST_BY_ZIP";
    public static final String REQ_FORECAST_BY_CITY_ID = "REQ_FORECAST_BY_CITY_ID";

    public static final Map<String, Boolean> req_cache_map_options;

    static {
        req_cache_map_options = new TreeMap<>();
        req_cache_map_options.put(REQ_WEATHER_BY_LAT_LON, true);
        req_cache_map_options.put(REQ_WEATHER_BY_ZIP, true);
        req_cache_map_options.put(REQ_WEATHER_BY_CITY_ID, true);
        req_cache_map_options.put(REQ_FORECAST_BY_LAT_LON, true);
        req_cache_map_options.put(REQ_FORECAST_BY_ZIP, true);
        req_cache_map_options.put(REQ_FORECAST_BY_CITY_ID, true);
    }

    /**
     * No need of constructor on util classes
     */
    private RequestUtil() {
    }

    public static boolean shouldCacheResponse(String reqId) {
        Boolean cacheResponseForReqId = req_cache_map_options.get(reqId);
        return cacheResponseForReqId != null && cacheResponseForReqId;
    }
}
