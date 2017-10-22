package com.allexis.weatherapp.core.persist.data;

import com.allexis.weatherapp.core.persist.CacheManager;

/**
 * Created by allexis on 10/19/17.
 */

public final class Temperature {

    public static final String TEMP_K = "K";
    public static final String TEMP_F = "°F";
    public static final String TEMP_C = "°C";
    private static String PREFERRED_TEMP;

    static {
        fetchPreferredTemp();
    }

    private Temperature() {
    }

    private static void fetchPreferredTemp() {
        PREFERRED_TEMP = CacheManager.getInstance().getPreferredTemp();
    }

    public static void savePreferredTemp(String preferredTemp) {
        PREFERRED_TEMP = preferredTemp;
        CacheManager.getInstance().setPreferredTemp(PREFERRED_TEMP);
    }

    public static String getPreferredTemp() {
        return PREFERRED_TEMP;
    }
}
