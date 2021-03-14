package com.habp.fhouse.util;

import android.content.SharedPreferences;

import com.habp.fhouse.R;

public class PreferenceHelper {

    private final SharedPreferences sharedPreferences;

    public PreferenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isFirstTime(String key) {
        boolean isFirstRun = sharedPreferences.getBoolean(key, true);
        if(isFirstRun) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(String.valueOf(R.string.is_first_run), false).apply();
        }
        return isFirstRun;
    }

}
