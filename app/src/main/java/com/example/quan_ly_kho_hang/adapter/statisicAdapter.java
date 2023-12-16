package com.example.quan_ly_kho_hang.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.dao.statisicDao;
import com.example.quan_ly_kho_hang.model.Product;

import java.util.ArrayList;

public class statisicAdapter extends RecyclerView.Adapter<statisicAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Product> list;
    statisicDao statisicDao;

    public statisicAdapter(Context context, ArrayList<Product> list) {
        Log.d("DataAdapter", "Adapter created with context");
        this.context = context;
        this.list = list;
        statisicDao = new statisicDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_statisic, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("DataAdapter", "Binding data for position: " + position);
        holder.txtName.setText("Tên sản phẩm :"+list.get(position).getName());
        holder.txtGia.setText("Giá sản phẩm :"+list.get(position).getPrice());
        holder.txtSoLuong.setText("Số lượng sản phẩm :"+list.get(position).getQuantity());
        int SL = Integer.parseInt(list.get(position).getQuantity());
        int gia = Integer.parseInt(list.get(position).getPrice());
        int tongTien = SL * gia;
        holder.txtTongTien.setText("Tổng tiền :"+String.valueOf(tongTien));
        if (tongTien<0){
            holder.txtTrangThai.setText("Trạng thái : nhập kho");
        }else {
            holder.txtTrangThai.setText("Trạng thái : xuất kho");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtSoLuong,txtGia,txtTongTien,txtTrangThai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameST);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuongST);
            txtGia = itemView.findViewById(R.id.txtGiaST);
            txtTongTien = itemView.findViewById(R.id.txtTongTienST);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
        }
    }

}
