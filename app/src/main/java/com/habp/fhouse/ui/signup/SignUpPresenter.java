package com.habp.fhouse.ui.signup;

import com.habp.fhouse.data.datasource.FirebaseAuthRepository;

public class SignUpPresenter implements SignUpContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private SignUpContract.View mView;

    public SignUpPresenter(FirebaseAuthRepository firebaseAuthRepository, SignUpContract.View mView) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.mView = mView;
    }

    @Override
    public void onSignUp(String email, String password, String confirmPsw, String name, String phoneNumber) {
        if (email.length() == 0) {
            mView.onInvalidEmail("Email is required");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.onInvalidEmail("Invalid Email address");
            return;
        }
        if (password.length() < 6) {
            mView.onInvalidPassword("Password at least 6 character");
            return;
        }
        if (confirmPsw.length() < 6) {
            mView.onInvalidConfirmPassword("Confirm password is required");
            return;
        }
        if (!confirmPsw.equals(password)) {
            mView.onInvalidConfirmPassword("Confirm password is not matching");
            return;
        }
        if (name.length() == 0) {
            mView.onInvalidName("Name is required");
            return;
        }
        if (phoneNumber.length() != 10) {
            mView.onInvalidPhoneNumber("Phone number is required. Has to be 10 numbers");
            return;
        }
        firebaseAuthRepository.signUp(email, password, name, phoneNumber, isSuccess -> {
            if (isSuccess) {
                mView.onSignUpSuccess();
            } else {
                mView.onSignUpFailed("Your email is exist");
            }
        });
    }
}
