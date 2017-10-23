package com.allexis.weatherapp.ui.module.home;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.event.common.PermissionGrantedEvent;
import com.allexis.weatherapp.core.event.common.WeatherSavedItemClickedEvent;
import com.allexis.weatherapp.core.network.service.group.GroupController;
import com.allexis.weatherapp.core.network.service.group.GroupEvent;
import com.allexis.weatherapp.core.network.service.weather.WeatherController;
import com.allexis.weatherapp.core.network.service.weather.WeatherEvent;
import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;
import com.allexis.weatherapp.core.persist.data.SavedLocation;
import com.allexis.weatherapp.core.util.RuntimePermissionUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by allexis on 10/12/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private final WeatherController weatherController;
    private final GroupController groupController;
    private final List<WeatherResponse> savedLocationsWeather;
    private List<Integer> savedLocationCityIds;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.weatherController = new WeatherController();
        this.groupController = new GroupController();
        this.savedLocationsWeather = new ArrayList<>();
    }

    @Override
    public void onStart() {
        EventDispatcher.register(this);
    }

    @Override
    public void onResume() {
        verifyLocationPermission();
        fetchSavedLocations(true);
    }

    @Override
    public void onStop() {
        EventDispatcher.unregister(this);
    }

    public List<WeatherResponse> getSavedLocationsWeather() {
        return savedLocationsWeather;
    }

    private void verifyLocationPermission() {
        RuntimePermissionUtil.requestLocationPermission(view.getContainerActivity());
    }

    private void fetchSavedLocations(boolean reset) {
        if (savedLocationCityIds == null || reset) {
            savedLocationCityIds = SavedLocation.getSavedLocations();
            groupController.getGroupByCitiesId(savedLocationCityIds);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PermissionGrantedEvent event) {
        if (Manifest.permission.ACCESS_FINE_LOCATION.equals(event.getPermission())) {
            try {
                LocationManager lm = (LocationManager) view.getContainerActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                location = location == null ? lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) : location;
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                weatherController.getWeather(latitude, longitude);
            } catch (SecurityException e) {
                //This point shouldn't be reached, permission is already being checked on RuntimePermissionUtil
                Log.d(TAG, "onCreateView: Unexpected SecurityException caught @onCreateView", e);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherEvent event) {
        if (event.isSuccessful()) {
            view.updateCurrentWeather(event.getResponseObject());
        } else if (event.getResponseObject() == null) {
            view.showLongToast(view.getContainerActivity().getString(R.string.unable_to_process_request));
        } else {
            view.showLongToast(view.getContainerActivity().getString(R.string.network_issues_weather));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupEvent event) {
        if (event.isSuccessful()) {
            savedLocationsWeather.clear();
            savedLocationsWeather.addAll(event.getResponseObject().getList());
            view.updateSavedLocations();
        } else {
            view.showLongToast(view.getContainerActivity().getString(R.string.error_unable_to_retrieve_saved_locations_weather));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WeatherSavedItemClickedEvent event) {
        view.openSavedWeatherDetail(event.getWeather().getId());
    }
}
