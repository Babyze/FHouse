package com.habp.fhouse.data.model;

public class Room {
    private String roomId, roomName, photoPath, houseId;

    public Room() { }

    public Room(String roomId, String roomName, String photoPath, String houseId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.photoPath = photoPath;
        this.houseId = houseId;
    }

    public Room(String roomId, String roomName, String houseId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.houseId = houseId;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
