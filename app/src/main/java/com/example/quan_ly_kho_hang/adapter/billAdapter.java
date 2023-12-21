package com.example.quan_ly_kho_hang.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.dao.billDao;
import com.example.quan_ly_kho_hang.model.Bill;
import com.example.quan_ly_kho_hang.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class billAdapter extends RecyclerView.Adapter<billAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Bill> list;
    billDao billDao;
    EditText edtUDProductIDB, edtUDNameB, edtUDQuantityB, edtUDCreateByB, edtUDCreatedDateB, edtUDNoteB;
    Button btnUDBill, btnCancel;

    public billAdapter(Context context, ArrayList<Bill> list) {
        this.context = context;
        this.list = list;
        billDao = new billDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_bill, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = list.get(position);
        holder.txtCreateBy.setText("ID :" + list.get(position).getCreateByUser());
        holder.txtNgayTao.setText("Ngày tạo :" + list.get(position).getCreatedDate());
        holder.txtGhiChu.setText("Ghi Chú :" + list.get(position).getNote());
        //     holder.txtSoLuong.setText("Số lượng :"+ list.get(position).getQuantity());
        int sl = Integer.parseInt(list.get(position).getQuantity());
        if (sl < 0) {
            holder.txtSoLuong.setText("xuat kho");
        } else {
            holder.txtSoLuong.setText("nhap kho");
        }
        holder.btnDeleteBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("chú ý");
                builder.setMessage("bạn có chắc chắn muốn xóa không ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (billDao.delete(bill.getId())) {
                            list.clear();
                            list.addAll(billDao.selectAll());
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
        holder.btnUpdateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogUD(bill);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCreateBy, txtSoLuong, txtNgayTao, txtGhiChu;
        Button btnDeleteBill, btnUpdateBill;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCreateBy = itemView.findViewById(R.id.txtCreateBy);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtNgayTao = itemView.findViewById(R.id.txtNgayTao);
            txtGhiChu = itemView.findViewById(R.id.txtGhiChu);
            btnDeleteBill = itemView.findViewById(R.id.btnDeleteBill);
            btnUpdateBill = itemView.findViewById(R.id.btnUpdateBill);
        }
    }

    public void openDiaLogUD(Bill bill) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_bill, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //   EditText edtUDProductIDB,edtUDNameB,edtUDQuantityB,edtUDCreateByB,edtUDCreatedDateB,edtUDNoteB;
        edtUDProductIDB = view.findViewById(R.id.edtUDProductIDB);
        edtUDNameB = view.findViewById(R.id.edtUDNameB);
        edtUDQuantityB = view.findViewById(R.id.edtUDQuantityB);
        edtUDCreateByB = view.findViewById(R.id.edtUDCreateByB);
        edtUDCreatedDateB = view.findViewById(R.id.edtUDCreatedDateB);
        edtUDNoteB = view.findViewById(R.id.edtUDNoteB);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnUDBill = view.findViewById(R.id.btnUDBill);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // gan du lieu len view
        edtUDProductIDB.setText(String.valueOf(bill.getProductID()));
        edtUDNameB.setText(bill.getName());
        edtUDQuantityB.setText(bill.getQuantity());
        edtUDCreateByB.setText(bill.getCreateByUser());
        edtUDCreatedDateB.setText(bill.getCreatedDate());
        edtUDNoteB.setText(bill.getNote());

        btnUDBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill.setProductID(Integer.parseInt(edtUDProductIDB.getText().toString()));
                bill.setName(edtUDNameB.getText().toString());
                bill.setQuantity(edtUDQuantityB.getText().toString());
                bill.setCreateByUser(edtUDCreateByB.getText().toString());
                bill.setCreatedDate(edtUDCreatedDateB.getText().toString());
                bill.setNote(edtUDNoteB.getText().toString());

                if (billDao.update(bill)) {
                    list.clear();
                    list.addAll(billDao.selectAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "update thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "update thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
