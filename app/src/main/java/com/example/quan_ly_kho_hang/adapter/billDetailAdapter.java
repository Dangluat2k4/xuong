package com.example.quan_ly_kho_hang.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.dao.billDetailDao;
import com.example.quan_ly_kho_hang.model.BillDetail;

import java.util.ArrayList;

public class billDetailAdapter extends RecyclerView.Adapter<billDetailAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<BillDetail> list;
    billDetailDao billDetailDao;

    public billDetailAdapter(Context context, ArrayList<BillDetail> list) {
        this.context = context;
        this.list = list;
        billDetailDao = new billDetailDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_bill_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtIDDT.setText("Mã sản phẩm : " + String.valueOf(list.get(position).getId()));
        holder.txtBID.setText("Mã Bill : " + String.valueOf(list.get(position).getBillID()));
        holder.txtQLTDT.setText("Số lượng sản phẩm : " + list.get(position).getQuantity());
        holder.txtCRDate.setText("Tạo ngày : " + list.get(position).getCreatedDate());
        holder.txtPriceDT.setText("Giá :" + String.valueOf(list.get(position).getPrice()));
        BillDetail billDetail = list.get(position);
        holder.btnDeleteDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("chú ý");
                builder.setMessage("bạn có chắc chắn muốn xóa không ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (billDetailDao.delete(billDetail.getId())) {
                            list.clear();
                            list.addAll(billDetailDao.selectAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "ban da xoa thanh cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "xoa that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIDDT, txtBID, txtQLTDT, txtCRDate, txtPriceDT;
        Button btnDeleteDT, btnUDDT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIDDT = itemView.findViewById(R.id.txtIDDT);
            txtBID = itemView.findViewById(R.id.txtBID);
            txtQLTDT = itemView.findViewById(R.id.txtQLTDT);
            txtCRDate = itemView.findViewById(R.id.txtCRDate);
            txtPriceDT = itemView.findViewById(R.id.txtPriceDT);
            btnDeleteDT = itemView.findViewById(R.id.btnDeleteDT);
        }
    }
}
