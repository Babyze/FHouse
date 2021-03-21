package com.habp.fhouse.ui.article.createArticle.room;

import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;

import java.util.List;

public interface CreateRoomContract {
    interface View {
        void showHouseData(List<House> houses);
        void showRoomData(List<Room> rooms);
        void onInvalidName(String msg);
        void onInvalidDescription(String msg);
        void onInvalidPrice(String msg);
        void onCreateSuccess();
        void onCreateFail();
    }
    interface Presenter {
        void loadHouseData();
        void loadRoomData(String houseId);
        void createArticle(String articleName, String description, String price, House house, Room room);
    }
}
