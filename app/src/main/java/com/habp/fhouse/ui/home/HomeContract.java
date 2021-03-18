package com.habp.fhouse.ui.home;

import android.text.Editable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.ArticleSnap;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.util.CallBack;

import java.util.List;

public interface HomeContract {
    interface View {
        void showArticle(boolean isLoadMore, List<Article> articles);
        void showUserInfo(User user);
        void redirectToSearch(String keyword);
    }
    interface Presenter {
        void loadUserInfo();
        void loadArticle(DocumentSnapshot snapshot, CallBack<ArticleSnap> articleSnap);
        void searchArticle(Editable searchText);
    }
}
