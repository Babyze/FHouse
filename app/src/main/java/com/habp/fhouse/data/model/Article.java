package com.habp.fhouse.data.model;

public class Article {
    private String articleID, articleName, address;
    private float price;
    private House house;


    public Article(String articleID, String articleName,String address, float price, House house) {
        this.articleID = articleID;
        this.articleName = articleName;
        this.address = address;
        this.price = price;
        this.house = house;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
