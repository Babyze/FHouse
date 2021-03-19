package com.habp.fhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.habp.fhouse.ui.article.ArticleFragment;
import com.habp.fhouse.ui.home.HomeFragment;
import com.habp.fhouse.ui.boarding.HouseManagementFragment;
import com.habp.fhouse.ui.profile.ProfileFragment;
import com.habp.fhouse.ui.wishlist.WishlistFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new HomeFragment());
        fragmentTransaction.commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_Home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_HouseManagement:
                            selectedFragment = new HouseManagementFragment();
                            break;
                        case R.id.nav_Article:
                            selectedFragment = new ArticleFragment();
                            break;
                        case R.id.nav_Wishlist:
                            selectedFragment = new WishlistFragment();
                            break;
                        case R.id.nav_Profile:
                            selectedFragment = new ProfileFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}