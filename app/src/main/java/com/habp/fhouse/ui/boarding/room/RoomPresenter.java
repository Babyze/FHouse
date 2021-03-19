package com.habp.fhouse.ui.boarding.room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;

public class RoomPresenter implements  RoomContract.Presenter{
    private RoomContract.View mView;

    public RoomPresenter(RoomContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadRoom(String houseId) {
        RoomFirestoreRepository roomFirestoreRepository =
                new RoomFirestoreRepository(FirebaseFirestore.getInstance());
        roomFirestoreRepository.getRoomList(houseId, listRoomData ->{
            mView.showRoomList(listRoomData);
        });
    }
}
