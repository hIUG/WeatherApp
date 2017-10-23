package com.allexis.weatherapp.ui.module.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;
import com.allexis.weatherapp.core.persist.CacheManager;
import com.allexis.weatherapp.ui.base.BaseFragment;
import com.allexis.weatherapp.ui.module.forecastdetail.ForecastDetailFragment;
import com.allexis.weatherapp.ui.module.home.adapter.HomeSavedLocationsAdapter;
import com.allexis.weatherapp.ui.module.settings.SettingsFragment;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by allexis on 10/12/17.
 */

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View,
        MaterialDialog.InputCallback, MaterialDialog.SingleButtonCallback {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private ViewGroup currentWeatherContainerCl;
    private ImageView currentWeatherIv;
    private TextView currentWeatherTitle;
    private TextView currentWeatherLocationTv;
    private TextView currentWeatherTempTv;
    private TextView currentWeatherDescTv;
    private TextView currentWeatherTempMinTv;
    private TextView currentWeatherTempMaxTv;
    private TextView currentWeatherSunriseTv;
    private TextView currentWeatherSunsetTv;
    private TextView currentWeatherRelativeTimeSunriseTv;
    private TextView currentWeatherRelativeTimeSunsetTv;
    private RecyclerView weatherSelectionsRv;
    private TextView notSavedLocationTv;
    private FloatingActionButton searchLocationFab;
    private FloatingActionButton openSettingsFab;

    private LinearLayoutManager layoutManager;
    private HomeSavedLocationsAdapter adapter;

    private int currentCityId;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        currentWeatherContainerCl = v.findViewById(R.id.current_weather_container_cl);
        currentWeatherIv = v.findViewById(R.id.current_weather_iv);
        currentWeatherTitle = v.findViewById(R.id.current_weather_title);
        currentWeatherLocationTv = v.findViewById(R.id.current_weather_location_tv);
        currentWeatherTempTv = v.findViewById(R.id.current_weather_temp_tv);
        currentWeatherDescTv = v.findViewById(R.id.current_weather_desc_tv);
        currentWeatherTempMinTv = v.findViewById(R.id.current_weather_temp_min);
        currentWeatherTempMaxTv = v.findViewById(R.id.current_weather_temp_max_tv);
        currentWeatherSunriseTv = v.findViewById(R.id.current_weather_sunrise_tv);
        currentWeatherRelativeTimeSunriseTv = v.findViewById(R.id.current_weather_relative_time_sunrise_tv);
        currentWeatherSunsetTv = v.findViewById(R.id.current_weather_sunset_tv);
        currentWeatherRelativeTimeSunsetTv = v.findViewById(R.id.current_weather_relative_time_sunset_tv);
        weatherSelectionsRv = v.findViewById(R.id.weather_selections_rv);
        notSavedLocationTv = v.findViewById(R.id.not_saved_location_tv);
        searchLocationFab = v.findViewById(R.id.search_location_fab);
        openSettingsFab = v.findViewById(R.id.open_settings_fab);

        currentWeatherContainerCl.setOnClickListener(this);
        notSavedLocationTv.setOnClickListener(this);
        searchLocationFab.setOnClickListener(this);
        openSettingsFab.setOnClickListener(this);
        weatherSelectionsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if ((layoutManager.findFirstCompletelyVisibleItemPosition() > 0) &&
                        layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition() < adapter.getItemCount() - 2) {
                    currentWeatherContainerCl.setVisibility(GONE);
                } else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    currentWeatherContainerCl.setVisibility(VISIBLE);
                }
            }
        });

        return v;
    }

    @Override
    protected void init() {
        presenter = new HomePresenter(this);

        layoutManager = new LinearLayoutManager(getContainerActivity());
        adapter = new HomeSavedLocationsAdapter(presenter.getSavedLocationsWeather());
        weatherSelectionsRv.setLayoutManager(layoutManager);
        weatherSelectionsRv.setAdapter(adapter);

        currentWeatherTitle.setText(String.format(getString(R.string.your_current_location), CacheManager.getInstance().getUsername()));
    }

    @Override
    public void updateCurrentWeather(WeatherResponse weather) {
        if (!weather.getWeather().isEmpty()) {
            Picasso.with(getContainerActivity()).load(weather.getWeather().get(0).getIconUrl()).into(currentWeatherIv);
            currentWeatherDescTv.setText(weather.getWeather().get(0).getDescription());
        }
        currentWeatherLocationTv.setText(String.format(getString(R.string.location_txt), weather.getName(), weather.getSys().getCountry()));
        currentWeatherTempTv.setText(String.format(getString(R.string.current_temp_txt), weather.getMain().getTempStr(), CacheManager.getInstance().getPreferredTemp()));
        currentWeatherTempMinTv.setText(String.format(getString(R.string.min_temp_txt), weather.getMain().getTempMinStr(), CacheManager.getInstance().getPreferredTemp()));
        currentWeatherTempMaxTv.setText(String.format(getString(R.string.max_temp_txt), weather.getMain().getTempMaxStr(), CacheManager.getInstance().getPreferredTemp()));
        currentWeatherSunriseTv.setText(String.format(getString(R.string.sunrise_at_txt), DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        currentWeatherSunsetTv.setText(String.format(getString(R.string.sunset_at_txt), DateUtils.formatDateTime(getContainerActivity(),
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME)));
        currentWeatherRelativeTimeSunriseTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunrise() * DateUtils.SECOND_IN_MILLIS, Calendar.getInstance().getTimeInMillis(), 0)));
        currentWeatherRelativeTimeSunsetTv.setText(String.format("(%s)", DateUtils.getRelativeTimeSpanString(
                weather.getSys().getSunset() * DateUtils.SECOND_IN_MILLIS, new Date().getTime(), 0)));
        currentWeatherContainerCl.setVisibility(VISIBLE);

        currentCityId = weather.getId();
    }

    @Override
    public void updateSavedLocations() {
        if (adapter.getItemCount() > 0) {
            notSavedLocationTv.setVisibility(GONE);
        } else {
            notSavedLocationTv.setVisibility(VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openSavedWeatherDetail(int cityId) {
        ForecastDetailFragment fragment = ForecastDetailFragment.newInstanceForCityId(cityId);
        goToNewFragment(fragment, fragment.getFragmentTag());
    }

    private void openSettings() {
        SettingsFragment fragment = SettingsFragment.newInstance();
        goToNewFragment(fragment, fragment.getFragmentTag());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_weather_container_cl:
                openSavedWeatherDetail(currentCityId);
                break;
            case R.id.not_saved_location_tv:
            case R.id.search_location_fab:
                displayZipSearch();
                break;
            case R.id.open_settings_fab:
                openSettings();
                break;
        }
    }

    private void displayZipSearch() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.dialog_search_zip_title)
                .content(R.string.dialog_search_zip_content)
                .inputType(InputType.TYPE_CLASS_NUMBER) //InputType.TYPE_CLASS_TEXT |
                .inputRange(5, 5)
                .positiveText(R.string.dialog_search_zip_accept)
                .negativeText(R.string.dialog_search_zip_cancel)
                .input(getString(R.string.dialog_search_zip_hint),
                        CacheManager.getInstance().getLastSearchedZipCode(), this)
                .onPositive(this)
                .show();
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (which.equals(DialogAction.POSITIVE) && dialog.getInputEditText() != null) {
            ForecastDetailFragment fragment = ForecastDetailFragment.newInstanceForZip(
                    dialog.getInputEditText().getText().toString());
            goToNewFragment(fragment, fragment.getFragmentTag());
        }
    }

    @Override
    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
    }

    @Override
    public void finish() {
        getContainerActivity().onBackPressed();
    }

    @Override
    public String getFragmentTag() {
        return HomeFragment.class.getCanonicalName();
    }
}
