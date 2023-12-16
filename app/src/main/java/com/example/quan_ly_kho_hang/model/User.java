package com.example.quan_ly_kho_hang.model;

public class User {
    // user
    String userName;// khoa chinh
    String password;
    String numberPhone;
    String position;// chuc vu
    byte[] avatar;
    String profile;// gioi thieu tom tat
    String lastLogin;// lan cuoi dang nhap
    String createdDate; // ngay tao tai khoan
    String lastAction; // lan cuoi hanh dong tren he thong

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String numberPhone, String position, byte[] avatar, String profile, String lastLogin, String createdDate, String lastAction) {
        this.userName = userName;
        this.password = password;
        this.numberPhone = numberPhone;
        this.position = position;
        this.avatar = avatar;
        this.profile = profile;
        this.lastLogin = lastLogin;
        this.createdDate = createdDate;
        this.lastAction = lastAction;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }
}
   // create table User (userName text primary key,password text,numberPhone text,positon text,avatar text,profile text,lastLogin text,screatedDate text,lastAction text )
