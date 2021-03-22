package com.habp.fhouse.ui.wishlist;

import com.habp.fhouse.data.model.Article;

import java.util.List;

public interface WishlistContract {
    interface View {
        void startSignInActivity();
        void redirectToHomePage();
        void showWishListData(List<Article> articles);
    }
    interface Presenter {
        void checkAuthorize(boolean isReturn);
        void loadWishListData();
        void removeWishList(String articleId);
    }
}
