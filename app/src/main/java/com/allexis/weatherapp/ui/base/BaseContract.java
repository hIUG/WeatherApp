package com.allexis.weatherapp.ui.base;

/**
 * Created by allexis on 10/12/17.
 */

public interface BaseContract {

    interface BaseView {
        void showShortToast(String message);

        void showLongToast(String message);
    }

    interface BasePresenter<V extends BaseView> {
    }
}
