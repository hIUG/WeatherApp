package com.allexis.weatherapp.ui.module.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.network.service.weather.WeatherResponse;
import com.allexis.weatherapp.ui.base.BaseFragment;
import com.allexis.weatherapp.ui.module.forecastdetail.ForecastDetailFragment;
import com.squareup.picasso.Picasso;

import static android.view.View.VISIBLE;
import static com.allexis.weatherapp.core.util.TemperatureUtil.preferredTemp;

/**
 * Created by allexis on 10/12/17.
 */

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View, MaterialDialog.InputCallback, MaterialDialog.SingleButtonCallback {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private ViewGroup currentWeatherContainerCl;
    private ImageView currentWeatherIv;
    private TextView currentWeatherLocationTv;
    private TextView currentWeatherTempTv;
    private TextView currentWeatherDescTv;
    private TextView currentWeatherTempMinTv;
    private TextView currentWeatherTempMaxTv;
    private RecyclerView weatherSelectionsRv;
    private TextView notSavedLocationTv;
    private FloatingActionButton searchLocationFab;

    private RecyclerView.LayoutManager layoutManager;
//    private

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

        this.currentWeatherContainerCl = v.findViewById(R.id.current_weather_container_cl);
        this.currentWeatherIv = v.findViewById(R.id.current_weather_iv);
        this.currentWeatherLocationTv = v.findViewById(R.id.current_weather_location_tv);
        this.currentWeatherTempTv = v.findViewById(R.id.current_weather_temp_tv);
        this.currentWeatherDescTv = v.findViewById(R.id.current_weather_desc_tv);
        this.currentWeatherTempMinTv = v.findViewById(R.id.current_weather_temp_min);
        this.currentWeatherTempMaxTv = v.findViewById(R.id.current_weather_temp_max_tv);
//        this.weatherSelectionsRv = v.findViewById(R.id.weather_selections_rv);
        this.notSavedLocationTv = v.findViewById(R.id.not_saved_location_tv);
        this.searchLocationFab = v.findViewById(R.id.search_location_fab);

        this.currentWeatherContainerCl.setOnClickListener(this);
        this.notSavedLocationTv.setOnClickListener(this);
        this.searchLocationFab.setOnClickListener(this);

        return v;
    }

    @Override
    protected void init() {
        presenter = new HomePresenter(this);
    }

    @Override
    public void updateCurrentWeather(WeatherResponse weather) {
        if (!weather.getWeather().isEmpty()) {
            Picasso.with(getContainerActivity()).load(weather.getWeather().get(0).getIconUrl()).into(this.currentWeatherIv);
            this.currentWeatherDescTv.setText(weather.getWeather().get(0).getDescription());
        }
        this.currentWeatherLocationTv.setText(String.format(getString(R.string.location_txt), weather.getName(), weather.getSys().getCountry()));
        this.currentWeatherTempTv.setText(String.format(getString(R.string.current_temp_txt), weather.getMain().getTempStr(), preferredTemp));
        this.currentWeatherTempMinTv.setText(String.format(getString(R.string.min_temp_txt), weather.getMain().getTempMinStr(), preferredTemp));
        this.currentWeatherTempMaxTv.setText(String.format(getString(R.string.max_temp_txt), weather.getMain().getTempMaxStr(), preferredTemp));
        this.currentWeatherContainerCl.setVisibility(VISIBLE);

        this.currentCityId = weather.getId();
    }

    @Override
    public String getFragmentTag() {
        return HomeFragment.class.getCanonicalName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_weather_container_cl:
                goToNewFragment(ForecastDetailFragment.newInstanceForCityId(this.currentCityId));
                break;
            case R.id.not_saved_location_tv:
            case R.id.search_location_fab:
                displayZipSearch();
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
                .input(R.string.dialog_search_zip_hint, R.string.dialog_search_zip_default, this)
                .onPositive(this)
                .show();

    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (which.equals(DialogAction.POSITIVE) && dialog.getInputEditText() != null) {
            goToNewFragment(ForecastDetailFragment.newInstanceForZip(
                    Integer.parseInt(dialog.getInputEditText().getText().toString())));
        }
    }

    @Override
    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
    }

    @Override
    public void finish() {
        getContainerActivity().onBackPressed();
    }
}
