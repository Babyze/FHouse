package com.habp.fhouse.ui.forgotpassword;

import com.habp.fhouse.data.datasource.FirebaseAuthRepository;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private ForgotPasswordContract.View mView;

    public ForgotPasswordPresenter(FirebaseAuthRepository firebaseAuthRepository, ForgotPasswordContract.View mView) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.mView = mView;
    }

    @Override
    public void onResetPassword(String email) {
        // Email validation
        if (email.length() == 0) {
            mView.onInvalidEmail("Email is required");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.onInvalidEmail("Invalid Email address");
            return;
        }

        firebaseAuthRepository.resetPassword(email, isSuccess -> {
            if (isSuccess) {
                mView.onResetPasswordSuccess();
            } else {
                mView.onResetPasswordFailed("Reset password failed");
            }
        });
    }
}
