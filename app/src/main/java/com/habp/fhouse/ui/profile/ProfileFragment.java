package com.habp.fhouse.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.ui.article.ArticleFragment;
import com.habp.fhouse.ui.home.HomeFragment;
import com.habp.fhouse.ui.house_management.HouseManagementFragment;
import com.habp.fhouse.ui.sign.SignInActivity;
import com.habp.fhouse.ui.sign.SignInPresenter;
import com.habp.fhouse.ui.wishlist.WishlistFragment;


public class ProfileFragment extends Fragment implements ProfileContract.View {
    private LinearLayout lnMyProfile, lnMyArticle, lnMyBoarding, lnMyWishlist;
    private Button btnSignOut, btnMyProfile, btnMyArticle, btnMyBoarding, btnMyWishlist;
    private TextView txtUsername;
    private View view = null;
    private View view2 = null;

    private FirebaseAuthRepository firebaseAuthRepository;
    private UserFirestoreRepository userFirestoreRepository;
    private ProfilePresenter profilePresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        view2 = inflater.inflate(R.layout.activity_main, container, false);

        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        userFirestoreRepository =
                new UserFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
        profilePresenter = new ProfilePresenter(firebaseAuthRepository, userFirestoreRepository,this);

        lnMyProfile = view.findViewById(R.id.lnMyProfile);
        lnMyArticle = view.findViewById(R.id.lnMyArticle);
        lnMyBoarding = view.findViewById(R.id.lnMyBoarding);
        lnMyWishlist = view.findViewById(R.id.lnMyWishlist);
        btnMyProfile = view.findViewById(R.id.btnMyProfile);
        btnMyArticle = view.findViewById(R.id.btnMyArticle);
        btnMyBoarding = view.findViewById(R.id.btnMyBoarding);
        btnMyWishlist = view.findViewById(R.id.btnMyWishlist);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        txtUsername = view.findViewById(R.id.txtUsername);
        // Set Username
        profilePresenter.checkAuthorization(true);
        // Set on click to change fragment
        this.setOnClick();
        return view;
    }

    private void setOnClick() {
        this.setOnClickMyProfile();
        this.setOnClickMyArticle();
        this.setOnClickMyBoarding();
        this.setOnClickMyWishlist();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePresenter.onSignOut();
                changeFragment(new HomeFragment());
                Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickMyProfile() {
        lnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
//                startActivity(intent);
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void setOnClickMyArticle() {
        lnMyArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ArticleFragment());
            }
        });

        btnMyArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ArticleFragment());
            }
        });
    }

    private void setOnClickMyBoarding() {
        lnMyBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new HouseManagementFragment());
            }
        });

        btnMyBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new HouseManagementFragment());
            }
        });
    }

    private void setOnClickMyWishlist() {
        lnMyWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new WishlistFragment());
            }
        });

        btnMyWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new WishlistFragment());
            }
        });
    }

    private void changeFragment(Fragment selectedFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
        fragmentTransaction.commit();

//        BottomNavigationView bottomNavigationView;
//        bottomNavigationView = (BottomNavigationView) view2.findViewById(R.id.bottom_navigation);
//        if (bottomNavigationView != null) {
//
//        } else {
//            System.out.println("NULLllllllllllllllll");
//        }
    }

    @Override
    public void onGetUserProfileSuccess(User user) {
        txtUsername.setText(user.getUserName());
    }

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void closeActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        profilePresenter.checkAuthorization(true);
    }

}
