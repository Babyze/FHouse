package com.habp.fhouse.ui.articledetail;


import com.habp.fhouse.data.model.Article;

public interface ArticleDetailContract {
    interface View {
        void startSignInActivity();
        void showData();
        void setActivityWishlist(boolean status);
        void closeActivity();
    }

    interface Presenter {
        void checkAuthorization(boolean isReturn);
        void loadData(Article article);
    }
}
