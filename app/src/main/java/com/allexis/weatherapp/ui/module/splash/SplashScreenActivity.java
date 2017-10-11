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
import com.allexis.weatherapp.ui.module.home.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity implements Animator.AnimatorListener {

    private static final String ANIM_PROPERTY_NAME_SCALE_X = "scaleX";
    private static final String ANIM_PROPERTY_NAME_SCALE_Y = "scaleY";
    private static final int ANIM_DURATION = 1000;
    private static final int ANIM_DELAY = 1000;
    private static final int ANIM_REPEAT_COUNT = 3;
    private ImageView splashImage;
    private ObjectAnimator splashImageAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashImage = findViewById(R.id.splash_image);

        animateSplashImage();
    }

    private void animateSplashImage() {
        if (splashImageAnimator == null || splashImageAnimator.isStarted()) {
            splashImageAnimator = ObjectAnimator.ofPropertyValuesHolder(splashImage,
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_SCALE_X, 1F, 1.2F, 1F),
                    PropertyValuesHolder.ofFloat(ANIM_PROPERTY_NAME_SCALE_Y, 1F, 1.2F, 1F));
            splashImageAnimator.setStartDelay(ANIM_DELAY);
            splashImageAnimator.setDuration(ANIM_DURATION);
            splashImageAnimator.setInterpolator(new FastOutSlowInInterpolator());
            splashImageAnimator.setRepeatCount(ANIM_REPEAT_COUNT);
            splashImageAnimator.addListener(this);
            splashImageAnimator.start();
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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
