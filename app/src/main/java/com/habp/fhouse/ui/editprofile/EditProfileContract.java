package com.habp.fhouse.ui.editprofile;

import com.habp.fhouse.data.model.User;

public interface EditProfileContract {
    interface View {
        void onGetUserProfileSuccess(User user);
        void onGetUserProfileFailed(String message);
        void onUpdateSuccess();
        void onUpdateFailed(String message);
        void onInvalidName(String message);
        void onInvalidPhoneNumber(String message);
    }

    interface Presenter {
        void onUpdate(String name, String phoneNumber, byte[] imageByte);
        void getUserProfile();
    }
}
