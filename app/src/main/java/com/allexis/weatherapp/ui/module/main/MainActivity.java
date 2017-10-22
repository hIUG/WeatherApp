package com.allexis.weatherapp.ui.module.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.core.util.RuntimePermissionUtil;
import com.allexis.weatherapp.ui.base.BaseFragment;
import com.allexis.weatherapp.ui.module.home.HomeFragment;

import static com.allexis.weatherapp.core.util.AnimationConstants.ANIM_PROPERTY_NAME_ALPHA;
import static com.allexis.weatherapp.core.util.AnimationConstants.ANIM_PROPERTY_NAME_SCALE_X;
import static com.allexis.weatherapp.core.util.AnimationConstants.ANIM_PROPERTY_NAME_SCALE_Y;
import static com.allexis.weatherapp.core.util.AnimationConstants.HOME_ANIM_DELAY;
import static com.allexis.weatherapp.core.util.AnimationConstants.HOME_ANIM_DURATION;

/**
 * Created by allexis on 10/12/17.
 */

public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener, BaseFragment.FragmentInteractionListener {

    public static final String PERFORM_INITIAL_ANIMATION = "perform_initial_animation";

    private ImageView imageViewReversedIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //compileSdkVersion 26 - No need of casting for findViewById() anymore
        imageViewReversedIcon = findViewById(R.id.image_view_reversed_icon);

        boolean performAnimation = false;
        if (savedInstanceState == null) {
            goToNewFragment(HomeFragment.newInstance(), false);
            if (getIntent() != null && getIntent().getExtras() != null) {
                performAnimation = getIntent().getExtras().getBoolean(PERFORM_INITIAL_ANIMATION);
            }
        }
        setupAnimation(performAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {

    }

    private void setupAnimation(boolean performAnimation) {
        ObjectAnimator reversedIconAnimator;
        if (performAnimation) {
            showImageViewReverseIcon();
            reversedIconAnimator = ObjectAnimator.ofPropertyValuesHolder(imageViewReversedIcon,
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_SCALE_X, 1F, 50F),
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_SCALE_Y, 1F, 50F),
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_ALPHA, 1F, 0F));
            reversedIconAnimator.setDuration(HOME_ANIM_DURATION);
            reversedIconAnimator.setStartDelay(HOME_ANIM_DELAY);
            reversedIconAnimator.addListener(this);
            reversedIconAnimator.start();
        } else {
            hideImageViewReverseIcon();
        }
    }

    private void showImageViewReverseIcon() {
        imageViewReversedIcon.setVisibility(View.VISIBLE);
    }

    private void hideImageViewReverseIcon() {
        imageViewReversedIcon.setVisibility(View.GONE);
        init();
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        hideImageViewReverseIcon();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        hideImageViewReverseIcon();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    @Override
    public <F extends BaseFragment> void goToNewFragment(F fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_layout, fragment, fragment.getFragmentTag());
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getFragmentTag());
        }
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Restart the permission process, this app needs location permission, it's core functionality
        RuntimePermissionUtil.requestLocationPermission(this);
    }
}
