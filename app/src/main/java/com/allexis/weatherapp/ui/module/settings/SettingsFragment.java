package com.allexis.weatherapp.ui.module.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.persist.CacheManager;
import com.allexis.weatherapp.ui.base.TaggableBaseFragment;

/**
 * Created by allexis on 10/22/17.
 */

public class SettingsFragment extends PreferenceFragment implements TaggableBaseFragment, SharedPreferences.OnSharedPreferenceChangeListener {

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);

        bindPreferenceSummaryToValue(CacheManager.KEY_USERNAME);
        bindPreferenceSummaryToValue(CacheManager.KEY_PREFERRED_TEMP);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public String getFragmentTag() {
        return SettingsFragment.class.getCanonicalName();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        String stringValue = sharedPreferences.getString(key, null);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            preference.setSummary(
                    index >= 0
                            ? listPreference.getEntries()[index]
                            : null);

        } else {
            preference.setSummary(stringValue);
        }
    }

    private void bindPreferenceSummaryToValue(String key) {
        onSharedPreferenceChanged(getPreferenceManager().getSharedPreferences(), key);
    }
}
