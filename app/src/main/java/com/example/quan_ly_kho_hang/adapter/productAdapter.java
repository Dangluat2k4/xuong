package com.example.quan_ly_kho_hang.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quan_ly_kho_hang.Interface.OnImageSelectListtenerPR;
import com.example.quan_ly_kho_hang.Interface.OnImageSelectedListener;
import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.dao.productDao;
import com.example.quan_ly_kho_hang.model.Product;
import com.example.quan_ly_kho_hang.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Product> list;
    productDao  productDao;
    EditText edtUDNamePR,edtUDQuanlityPR,edtUDPricePR,edtUDuserPR;
    private ImageView IIMGPR;
    private OnImageSelectListtenerPR imageSelectListtenerPR; // Thêm vào
    ImageButton IBUDIMGPR;
    Button btnUDUPR;
    public void setIIMG(ImageView IIMG) {
        this.IIMGPR = IIMG;
    }
    public productAdapter(Context context, ArrayList<Product> list, OnImageSelectListtenerPR imageSelectListtenerPR) {
        this.context = context;
        this.list = list;
        this.imageSelectListtenerPR = imageSelectListtenerPR;
        this.IIMGPR = IIMGPR;
        productDao = new productDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.txtSoLuong.setText("Số lượng :"+ list.get(position).getQuantity());
        holder.txtTen.setText("Tên sản phẩm :"+list.get(position).getName());
        holder.txtNguoiTao.setText("Tạo bởi :"+list.get(position).getUserID());
        holder.txtGia.setText("Giá :"+list.get(position).getPrice());
        byte[] imageByteArray = list.get(position).getPhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        if (bitmap != null) {
            holder.iIMG.setImageBitmap(bitmap);
            Log.d("xetanh", "co chua anh " + holder.iIMG);
        }
        holder.btnDeletePRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("chú ý");
                builder.setMessage("bạn có chắc chắn muốn xóa không ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (productDao.delete(product.getId())) {
                            list.clear();
                            list.addAll(productDao.selectAll());
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
        holder.btnUDPRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogUD(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSoLuong, txtTen, txtNguoiTao, txtGia;
        Button btnUDPRD, btnDeletePRD;
        ImageView iIMG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtNguoiTao = itemView.findViewById(R.id.txtNguoiTao);
            txtGia = itemView.findViewById(R.id.txtGia);
            btnUDPRD = itemView.findViewById(R.id.btnUDPRD);
            btnDeletePRD = itemView.findViewById(R.id.btnDeletePRD);
            iIMG = itemView.findViewById(R.id.iIMG);
        }
    }
    public void openDiaLogUD(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_product, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        edtUDNamePR = view.findViewById(R.id.edtUDNamePR);
        edtUDQuanlityPR = view.findViewById(R.id.edtUDQuanlityPR);
        edtUDPricePR = view.findViewById(R.id.edtUDPricePR);
        edtUDuserPR = view.findViewById(R.id.edtUDuserPR);
        IIMGPR = view.findViewById(R.id.IIMGPR);
        IBUDIMGPR = view.findViewById(R.id.IBUDIMGPR);
        btnUDUPR = view.findViewById(R.id.btnUDUPR);

        edtUDNamePR.setText(product.getName());
        edtUDQuanlityPR.setText(product.getQuantity());
        edtUDPricePR.setText(product.getPrice());
        edtUDuserPR.setText(product.getUserID());

        byte[] phoTo = product.getPhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(phoTo, 0, phoTo.length);
        if (bitmap != null) {
            IIMGPR.setImageBitmap(bitmap);
        }
        IBUDIMGPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelectListtenerPR.onImageSelected(product,IIMGPR);
            }
        });

        btnUDUPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setName(edtUDNamePR.getText().toString());
                product.setQuantity(edtUDQuanlityPR.getText().toString());
                product.setPrice(edtUDPricePR.getText().toString());
                product.setUserID(edtUDuserPR.getText().toString());

                Bitmap bitmap = ((BitmapDrawable) IIMGPR.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                product.setPhoto(byteArray);

                if (productDao.update(product)) {
                    list.clear();
                    list.addAll(productDao.selectAll());
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
