package com.allexis.weatherapp.ui.module.home;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.event.PermissionGrantedEvent;
import com.allexis.weatherapp.core.network.service.weather.WeatherController;
import com.allexis.weatherapp.core.network.service.weather.WeatherEvent;
import com.allexis.weatherapp.core.util.RuntimePermissionUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by allexis on 10/12/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private WeatherController controller;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.controller = new WeatherController();
    }

    @Override
    public void onStart() {
        EventDispatcher.register(this);
    }

    @Override
    public void onResume() {
        verifyLocationPermission();
    }

    @Override
    public void onStop() {
        EventDispatcher.unregister(this);
    }

    private void verifyLocationPermission() {
        RuntimePermissionUtil.requestLocationPermission(view.getContainerActivity());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPermissionGrantedEvent(PermissionGrantedEvent event) {
        if (Manifest.permission.ACCESS_FINE_LOCATION.equals(event.getPermission())) {
            try {
                LocationManager lm = (LocationManager) view.getContainerActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                location = location == null ? lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) : location;
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                controller.getWeather(latitude, longitude);
            } catch (SecurityException e) {
                //This point shouldn't be reached, permission is already being checked on RuntimePermissionUtil
                Log.d(TAG, "onCreateView: Unexpected SecurityException caught @onCreateView", e);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent event) {
        if (event.isSuccessful()) {
            view.updateCurrentWeather(event.getResponseObject());
        } else if (event.getResponseObject() == null) {
            view.showLongToast(view.getContainerActivity().getString(R.string.unable_to_process_request));
        } else {
            view.showLongToast(view.getContainerActivity().getString(R.string.network_issues_weather));
        }
    }
}
