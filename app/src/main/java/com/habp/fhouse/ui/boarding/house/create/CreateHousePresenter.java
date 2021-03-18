package com.habp.fhouse.ui.boarding.house.create;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.util.DatabaseConstraints;

public class CreateHousePresenter implements CreateHouseContract.Presenter {
    private CreateHouseContract.View mView;
    private HouseFirestoreRepository houseFirestoreRepository;
    private FirebaseStorageRemote firebaseStorageRemote;

    public CreateHousePresenter(CreateHouseContract.View mView, HouseFirestoreRepository houseFirestoreRepository, FirebaseStorageRemote firebaseStorageRemote) {
        this.mView = mView;
        this.houseFirestoreRepository = houseFirestoreRepository;
        this.firebaseStorageRemote = firebaseStorageRemote;
    }

    @Override
    public void createHouse(House house, byte[] imageByte) {
        String imagePath = DatabaseConstraints.BOARDING_IMAGE_PATH + house.getHouseId() + ".jpg";
        firebaseStorageRemote.uploadImage(imageByte, imagePath, isUploadSuccess -> {
            if(isUploadSuccess) {
                house.setPhotoPath(imagePath);
                houseFirestoreRepository.createHouse(house, isSuccess -> {
                    if(isSuccess)
                        mView.onCreateSuccess("Create OK");
                    else
                        mView.onCreateFailed("Create Failed");
                });
            }
        });
    }
}
