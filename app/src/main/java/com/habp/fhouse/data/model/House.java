package com.habp.fhouse.data.model;

public class House {
    private String ownerID;
    private String nameHouse;
    private int photo;
    private String address;
    private String area;


    public House() {
    }

    public House(String ownerID, String nameHouse, int photo, String address, String area) {
        this.ownerID = ownerID;
        this.nameHouse = nameHouse;
        this.photo = photo;
        this.address = address;
        this.area = area;
    }
    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getNameHouse() {
        return nameHouse;
    }

    public void setNameHouse(String nameHouse) {
        this.nameHouse = nameHouse;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
