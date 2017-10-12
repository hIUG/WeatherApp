package com.allexis.weatherapp.ui.module.splash;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.allexis.weatherapp.R;
import com.allexis.weatherapp.ui.module.main.MainActivity;

import static com.allexis.weatherapp.core.util.AnimationConstants.ANIM_PROPERTY_NAME_SCALE_X;
import static com.allexis.weatherapp.core.util.AnimationConstants.ANIM_PROPERTY_NAME_SCALE_Y;
import static com.allexis.weatherapp.core.util.AnimationConstants.SPLASH_ANIM_DELAY;
import static com.allexis.weatherapp.core.util.AnimationConstants.SPLASH_ANIM_DURATION;
import static com.allexis.weatherapp.core.util.AnimationConstants.SPLASH_ANIM_REPEAT_COUNT;

/**
 * Created by allexis on 10/12/17.
 */

public class SplashScreenActivity extends AppCompatActivity implements Animator.AnimatorListener {

    private ImageView splashImage;
    private ObjectAnimator splashImageAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //compileSdkVersion 26 - No need of casting for findViewById() anymore
        splashImage = findViewById(R.id.splash_image);

        animateSplashImage();
    }

    private void animateSplashImage() {
        if (splashImageAnimator == null || splashImageAnimator.isStarted()) {
            splashImageAnimator = ObjectAnimator.ofPropertyValuesHolder(splashImage,
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_SCALE_X, 1F, 1.1F, 1F),
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_SCALE_Y, 1F, 1.1F, 1F));
            splashImageAnimator.setStartDelay(SPLASH_ANIM_DELAY);
            splashImageAnimator.setDuration(SPLASH_ANIM_DURATION);
            splashImageAnimator.setInterpolator(new FastOutSlowInInterpolator());
            splashImageAnimator.setRepeatCount(SPLASH_ANIM_REPEAT_COUNT);
            splashImageAnimator.addListener(this);
            splashImageAnimator.start();
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PERFORM_INITIAL_ANIMATION, true);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onAnimationStart(Animator animation) {}

    @Override
    public void onAnimationEnd(Animator animation) {
        goToHomeActivity();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        goToHomeActivity();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {}
}
