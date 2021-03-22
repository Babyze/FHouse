package com.habp.fhouse.ui.boarding.room.create;

import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.DatabaseConstraints;

public class CreateRoomPresenter implements CreateRoomContract.Presenter{
    private CreateRoomContract.View mView;
    private RoomFirestoreRepository roomFirestoreRepository;
    private FirebaseStorageRemote firebaseStorageRemote;

    public CreateRoomPresenter(CreateRoomContract.View mView, RoomFirestoreRepository roomFirestoreRepository, FirebaseStorageRemote firebaseStorageRemote) {
        this.mView = mView;
        this.roomFirestoreRepository = roomFirestoreRepository;
        this.firebaseStorageRemote = firebaseStorageRemote;
    }

    @Override
    public void createRoom(Room room, byte[] imageByte) {
        String imagePath = DatabaseConstraints.BOARDING_IMAGE_PATH + room.getRoomId() + ".jpg";
        firebaseStorageRemote.uploadImage(imageByte, imagePath, isUploadSuccess -> {
            if(isUploadSuccess) {
                room.setPhotoPath(imagePath);
                roomFirestoreRepository.createRoom(room, isSuccess -> {
                    if(isSuccess)
                        mView.onCreateSuccess("Create OK");
                    else
                        mView.onCreateFailed("Create Failed");
                });
            }
        });
    }
}
