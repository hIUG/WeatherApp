package com.allexis.weatherapp.core.persist.data;

import android.text.TextUtils;

import com.allexis.weatherapp.core.persist.CacheManager;
import com.allexis.weatherapp.core.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allexis on 10/22/17.
 */

public final class SavedLocation {

    public static final List<Integer> SAVED_LOCATIONS = new ArrayList<>();

    static {
        fetchSavedLocations();
    }

    private SavedLocation() {
    }

    private static void fetchSavedLocations() {
        String[] savedLocations = CacheManager.getInstance().getSavedLocations().split(TextUtil.SEPARATOR_COMMA);
        for (String savedLocation : savedLocations) {
            if (!savedLocation.isEmpty()) {
                SAVED_LOCATIONS.add(Integer.parseInt(savedLocation));
            }
        }
    }

    public static void addSavedLocation(int cityId) {
        SAVED_LOCATIONS.add(cityId);
        CacheManager.getInstance().setSavedLocations(TextUtils.join(TextUtil.SEPARATOR_COMMA, SAVED_LOCATIONS.toArray()));
    }

    public static void removeSavedLocation(int cityId) {
        SAVED_LOCATIONS.remove(cityId);
        CacheManager.getInstance().setSavedLocations(TextUtils.join(TextUtil.SEPARATOR_COMMA, SAVED_LOCATIONS.toArray()));
    }

    public static void toggleSavedLocation(int cityId) {
        if (isSavedLocation(cityId)) {
            removeSavedLocation(cityId);
        } else {
            addSavedLocation(cityId);
        }
    }

    public static boolean isSavedLocation(int cityId) {
        return SAVED_LOCATIONS.contains(cityId);
    }

    public static List<Integer> getSavedLocations() {
        return SAVED_LOCATIONS;
    }
}
