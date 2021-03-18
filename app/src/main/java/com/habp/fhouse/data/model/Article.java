package com.habp.fhouse.data.model;

import java.io.Serializable;

public class Article implements Serializable {
    private String articleId, articleName, articleDescription, houseId, roomId, bedId;
    private String houseAddress, phoneNumber, photoPath, wishListId;
    private float price;
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

    public float getPrice() {
        return price;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public Article(String articleId, String articleName, String articleDescription, String houseId, float price, int articleType) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.houseId = houseId;
        this.price = price;
        this.articleType = articleType;
    }

    public Article(String articleId, String articleName, String articleDescription, String houseId, String roomId, float price, int articleType) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.houseId = houseId;
        this.roomId = roomId;
        this.price = price;
        this.articleType = articleType;
    }

    public Article(String articleId, String articleName, String articleDescription, String houseId, String roomId, String bedId, float price, int articleType) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.houseId = houseId;
        this.roomId = roomId;
        this.bedId = bedId;
        this.price = price;
        this.articleType = articleType;
    }

    public Article() {
    }
}
