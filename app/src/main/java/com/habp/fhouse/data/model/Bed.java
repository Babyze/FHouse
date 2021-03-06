package com.habp.fhouse.data.model;

import java.io.Serializable;

public class Bed implements Serializable {
    private String bedId, bedName, photoPath, roomId;

    public Bed() {
    }

    public Bed(String bedId, String bedName, String photoPath, String roomId) {
        this.bedId = bedId;
        this.bedName = bedName;
        this.photoPath = photoPath;
        this.roomId = roomId;
    }

    public Bed(String bedId, String bedName, String roomId) {
        this.bedId = bedId;
        this.bedName = bedName;
        this.roomId = roomId;
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

    @Override
    public String toString() {
        return this.getBedName();
    }
}
