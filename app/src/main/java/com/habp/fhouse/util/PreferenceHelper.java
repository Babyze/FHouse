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
            setFirstTime(false, key);
        }
        return isFirstRun;
    }

    public void setFirstTime(boolean status, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, false).apply();
    }

}
