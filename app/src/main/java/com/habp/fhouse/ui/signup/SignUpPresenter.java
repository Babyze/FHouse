package com.habp.fhouse.ui.signup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.FirebaseAuthRemote;

public class SignUpPresenter implements SignUpContract.Presenter {
    private FirebaseAuthRemote firebaseAuthRemote;
    private SignUpContract.View mView;

    public SignUpPresenter(FirebaseAuthRemote firebaseAuthRemote, SignUpContract.View mView) {
        this.firebaseAuthRemote = firebaseAuthRemote;
        this.mView = mView;
    }

    @Override
    public void onSignUp(String email, String password, String confirmPsw, String name, String phoneNumber) {
        if (email.length() == 0) {
            mView.onInvalidEmail("Email is required");
            return;
        }
        if (password.length() < 6) {
            mView.onInvalidPassword("Password is required");
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
            mView.onInvalidConfirmPassword("Name is required");
            return;
        }
        if (phoneNumber.length() != 10) {
            mView.onInvalidConfirmPassword("Phone number is required. Has to be 10 numbers");
            return;
        }
        firebaseAuthRemote.signUp(email, password, name, phoneNumber, isSuccess -> {
            if (isSuccess) {
                mView.onSignUpSuccess();
            } else {
                mView.onSignUpFailed("Sign up failed");
            }
        });
    }
}
