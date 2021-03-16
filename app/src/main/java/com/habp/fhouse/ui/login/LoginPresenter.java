package com.habp.fhouse.ui.login;

import com.habp.fhouse.data.repository.FirebaseAuthRepository;

public class LoginPresenter implements LoginContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private LoginContract.View mView;

    public LoginPresenter(FirebaseAuthRepository firebaseAuthRepository, LoginContract.View mView) {
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

        boolean isSuccess = firebaseAuthRepository.signIn(email,password);
        if (isSuccess) {
            mView.onLoginSuccess();
        } else {
            mView.onLoginFailed("Login Failed");
        }
    }
}
