package com.habp.fhouse.ui.search;

import com.google.firebase.firestore.DocumentSnapshot;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.House;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private HouseFirestoreRepository houseRep;
    private ArticleFirestoreRepository articleRep;

    public SearchPresenter(SearchContract.View mView, HouseFirestoreRepository houseRep, ArticleFirestoreRepository articleRep) {
        this.mView = mView;
        this.houseRep = houseRep;
        this.articleRep = articleRep;
    }


    @Override
    public void loadArticleResult(DocumentSnapshot snapshot, String keyword) {
        if(keyword.isEmpty()) {
            return;
        }
        houseRep.getHouseListByAddress(listHouse -> {
            for(House house : listHouse) {
                if(house.getHouseAddress().toLowerCase().contains(keyword.toLowerCase())) {
                    if(snapshot == null) {
                        articleRep.getSearchArticleList(house.getHouseId(), articleSnap -> {
                            mView.showResult(false, articleSnap);
                        });
                    } else {
                        articleRep.getSearchArticleList(snapshot, house.getHouseId(), articleSnap -> {
                            mView.showResult(true, articleSnap);
                        });
                    }
                }
            }
        });

    }
}
