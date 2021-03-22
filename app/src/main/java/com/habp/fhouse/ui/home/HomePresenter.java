package com.habp.fhouse.ui.home;


import android.text.Editable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.ArticleSnap;
import com.habp.fhouse.util.CallBack;

import java.util.Collections;
import java.util.Comparator;

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
    public void loadArticle(DocumentSnapshot snap, CallBack<ArticleSnap> callBack) {
        if(snap != null) {
            articleFirestoreRepository.getNewestArticleList(snap, articleSnap -> callBack.onSuccessListener(articleSnap));
        } else {
            articleFirestoreRepository.getNewestArticleList(articleSnap -> {
                Collections.sort(articleSnap.getArticleList(), new Comparator<Article>() {
                    @Override
                    public int compare(Article o1, Article o2) {
                        return o2.getCreateAt().compareTo(o1.getCreateAt());
                    }
                });
                callBack.onSuccessListener(articleSnap);
            });
        }
    }

    @Override
    public void searchArticle(Editable keyword) {
        if(keyword != null) {
            String keywordText = keyword.toString();
            if(!keywordText.isEmpty()) {
                mView.redirectToSearch(keywordText);
            }
        }
    }
}
