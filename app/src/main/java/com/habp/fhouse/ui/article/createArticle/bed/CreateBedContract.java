package com.habp.fhouse.ui.article.createArticle.bed;

import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;

import java.util.List;

public interface CreateBedContract {
    interface View {
        void showHouseData(List<House> houses);
        void showRoomData(List<Room> rooms);
        void showBedData(List<Bed> beds);
        void onInvalidName(String msg);
        void onInvalidDescription(String msg);
        void onInvalidPrice(String msg);
        void onCreateSuccess();
        void onCreateFail();
    }
    interface Presenter {
        void loadHouseData();
        void loadRoomData(String houseId);
        void loadBedData(String roomId);
        void createArticle(String articleName, String description,
                           String price, House house, Room room, Bed bed);
    }
}
