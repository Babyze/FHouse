package com.habp.fhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.ui.article.ArticleFragment;
import com.habp.fhouse.ui.home.HomeFragment;
import com.habp.fhouse.ui.boarding.HouseManagementFragment;
import com.habp.fhouse.ui.profile.ProfileFragment;
import com.habp.fhouse.ui.wishlist.WishlistFragment;
import com.habp.fhouse.util.DatabaseConstraints;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth.getInstance().signOut();

//        FirebaseFirestore.getInstance().collection(DatabaseConstraints.HOUSE_COLLECTION_NAME)
//                .whereGreaterThanOrEqualTo("houseAddress", "QUAN 2")
//                .whereLessThanOrEqualTo("houseAddress", "quan 2" + '\uf8ff')
//
////                .whereLessThanOrEqualTo("houseAddress", "quan 2")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.getResult() != null) {
//                        for(DocumentSnapshot doc : task.getResult().getDocuments()) {
//                            House h = doc.toObject(House.class);
//                            System.out.println(h.getHouseAddress() + h.getHouseAddress().toLowerCase().equals("quan 2"));
//                        }
//                    }
//                });

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