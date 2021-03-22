package com.habp.fhouse.ui.boarding.room.create;

import com.habp.fhouse.data.model.Room;

public interface CreateRoomContract {
    interface View{
        void onCreateSuccess(String message);
        void onCreateFailed(String message);
        void onRoomNameError(String message);
    }
    interface Presenter{
        void createRoom(Room room, byte[] imageByte);
    }
}
