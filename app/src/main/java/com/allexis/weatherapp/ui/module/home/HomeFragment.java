package com.allexis.weatherapp.ui.module.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.ui.base.BaseFragment;

/**
 * Created by allexis on 10/12/17.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.Presenter presenter;

    public HomeFragment() {
    }

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();

        Bundle args = new Bundle();
        homeFragment.setArguments(args);

        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void init() {

    }

    @Override
    public String getFragmentTag() {
        return HomeFragment.class.getCanonicalName();
    }
}
