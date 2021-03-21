package com.habp.fhouse.ui.article.createArticle.house;


import com.google.firebase.auth.FirebaseAuth;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.UUID;

public class CreateHousePresenter implements CreateHouseContract.Presenter {
    private CreateHouseContract.View mView;
    private HouseFirestoreRepository houseRep;
    private ArticleFirestoreRepository articleRep;
    private FirebaseAuth firebaseAuth;

    public CreateHousePresenter(CreateHouseContract.View mView,
                                HouseFirestoreRepository houseRep,
                                ArticleFirestoreRepository articleRep,
                                FirebaseAuth firebaseAuth) {
        this.mView = mView;
        this.houseRep = houseRep;
        this.articleRep = articleRep;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void loadHouseData() {

        houseRep.getHouseList(houses -> {
            mView.showHouseData(houses);
        });
    }

    @Override
    public void createNewArticle(String articleName,
                                 String description,
                                 String price, House house) {
        if(articleName.isEmpty()) {
            mView.onInvalidName("Please enter article name");
            return;
        }
        if(description.isEmpty()) {
            mView.onInvalidDescription("Please enter description");
            return;
        }
        if(price.isEmpty()) {
            mView.onInvalidPrice("Please enter price");
            return;
        }
        String articleId = UUID.randomUUID().toString();
        String userId = firebaseAuth.getUid();
        Article article = new Article(articleId, articleName, description, house.getHouseId(),
                userId, price, DatabaseConstraints.HOUSE_ARTICLE);
        articleRep.createNewArticle(article, isSuccess -> {
            if(isSuccess) {
                mView.onCreateSuccess();
            } else {
                mView.onCreateFail();
            }
        });
    }
}
