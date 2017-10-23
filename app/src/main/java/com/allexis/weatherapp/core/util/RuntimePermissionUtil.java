package com.allexis.weatherapp.core.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.event.common.PermissionGrantedEvent;

/**
 * Created by allexis on 10/14/17.
 */

public final class RuntimePermissionUtil {

    public static final int LOCATION_PERMISSION_CODE = 1;
    private static final String TAG = RuntimePermissionUtil.class.getSimpleName();

    /**
     * No need of constructor on util classes
     */
    private RuntimePermissionUtil() {
    }

    private static boolean hasPermission(final Activity activity, final String permission) {
        boolean hasPermission = ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;

        if (hasPermission) {
            EventDispatcher.post(new PermissionGrantedEvent(Manifest.permission.ACCESS_FINE_LOCATION));
        }

        return hasPermission;
    }

    public static boolean hasLocationPermission(final Activity activity) {
        return hasPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private static boolean shouldShowPermitionRationale(final Activity activity, final String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    private static boolean shouldShowLocationPermitionRationale(final Activity activity) {
        return shouldShowPermitionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private static void requestPermissions(final Activity activity, final int reqCode, final String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, reqCode);
    }

    private static void requestLocationPermissions(final Activity activity) {
        requestPermissions(activity, LOCATION_PERMISSION_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void requestLocationPermission(final Activity activity) {
        if (hasLocationPermission(activity)) {
            return;
        }
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                R.string.permission_rationale_location, Snackbar.LENGTH_INDEFINITE)
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        Log.d(TAG, "onDismissed: Snackbar rationale for location dismissed");
                    }
                });
        if (shouldShowLocationPermitionRationale(activity)) {
            snackbar.setAction(R.string.grant_permission, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestLocationPermissions(activity);
                }
            });
        } else {
            snackbar.setAction(R.string.open_app_preferences, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.openAppSettings(activity);
                }
            });
        }
        snackbar.show();
    }
}
