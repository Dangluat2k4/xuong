package com.example.quan_ly_kho_hang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quan_ly_kho_hang.database.dbHelper;
import com.example.quan_ly_kho_hang.model.BillDetail;
import com.example.quan_ly_kho_hang.model.Product;
import com.example.quan_ly_kho_hang.model.User;

import java.util.ArrayList;

public class productDao {
    private static dbHelper dbHelper;

    public productDao(Context context) {
        dbHelper = new dbHelper(context);
    }
    public ArrayList<Product> selectAll(){
        ArrayList<Product>list= new ArrayList<>();
        SQLiteDatabase database =dbHelper.getReadableDatabase();
        try{
            Cursor cursor =database.rawQuery("select * from Product", null);
            if (cursor.getCount()>0){
                cursor.moveToFirst();
            }while (!cursor.isAfterLast()){
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setQuantity(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                product.setPhoto(cursor.getBlob(4));
                product.setUserID(cursor.getString(5));
                list.add(product);
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public  boolean update(Product product){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",product.getName());
        values.put("quantity",product.getQuantity());
        values.put("price",product.getPrice());
        values.put("photo",product.getPhoto());
        values.put("userID",product.getUserID());
        long row  = database.update("Product",values,"id=?", new String[]{String.valueOf(product.getId())});
        return row>0;
    }
    public  boolean insert(Product product){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",product.getName());
        values.put("quantity",product.getQuantity());
        values.put("price",product.getPrice());
        values.put("photo",product.getPhoto());
        values.put("userID",product.getUserID());
        long row  = database.insert("Product",null,values);
        return row>0;
    }
    public boolean delete(int id){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        long row =database.delete("Product","id=?", new String[]{String.valueOf(id)});
        return row>0;
    }

    public static Product getProductById(String productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Product product = null;

        // Thực hiện truy vấn để lấy thông tin sản phẩm dựa trên productId
        String[] columns = {"id", "name", "quantity", "price", "photo", "userID"}; // Thay đổi cột theo cấu trúc của bạn
        String selection = "id = ?";
        String[] selectionArgs = {productId};
        Cursor cursor = db.query("Product", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Lấy thông tin từ Cursor và tạo đối tượng Product
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int priceIndex = cursor.getColumnIndex("price");
            int photoIndex = cursor.getColumnIndex("photo");
            int userIDIndex = cursor.getColumnIndex("userID");

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String quantity = cursor.getString(quantityIndex);
            String price = cursor.getString(priceIndex);
            byte[] photo = cursor.getBlob(photoIndex);
            String userID = cursor.getString(userIDIndex);

            product = new Product(id, name, quantity, price, photo, userID);

            // Đóng Cursor sau khi sử dụng
            cursor.close();
        }

        // Đóng cơ sở dữ liệu
        db.close();

        return product;
    }
//    int id;// khoa chinh - tu tang 0-n
//    String name;// ten san pham
//    // luu kho xuat ko nam trong hoa don
//    String quantity;// so luong cua san pham
//    String price;// gia cua san pham
//    String photo;//anh cua san pham khi vao kho
//    String userID;// id cua nguoi tao
}
