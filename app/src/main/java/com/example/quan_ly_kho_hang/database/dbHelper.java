package com.example.quan_ly_kho_hang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbHelper extends SQLiteOpenHelper {
    private static final String db_name = "QLKH";

    public dbHelper(Context context) {
        super(context, db_name, null, 16);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE User(userName text PRIMARY KEY,password text,numberPhone text,position text,avatar BLOB,profile text,lastLogin text,createdDate text,lastAction text)");
        db.execSQL("CREATE TABLE Product(id integer PRIMARY KEY AUTOINCREMENT,name text,quantity text,price text,photo BLOB,userID text REFERENCES user(userName))");//,foreign key (userID) references User(userName)
        db.execSQL("CREATE TABLE Bill(id integer PRIMARY KEY AUTOINCREMENT,productID integer REFERENCES Product(id),name text,quantity text REFERENCES Product(quantity),createByUser text REFERENCES Product(userID) ,createdDate text,note text,price text REFERENCES Product(price))");//, foreign key (productID) references Product(id)
        db.execSQL("CREATE TABLE BillDetail(id integer PRIMARY KEY AUTOINCREMENT,billID integer REFERENCES Bill(id),quantity text REFERENCES Bill(quantity),createdDate text REFERENCES Bill(createdDate),price text REFERENCES Bill(price))");

        db.execSQL("insert into User values ('admin', '123', '123456789', 'Quản lý', 'avatar_a.jpg', 'Xin chào, tôi là Nguyễn Văn A.', '2023-10-18 08:00:00', '2023-10-01', '2023-10-18 08:30:00'),('thukho', '456', '987654321', 'Nhân viên', 'avatar_b.jpg', 'Chào mừng bạn đến với trang web.', '2023-10-18 09:30:00', '2023-09-15', '2023-10-18 10:00:00')");
        db.execSQL("insert into Product values(1,'Sản phẩm A', '10', '50000', 'product_a.jpg', 'admin'),(2,'Sản phẩm B', '15', '75000', 'product_b.jpg', 'thukho') ");
        db.execSQL("insert into Bill values (1,'1','Hóa đơn A', '10', 'admin', '2023-10-18 11:15:00', 'Hóa đơn trả hàng','50000'),(2,'2', 'Hóa đơn B', '15', 'thukho', '2023-10-18 11:30:00', 'Bán hàng cho khách hàng','75000')");
        db.execSQL("insert into BillDetail values (1,'1','10','2023-10-18 11:15:00','50000'),(2,'2','15','2023-10-18 11:30:00','75000')");
        // them du lieu mau

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Product");
            db.execSQL("DROP TABLE IF EXISTS Bill");
            db.execSQL("DROP TABLE IF EXISTS BillDetail");
            onCreate(db);
        }
    }
}
