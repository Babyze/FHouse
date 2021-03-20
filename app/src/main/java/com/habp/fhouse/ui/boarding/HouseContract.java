package com.habp.fhouse.ui.boarding;

import com.habp.fhouse.data.model.House;

import java.util.List;

public interface HouseContract {
    interface View {
        void showHouseList(List<House> listHouse);

        void startSignInActivity();

        void redirectToHomePage();
    }

    interface Presenter {
        void loadHouse();

        void checkAuthorize(boolean isReturn);
    }
}
