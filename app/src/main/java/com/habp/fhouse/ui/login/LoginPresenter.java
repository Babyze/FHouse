package com.habp.fhouse.ui.login;

import com.habp.fhouse.data.datasource.FirebaseAuthRemote;

public class LoginPresenter implements LoginContract.Presenter {
    private FirebaseAuthRemote firebaseAuthRemote;
    private LoginContract.View mView;

    public LoginPresenter(FirebaseAuthRemote firebaseAuthRemote, LoginContract.View mView) {
        this.firebaseAuthRemote = firebaseAuthRemote;
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

        firebaseAuthRemote.signIn(email, password, isSuccess -> {
            if (isSuccess) {
                mView.onLoginSuccess();
            } else {
                mView.onLoginFailed("Login Failed");
            }
        });
    }
}
