package com.habp.fhouse.ui.profile;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.util.DatabaseConstraints;

public class ProfilePresenter implements ProfileContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private UserFirestoreRepository userFirestoreRepository;
    private ProfileContract.View mView;

    public ProfilePresenter(FirebaseAuthRepository firebaseAuthRepository,
                            UserFirestoreRepository userFirestoreRepository, ProfileContract.View mView) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.userFirestoreRepository = userFirestoreRepository;
        this.mView = mView;
    }

    @Override
    public void onSignOut() {
        firebaseAuthRepository.signOut();
    }

    @Override
    public void onUpdate(String name, String phoneNumber, byte[] imageByte) {
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

        userFirestoreRepository.getUserInfo(user -> {
            System.out.println("UserID: " + user.getPhone());
            user.setFullName(name);
            user.setPhone(phoneNumber);
            String imgPath = DatabaseConstraints.PROFILE_IMAGE_PATH + "/" + user.getUserId() + ".jpg";
            user.setPhotoPath(imgPath);
            // Call API
            FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
            firebaseStorageRemote.uploadImage(imageByte, imgPath, isUploadSuccess -> {
                System.out.println("imgPath: " + imgPath);
                userFirestoreRepository.updateUser(user, isUpdateSuccess -> {
                    if (isUpdateSuccess) {
                        mView.onUpdateSuccess();
                    } else {
                        mView.onUpdateFailed("Update failed");
                    }
                });
            });

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

    @Override
    public void checkAuthorization(boolean isReturn) {
        FirebaseUser user = firebaseAuthRepository.getUser();
        if(user == null) {
            if(isReturn)
                mView.redirectToHomePage(); // Chuyển về main
            else
                mView.startSignInActivity(); // Chuyển sang Sign in
        } else {
            getUserProfile();
        }
    }
}
