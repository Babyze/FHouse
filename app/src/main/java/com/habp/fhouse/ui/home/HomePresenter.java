package com.habp.fhouse.ui.home;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.util.CallBack;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private ArticleFirestoreRepository articleFirestoreRepository;
    private UserFirestoreRepository userFirestoreRepository;
    private HomeContract.View mView;

    public HomePresenter(ArticleFirestoreRepository articleFirestoreRepository, UserFirestoreRepository userFirestoreRepository, HomeContract.View mView) {
        this.articleFirestoreRepository = articleFirestoreRepository;
        this.userFirestoreRepository = userFirestoreRepository;
        this.mView = mView;
    }

    @Override
    public void loadUserInfo() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            userFirestoreRepository.getUserInfo(user -> {
                mView.showUserInfo(user);
            });
        } else {
            mView.showUserInfo(null);
        }
    }

    @Override
    public void loadArticle(int nextResult, CallBack<List<Article>> callBack) {
        articleFirestoreRepository.getNewestArticleList(nextResult, articleList -> {
            callBack.onSuccessListener(articleList);
        });
    }
}
