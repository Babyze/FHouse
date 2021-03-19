package com.habp.fhouse.ui.boarding.bed.create;

import com.habp.fhouse.data.datasource.BedFirestoreRepository;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.util.DatabaseConstraints;

public class CreateBedPresenter implements CreateBedContract.Presenter {
    private CreateBedContract.View mView;
    private BedFirestoreRepository bedFirestoreRepository;

    public CreateBedPresenter(CreateBedContract.View mView, BedFirestoreRepository bedFirestoreRepository, FirebaseStorageRemote firebaseStorageRemote) {
        this.mView = mView;
        this.bedFirestoreRepository = bedFirestoreRepository;
        this.firebaseStorageRemote = firebaseStorageRemote;
    }

    private FirebaseStorageRemote firebaseStorageRemote;

    @Override
    public void createBed(Bed bed, byte[] imageByte) {
        String imagePath = DatabaseConstraints.BOARDING_IMAGE_PATH + bed.getBedId() + ".jpg";
        firebaseStorageRemote.uploadImage(imageByte, imagePath, isUploadSuccess -> {
            if (isUploadSuccess) {
                bed.setPhotoPath(imagePath);
                bedFirestoreRepository.createBed(bed, isSuccess -> {
                    if (isSuccess)
                        mView.onCreateSuccess("Create OK");
                    else
                        mView.onCreateFailed("Create Failed");
                });
            }
        });
    }
}
