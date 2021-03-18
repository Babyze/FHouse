package com.habp.fhouse.ui.boarding.house.create;

import com.habp.fhouse.data.model.House;

public interface CreateHouseContract {
    interface View {
        void onCreateSuccess(String message);
        void onCreateFailed(String message);
        void onHouseNameError(String message);
        void onAddressError(String message);
    }
    interface Presenter {
        void createHouse(House house, byte[] imageByte);
    }
}
