package com.example.quan_ly_kho_hang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quan_ly_kho_hang.database.dbHelper;
import com.example.quan_ly_kho_hang.model.Bill;
import com.example.quan_ly_kho_hang.model.BillDetail;
import com.example.quan_ly_kho_hang.model.Product;

import java.util.ArrayList;

public class billDao {
    private static  dbHelper dbHelper;

    public billDao(Context context) {
        dbHelper = new dbHelper(context);
    }

    public ArrayList<Bill> selectAll() {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("select * from Bill", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setProductID(cursor.getInt(1));
                bill.setName(cursor.getString(2));
                bill.setQuantity(cursor.getString(3));
                bill.setCreateByUser(cursor.getString(4));
                bill.setCreatedDate(cursor.getString(5));
                bill.setNote(cursor.getString(6));
                list.add(bill);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //delete bill
    public boolean delete(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        long row = database.delete("Bill", "id=?", new String[]{String.valueOf(id)});
        return row > 0;
    }

    public boolean insert(Bill bill) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productID", bill.getProductID());
        values.put("name", bill.getName());
        values.put("quantity", bill.getQuantity());
        values.put("createByUser", bill.getCreateByUser());
        values.put("createdDate", bill.getCreatedDate());
        values.put("note", bill.getNote());
        long row = database.insert("Bill", null, values);
        return row > 0;
    }
    //    int id;// ma hoa don
//    int productID; // khoa ngoai
//    String name; // tên sản phẩm
//    String quantity;//<0 nhap kho >0 xuat kho;
//    String createByUser;// tao boi ai
//    String createDate; // ngay tao hoa don
//    String note; // ghi chu
    public boolean update(Bill bill) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productID", bill.getProductID());
        values.put("name", bill.getName());
        values.put("quantity", bill.getQuantity());
        values.put("createByUser", bill.getCreateByUser());
        values.put("createdDate", bill.getCreatedDate());
        values.put("note", bill.getNote());
        long row  = database.update("Bill",values,"id=?", new String[]{String.valueOf(bill.getId())});
        return row > 0;
    }

    /// check hoa don theo ngay
    public ArrayList<Bill> checkHoaDon() {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Bill WHERE createdDate LIKE '2023-10-18%'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setProductID(cursor.getInt(1));
                bill.setName(cursor.getString(2));
                bill.setQuantity(cursor.getString(3));
                bill.setCreateByUser(cursor.getString(4));
                bill.setCreatedDate(cursor.getString(5));
                bill.setNote(cursor.getString(6));
                list.add(bill);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /// kiem tra hoa don theo nguoi dung
    public ArrayList<Bill> checkUser() {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Bill WHERE createdDate LIKE '2023-10-18%'", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setProductID(cursor.getInt(1));
                bill.setName(cursor.getString(2));
                bill.setQuantity(cursor.getString(3));
                bill.setCreateByUser(cursor.getString(4));
                bill.setCreatedDate(cursor.getString(5));
                bill.setNote(cursor.getString(6));
                list.add(bill);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Bill> tongSL() {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT billID, SUM(quantity) AS total_quantity FROM BillDetail GROUP BY billID", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setQuantity(cursor.getString(2));
                list.add(bill);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static Bill getBillID(String billID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Bill bill = null;

        // Thực hiện truy vấn để lấy thông tin sản phẩm dựa trên productId
        String[] columns = {"id", "productID", "name", "quantity", "createByUser", "createdDate","note"}; // Thay đổi cột theo cấu trúc của bạn
        String selection = "id = ?";
        String[] selectionArgs = {billID};
        Cursor cursor = db.query("Bill", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy thông tin từ Cursor và tạo đối tượng Product
            int idIndex = cursor.getColumnIndex("id");
            int productIDIndex = cursor.getColumnIndex("productID");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int createByUserIndex = cursor.getColumnIndex("createByUser");
            int createDateIDIndex = cursor.getColumnIndex("createdDate");
            int noteIndext = cursor.getColumnIndex("note");

            int id = cursor.getInt(idIndex);
            String productID = String.valueOf(cursor.getInt(productIDIndex));
            String name = cursor.getString(nameIndex);
            String quantity = cursor.getString(quantityIndex);
            String createByUser = cursor.getString(createByUserIndex);
            String createDate = cursor.getString(createDateIDIndex);
            String note = cursor.getString(noteIndext);

      //      product = new Product(id, name, quantity, price, photo, userID);
            bill = new Bill(id,Integer.parseInt(productID),name,quantity,createByUser,createDate,note);
            // Đóng Cursor sau khi sử dụng
            cursor.close();
        }
        // Đóng cơ sở dữ liệu
        db.close();
        return bill;
    }
}
