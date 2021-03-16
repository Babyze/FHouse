package com.habp.fhouse.ui.signup;

public interface SignUpContract {
    interface View {
        void onSignUpSuccess();
        void onSignUpFailed(String message);
        void onInvalidEmail(String message);
        void onInvalidPassword(String message);
        void onInvalidConfirmPassword(String message);
        void onInvalidName(String message);
        void onInvalidPhoneNumber(String message);
    }
    interface Presenter {
        void onSignUp(String email, String password, String confirmPsw, String name, String phoneNumber);
    }
}
