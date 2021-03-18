package com.habp.fhouse.ui.boarding;

import com.habp.fhouse.data.model.House;

import java.util.List;

public interface HouseContract {
    interface View {
        void showHouseList(List<House> listHouse);
    }
    interface Presenter {
        void loadHouse();
    }
}
