package com.habp.fhouse.data.model;

public class House {
    private String ownerID;
    private String houseID;
    private String houseName;
    private String photoPath;
    private String address;


    public House() {
    }

    public House(String ownerID, String houseID, String houseName, String photoPath, String address) {
        this.ownerID = ownerID;
        this.houseID = houseID;
        this.houseName = houseName;
        this.photoPath = photoPath;
        this.address = address;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
