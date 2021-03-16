package com.habp.fhouse.data.model;

public class House {
    private String houseId, houseName, houseAddress, photoPath, OwnerId;

    public House() {}

    public House(String houseId, String houseName, String houseAddress, String photoPath, String ownerId) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.houseAddress = houseAddress;
        this.photoPath = photoPath;
        OwnerId = ownerId;
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

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }
}
