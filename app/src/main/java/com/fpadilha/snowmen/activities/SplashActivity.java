package com.fpadilha.snowmen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fpadilha.snowmen.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by felipe on 08/07/2016.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_DURATION = 4000;

    @AfterViews void afterViews(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                MainActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
                finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }
}
