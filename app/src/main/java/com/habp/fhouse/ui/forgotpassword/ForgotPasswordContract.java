package com.habp.fhouse.ui.forgotpassword;

public interface ForgotPasswordContract {
    interface View {
        void onResetPasswordSuccess();
        void onResetPasswordFailed(String message);
        void onInvalidEmail(String message);
    }
    interface Presenter {
        void onResetPassword(String email);
    }
}
