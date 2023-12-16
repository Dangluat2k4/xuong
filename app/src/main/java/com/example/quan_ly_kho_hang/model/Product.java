package com.example.quan_ly_kho_hang.model;

public class Product {
    // product
    int id;// khoa chinh - tu tang 0-n
    String name;// ten san pham
    // luu kho xuat ko nam trong hoa don
    String quantity;// so luong cua san pham
    String price;// gia cua san pham
    byte[] photo;//anh cua san pham khi vao kho
    String userID;// id cua nguoi tao

    public Product() {
    }


    public Product(int id, String name, String quantity, String price, byte[] photo, String userID) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.photo = photo;
        this.userID = userID;
    }

    public Product(String name, String quantity, String price, byte[] photo, String userID) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.photo = photo;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

// create table (id integer  primary key autoincrement,name text,quality text,price text,photo text,userName )
