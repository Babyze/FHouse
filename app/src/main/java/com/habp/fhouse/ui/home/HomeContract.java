package com.habp.fhouse.ui.home;

import android.text.Editable;

import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.util.CallBack;

import java.util.List;

public interface HomeContract {
    interface View {
        void showArticle(List<Article> articles);
        void showUserInfo(User user);
        void redirectToSearch(String keyword);
    }
    interface Presenter {
        void loadUserInfo();
        void loadArticle(int nextResult, CallBack<List<Article>> articles);
        void searchArticle(Editable searchText);
    }
}
