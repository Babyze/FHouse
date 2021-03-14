package com.habp.fhouse.ui.firsttime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;

public class FirstTimeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FirstTimeAdapter firstTimeAdapter;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        SharedPreferences sharedPreferences = getSharedPreferences("FHouse", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if(!isFirstRun) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstRun", false).apply();
        }

        viewPager = findViewById(R.id.welcomePager);

        layouts = new int[] {
                R.layout.firsttime_first,
                R.layout.firsttime_second,
                R.layout.firsttime_third
        };

        firstTimeAdapter = new FirstTimeAdapter(this, layouts);
        viewPager.setAdapter(firstTimeAdapter);
    }

    public void clickToNextPage(View view) {
        if(viewPager.getCurrentItem() + 1 < layouts.length) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("FHouse", MODE_PRIVATE).edit();
            editor.putBoolean("isFirstRun", false).apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}