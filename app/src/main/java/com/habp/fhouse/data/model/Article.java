package com.habp.fhouse.data.model;

import java.io.Serializable;

public class Article implements Serializable {
    private String articleId, articleName, articleDescription, houseId, roomId, bedId, userId;
    private String houseAddress, phoneNumber, photoPath, wishListId;
    private String price;
    private int articleType;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public Article(String articleId, String articleName, String articleDescription,
                   String houseId, String userId, String price, int articleType) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.houseId = houseId;
        this.userId = userId;
        this.price = price;
        this.articleType = articleType;
    }

    public Article(String articleId, String articleName, String articleDescription,
                   String houseId, String roomId, String userId, String price, int articleType) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.houseId = houseId;
        this.roomId = roomId;
        this.userId = userId;
        this.price = price;
        this.articleType = articleType;
    }

    public Article(String articleId, String articleName, String articleDescription,
                   String houseId, String roomId, String bedId, String userId, String price, int articleType) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.houseId = houseId;
        this.roomId = roomId;
        this.bedId = bedId;
        this.userId = userId;
        this.price = price;
        this.articleType = articleType;
    }

    public Article() {
    }
}
