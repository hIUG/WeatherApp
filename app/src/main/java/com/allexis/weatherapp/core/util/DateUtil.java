package com.allexis.weatherapp.core.util;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allexis on 10/18/17.
 */

public final class DateUtil {

    public static final String FORMAT_HH_MM = "hh:mm";
    private static final String TAG = DateUtil.class.getSimpleName();

    private DateUtil() {
    }

    public static String format(long ms, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String r = null;
        try {
            r = sdf.format(new Date(ms));
        } catch (Exception e) {
            Log.d(TAG, "format: Unexpected exception while trying to cast long date timestamp");
        }
        return r;
    }

    public static String formatHHMM(long ms) {
        return format(ms, FORMAT_HH_MM);
    }

    public static String formatHHMM(long ms, Context context) {
        return DateUtils.formatDateTime(context, ms, DateUtils.FORMAT_SHOW_TIME);
    }
}
