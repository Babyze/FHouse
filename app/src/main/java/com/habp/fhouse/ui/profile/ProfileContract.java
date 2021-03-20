package com.habp.fhouse.ui.profile;

import com.habp.fhouse.data.model.User;

public interface ProfileContract {

    interface View {
        void onGetUserProfileSuccess(User user);
        void onGetUserProfileFailed(String message);
        void onUpdateSuccess();
        void onUpdateFailed(String message);
        void onInvalidName(String message);
        void onInvalidPhoneNumber(String message);
        void startSignInActivity();
        void closeActivity();
    }

    interface Presenter {
        void onSignOut();
        void getUserProfile();
        void onUpdate(String name, String phoneNumber, byte[] imageByte);
        void checkAuthorization(boolean isReturn);
    }
}
