package com.habp.fhouse.ui.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.User;

public class ProfilePresenter implements ProfileContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private UserFirestoreRepository userFirestoreRepository;
    private ProfileContract.View mView;

    public ProfilePresenter(FirebaseAuthRepository firebaseAuthRepository,
                            UserFirestoreRepository userFirestoreRepository, ProfileContract.View mView) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.userFirestoreRepository = userFirestoreRepository;
        this.mView = mView;
    }

    @Override
    public void onSignOut() {
        firebaseAuthRepository.signOut();
    }

    @Override
    public void onGetUserProfile() {
        userFirestoreRepository.getUserInfo(user -> mView.onGetUserProfileSuccess(user));
    }

    @Override
    public void checkAuthorization(boolean isReturn) {
        FirebaseUser user = firebaseAuthRepository.getUser();
        if(user == null) {
            if(isReturn)
                mView.closeActivity();
            else
                mView.startSignInActivity();
        } else {
            onGetUserProfile();
        }
    }
}
