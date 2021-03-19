package com.habp.fhouse.ui.profile;

import com.habp.fhouse.data.model.User;

public interface ProfileContract {
    interface View {
        void onGetUserProfileSuccess(User user);
        void startSignInActivity();
        void closeActivity();
    }
    interface Presenter {
        void onSignOut();
        void onGetUserProfile();
        void checkAuthorization(boolean isReturn);
    }
}
