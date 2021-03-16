package com.habp.fhouse.data.model;

public class Room {
    private String roomId, roomName, photoPath;
    private House house;

    public Room(String roomId, String roomName, String photoPath, House house) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.photoPath = photoPath;
        this.house = house;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
