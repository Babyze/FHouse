package com.habp.fhouse.ui.login;

public interface LoginContract {
    interface View {
        void onLoginSuccess();
        void onLoginFailed(String message);
        void onInvalidEmail(String message);
        void onInvalidPassword(String message);
    }
    interface Presenter {
        void onLogin(String email, String password);
    }
}
