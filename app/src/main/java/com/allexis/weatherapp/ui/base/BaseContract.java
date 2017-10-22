package com.allexis.weatherapp.ui.base;

import android.app.Activity;

/**
 * Created by allexis on 10/12/17.
 */

public interface BaseContract {

    interface BaseView {

        void showShortToast(String message);

        void showLongToast(String message);

        Activity getContainerActivity();

        void finish();
    }

    interface BasePresenter<V extends BaseView> {

        void onStart();

        void onStop();

        void onResume();
    }
}
