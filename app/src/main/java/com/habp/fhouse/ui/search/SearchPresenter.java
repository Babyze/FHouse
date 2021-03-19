package com.habp.fhouse.ui.search;

import com.google.firebase.firestore.DocumentSnapshot;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {
    private  SearchContract.View mView;
    private HouseFirestoreRepository houseRep;
    private ArticleFirestoreRepository articleRep;

    public SearchPresenter(SearchContract.View mView, HouseFirestoreRepository houseRep, ArticleFirestoreRepository articleRep) {
        this.mView = mView;
        this.houseRep = houseRep;
        this.articleRep = articleRep;
    }

    public SearchPresenter(SearchContract.View mView) {

    }


    @Override
    public void loadArticleResult(DocumentSnapshot snapshot, String keyword) {
        List<House> houses = new ArrayList<>();
        houseRep.getHouseListByAddress(keyword, listHouse -> {
            for(House house : listHouse) {
                System.out.println(house.getHouseAddress().toLowerCase().contains(keyword.toLowerCase()));
                if(house.getHouseAddress().toLowerCase().contains(keyword.toLowerCase())) {
                    houses.add(house);
                    if(snapshot == null) {
                        articleRep.getArticleList(house.getHouseId(), articleSnap -> {
                            mView.showResult(articleSnap);
                        });
                    } else {
                        articleRep.getArticleList(snapshot, house.getHouseId(), articleSnap -> {
                            mView.showResult(articleSnap);
                        });
                    }
                }
            }
        });

    }
}
