package com.habp.fhouse.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.ui.firsttime.FirstTimeActivity;
import com.habp.fhouse.util.PreferenceHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PreferenceHelper preferenceHelper =
                new PreferenceHelper(getSharedPreferences(getString(R.string.fhouse_pref_name), MODE_PRIVATE));
        boolean isFirstRun = preferenceHelper.isFirstTime(getString(R.string.is_first_run));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if(isFirstRun) {
                intent = new Intent(SplashActivity.this, FirstTimeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }, 2000);
    }
}