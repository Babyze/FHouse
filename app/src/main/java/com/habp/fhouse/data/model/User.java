package com.habp.fhouse.data.model;

public class User {
    private String userId, fullName, email, address, photoPath, phone;

    public User() {}

    public User(String userId, String fullName, String email, String address, String photoPath, String phone) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.photoPath = photoPath;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return fullName;
    }

    public void setUserName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
