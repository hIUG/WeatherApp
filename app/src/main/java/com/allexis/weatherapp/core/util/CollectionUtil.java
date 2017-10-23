package com.allexis.weatherapp.core.util;

import java.util.Collection;

/**
 * Created by allexis on 10/22/17.
 */

public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
