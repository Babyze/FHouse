package com.habp.fhouse.ui.article.createArticle.house;

import com.habp.fhouse.data.model.House;

import java.util.List;

public interface CreateHouseContract {
    interface View {
        void showHouseData(List<House> houses);
        void onInvalidName(String msg);
        void onInvalidDescription(String msg);
        void onInvalidPrice(String msg);
        void onCreateSuccess();
        void onCreateFail();
    }
    interface Presenter {
        void loadHouseData();
        void createNewArticle(String articleName, String description, String price, House house);
    }
}
