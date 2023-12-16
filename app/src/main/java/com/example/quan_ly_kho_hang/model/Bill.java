package com.example.quan_ly_kho_hang.model;

public class Bill {
    // Bill
    int id;// ma hoa don
    int productID; // khoa ngoai
    String name; // tên sản phẩm
    String quantity;//<0 nhap kho >0 xuat kho;
    String createByUser;// tao boi ai
    String createdDate; // ngay tao hoa don
    String note; // ghi chu

    public Bill() {
    }

    public Bill(int id, int productID, String name, String quantity, String createByUser, String createdDate, String note) {
        this.id = id;
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.createByUser = createByUser;
        this.createdDate = createdDate;
        this.note = note;
    }

    public Bill(int id, String quantity, String createByUser) {
        this.id = id;
        this.quantity = quantity;
        this.createByUser = createByUser;
    }

    public Bill(int productID, String name, String quantity, String createByUser, String createdDate, String note) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.createByUser = createByUser;
        this.createdDate = createdDate;
        this.note = note;
    }

    public Bill(String name, String quantity, String createByUser, String createdDate, String note) {
        this.name = name;
        this.quantity = quantity;
        this.createByUser = createByUser;
        this.createdDate = createdDate;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    public String getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    // create table Bill(id int primary key autoincrement ,productID integer,name text,quanlity text,createByUser text, createdDate text,note text )
}
