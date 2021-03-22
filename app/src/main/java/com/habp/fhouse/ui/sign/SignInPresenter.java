package com.habp.fhouse.ui.sign;

import com.habp.fhouse.data.datasource.FirebaseAuthRepository;

public class SignInPresenter implements SignInContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private SignInContract.View mView;

    public SignInPresenter(FirebaseAuthRepository firebaseAuthRepository, SignInContract.View mView) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.mView = mView;
    }

    @Override
    public void onLogin(String email, String password) {
        if (email.length() == 0) {
            mView.onInvalidEmail("Invalid Email");
            return;
        }
        if (password.length() == 0) {
            mView.onInvalidPassword("Invalid Password");
            return;
        }

        firebaseAuthRepository.signIn(email, password, isSuccess -> {
            if (isSuccess) {
                mView.onLoginSuccess();
            } else {
                mView.onLoginFailed("Sign in Failed");
            }
        });
    }
}
