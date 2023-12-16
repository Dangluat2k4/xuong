package com.example.quan_ly_kho_hang.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
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

import com.example.quan_ly_kho_hang.Interface.OnImageSelectedListener;
import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.dao.userDao;
import com.example.quan_ly_kho_hang.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<User> list;
    private OnImageSelectedListener imageSelectedListener; // Thêm vào
    private ImageView IIMG;
    userDao userDao;
    EditText edtUDUserName, edtUDNumber, edtUDPosition, edtUDProfile;
    ImageButton IBUDIMG;
  //  ImageView IIMG;
    Button btnUDU;
    int RESQUEST_CODE_FORDER = 456;
    public void setIIMG(ImageView IIMG) {
        this.IIMG = IIMG;
    }

    public userAdapter(Context context, ArrayList<User> list, OnImageSelectedListener imageSelectedListener) {
        this.context = context;
        this.list = list;
        this.imageSelectedListener = imageSelectedListener;
        this.IIMG = IIMG;
        userDao = new userDao(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_user, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.txtUser.setText("Họ tên :"+list.get(position).getUserName());
        holder.txtNumberPhone.setText("SDT :"+String.valueOf(list.get(position).getNumberPhone()));
        holder.txtPosition.setText("Chức vụ :"+list.get(position).getPosition());
        byte[] imageByteArray = list.get(position).getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        if (bitmap != null) {
            holder.IBAvatar.setImageBitmap(bitmap);
            Log.d("xetanh", "co chua anh " + holder.IBAvatar);
        }
        holder.txtProfile.setText("Giới thiệu :"+list.get(position).getProfile());
        holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("chú ý");
                builder.setMessage("bạn có chắc chắn muốn xóa không ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (userDao.delete(user.getUserName())) {
                            list.clear();
                            list.addAll(userDao.selectAll());
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
        holder.btnUDUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogUD(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUser, txtNumberPhone, txtPosition, txtProfile;
        ImageView IBAvatar;
        Button btnUDUser, btnDeleteUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUser = itemView.findViewById(R.id.txtuser);
            txtNumberPhone = itemView.findViewById(R.id.txtNumberPhone);
            txtPosition = itemView.findViewById(R.id.txtposition);
            IBAvatar = itemView.findViewById(R.id.IBAvatar);
            txtProfile = itemView.findViewById(R.id.txtprofile);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
            btnUDUser = itemView.findViewById(R.id.btnUDUser);
        }

    }

    public void openDiaLogUD(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_user, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        edtUDUserName = view.findViewById(R.id.edtUDUserName);
        edtUDNumber = view.findViewById(R.id.edtUDNumber);
        edtUDPosition = view.findViewById(R.id.edtUDPosition);
        edtUDProfile = view.findViewById(R.id.edtUDProfile);
        IBUDIMG = view.findViewById(R.id.IBUDIMG);
        IIMG = view.findViewById(R.id.IIMG);
        btnUDU = view.findViewById(R.id.btnUDU);

        edtUDUserName.setText(user.getUserName());
        edtUDNumber.setText(user.getNumberPhone());
        edtUDPosition.setText(user.getPosition());
        edtUDProfile.setText(user.getProfile());

        byte[] avatarData = user.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatarData, 0, avatarData.length);
        if (bitmap != null) {
            IIMG.setImageBitmap(bitmap);
        }
        IBUDIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelectedListener.onImageSelected(user,IIMG);
            }
        });

        btnUDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUserName(edtUDUserName.getText().toString());
                user.setNumberPhone(edtUDNumber.getText().toString());
                user.setPosition(edtUDPosition.getText().toString());
                user.setProfile(edtUDProfile.getText().toString());

                Bitmap bitmap = ((BitmapDrawable) IIMG.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                user.setAvatar(byteArray);

                if (userDao.update(user)) {
                    list.clear();
                    list.addAll(userDao.selectAll());
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
