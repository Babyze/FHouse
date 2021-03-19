package com.habp.fhouse.ui.wishlist;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.habp.fhouse.data.datasource.WishListFirestoreRepository;

public class WishlistPresenter implements WishlistContract.Presenter {
    private WishlistContract.View mView;
    private WishListFirestoreRepository wishListRep;
    private FirebaseAuth firebaseAuth;

    public WishlistPresenter(WishlistContract.View mView,
                             WishListFirestoreRepository wishListRep,
                             FirebaseAuth firebaseAuth) {
        this.mView = mView;
        this.wishListRep = wishListRep;
        this.firebaseAuth = firebaseAuth;
    }

    public WishlistPresenter(WishListFirestoreRepository wishListRep,
                             FirebaseAuth firebaseAuth) {
        this.wishListRep = wishListRep;
        this.firebaseAuth = firebaseAuth;
    }

    public WishlistPresenter() {
    }

    @Override
    public void checkAuthorize(boolean isReturn) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null) {
            if(isReturn) {
                mView.redirectToHomePage();
            } else {
                mView.startSignInActivity();
            }
        }
    }

    @Override
    public void loadWishListData() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            String userId = firebaseUser.getUid();
            wishListRep.getArticleWishListByUserId(userId, articles -> {
                mView.showWishListData(articles);
            });
        }
    }

    @Override
    public void removeWishList(String articleId) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            wishListRep.getWishList(firebaseUser.getUid(), articleId, wishList -> {
                wishListRep.deleteWishList(wishList.getWishListId(), isSuccess -> { });
            });
        }
    }

}
