package com.habp.fhouse.data.model;

import java.io.Serializable;

public class House implements Serializable {
    private String houseId, houseName, houseAddress, photoPath, userId;

    public House() {}

    public House(String houseId, String houseName, String houseAddress, String photoPath, String userId) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.houseAddress = houseAddress;
        this.photoPath = photoPath;
        this.userId = userId;
    }

    public House(String houseId, String houseName, String houseAddress, String userId) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.houseAddress = houseAddress;
        this.userId = userId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
