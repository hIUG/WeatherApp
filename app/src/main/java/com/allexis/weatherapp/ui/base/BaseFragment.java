package com.allexis.weatherapp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by allexis on 10/12/17.
 * Base fragment to be extended by all fragments in the app. This makes it easier to add/modify
 * common functionality among all fragments in the app.
 */

public abstract class BaseFragment extends Fragment implements BaseContract.BaseView {

    private FragmentInteractionListener fragmentInteractionListener;

    @Override
    public void onStart() {
        super.onStart();
//        EventDispatcher.subscribe(this);
    }

    @Override
    public void onStop() {
//        EventDispatcher.unsubscribe(this);
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            fragmentInteractionListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement "
                    + FragmentInteractionListener.class.getCanonicalName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
    }

    protected abstract void init();

    public abstract String getFragmentTag();

    protected <F extends BaseFragment> void goToNewFragment(F fragment) {
        fragmentInteractionListener.goToNewFragment(fragment);
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public interface FragmentInteractionListener {
        <F extends BaseFragment> void goToNewFragment(F fragment);
    }
}