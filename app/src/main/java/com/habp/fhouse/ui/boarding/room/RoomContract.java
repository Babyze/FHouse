package com.habp.fhouse.ui.boarding.room;

import com.habp.fhouse.data.model.Room;

import java.util.List;

public interface RoomContract {
    interface View {
        void showRoomList(List<Room> listRoom);
    }
    interface Presenter{
        void loadRoom(String houseId);
    }
}
