package com.habp.fhouse.data.model;

public class Bed {
    private String bedId, bedName, photoPath;
    private Room room;
    private House house;

    public Bed(String bedId, String bedName, String photoPath, Room room, House house) {
        this.bedId = bedId;
        this.bedName = bedName;
        this.photoPath = photoPath;
        this.room = room;
        this.house = house;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
