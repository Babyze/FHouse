package com.habp.fhouse.data.model;

import java.io.Serializable;

public class WishList implements Serializable {
    private String wishListId, userId, articleId;

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public WishList(String wishListId, String userId, String articleId) {
        this.wishListId = wishListId;
        this.userId = userId;
        this.articleId = articleId;
    }

    public WishList() { }
}
