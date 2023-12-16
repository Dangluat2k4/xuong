package com.example.quan_ly_kho_hang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quan_ly_kho_hang.database.dbHelper;
import com.example.quan_ly_kho_hang.model.Product;
import com.example.quan_ly_kho_hang.model.User;

import java.util.ArrayList;

public class userDao {
    private final dbHelper dbHelper;
    SharedPreferences preferences;

    public userDao(Context context) {
        dbHelper = new dbHelper(context);
        preferences = context.getSharedPreferences("thongtin", Context.MODE_PRIVATE);
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("select * from User", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                User user = new User();
                user.setUserName(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setNumberPhone(cursor.getString(2));
                user.setPosition(cursor.getString(3));
                user.setAvatar(cursor.getBlob(4));
                user.setProfile(cursor.getString(5));
                user.setLastLogin(cursor.getString(6));
                user.setCreatedDate(cursor.getString(7));
                user.setLastAction(cursor.getString(8));
                list.add(user);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<User> checkUser() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("select * from User", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                User user = new User();
                user.setUserName(cursor.getString(0));
                user.setAvatar(cursor.getBlob(1));
                list.add(user);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean delete(String userName){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        long row =database.delete("User","userName=?", new String[]{userName});
        return row>0;
    }
    public  boolean update(User user){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName",user.getUserName());
        values.put("numberPhone",user.getNumberPhone());
        values.put("position",user.getPosition());
        values.put("avatar",user.getAvatar());
        values.put("profile",user.getProfile());
        long row  = database.update("User",values,"userName=?", new String[]{user.getUserName()});
        return row>0;
    }
    public boolean insertUser(User user){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("password", user.getPassword());
        values.put("numberPhone", user.getNumberPhone());
        values.put("position", user.getPosition());
        values.put("avatar", user.getAvatar());
        values.put("lastLogin", user.getLastLogin());
        values.put("createdDate", user.getCreatedDate());
        values.put("lastAction", user.getLastLogin());
        long row  = database.insert("User",null, values);
        return row>0;
    }
//    String userName;// khoa chinh
//    String password;
//    String numberPhone;
//    String position;// chuc vu
//    byte[] avatar;
//    String profile;// gioi thieu tom tat
//    String lastLogin;// lan cuoi dang nhap
//    String createdDate; // ngay tao tai khoan
//    String lastAction; // lan cuoi hanh dong tren he thong
    public boolean checkLogin(String userName, String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from User where userName=? and password=?", new String[]{userName, password});
        if (cursor.getCount() != 0) {
            //luu
            cursor.moveToFirst();
            SharedPreferences.Editor editor = preferences.edit();
            // luu gia tri
            // lay gia tri cua userName
            editor.putString("userName", cursor.getString(0));
            // luu loai tai khoan
            editor.putString("position", cursor.getString(3));
            editor.commit();
            return true;
        } else {
            return false;
        }

    }

    public boolean insert(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName",user.getUserName());
        values.put("password",user.getPassword());
        try {
            long row = db.insert("User", null, values);

            if (row > 0) {
                return true; // Insertion successful
            } else {
                return false; // Insertion failed
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Handle exception and return false
        } finally {
            db.close(); // Close the database connection
        }
    }
//    String userName;// khoa chinh
//    String password;
//    String numberPhone;
//    String position;// chuc vu
//    String avatar;
//    String profile;// gioi thieu tom tat
//    String lastLogin;// lan cuoi dang nhap
//    String screatedDate; // ngay tao tai khoan
//    String lastAction; // lan cuoi hanh dong tren he thong
}
