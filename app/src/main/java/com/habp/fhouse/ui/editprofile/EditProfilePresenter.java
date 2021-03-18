package com.habp.fhouse.ui.editprofile;

import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.User;

public class EditProfilePresenter implements EditProfileContract.Presenter {
    private UserFirestoreRepository userFirestoreRepository;
    private EditProfileContract.View mView;

    public EditProfilePresenter(UserFirestoreRepository userFirestoreRepository, EditProfileContract.View mView) {
        this.userFirestoreRepository = userFirestoreRepository;
        this.mView = mView;
    }

    @Override
    public void onUpdate(String name, String phoneNumber) {
        // Validation
        // Name
        if (name.length() == 0) {
            mView.onInvalidName("Name is required");
            return;
        }
        // Phone number
        if (phoneNumber.length() != 10) {
            mView.onInvalidPhoneNumber("Phone number is required. Has to be 10 numbers");
            return;
        }

        User user = new User();
        user.setUserName(name);
        user.setPhone(phoneNumber);
        // Call API
        userFirestoreRepository.updateUser(user, isSuccess -> {
            if (isSuccess) {
                mView.onUpdateSuccess();
            } else {
                mView.onUpdateFailed("Update failed");
            }
        });
    }

    @Override
    public void getUserProfile() {
        // Call API
        userFirestoreRepository.getUserInfo(user -> {
            if (user != null) {
                mView.onGetUserProfileSuccess(user);
            } else {
                mView.onGetUserProfileFailed("Get user profile failed");
            }
        });
    }
}
