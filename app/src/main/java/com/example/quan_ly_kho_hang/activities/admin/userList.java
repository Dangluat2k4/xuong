package com.example.quan_ly_kho_hang.activities.admin;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quan_ly_kho_hang.Interface.OnImageSelectedListener;
import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.activities.CustomItemDecoration;
import com.example.quan_ly_kho_hang.adapter.userAdapter;
import com.example.quan_ly_kho_hang.dao.userDao;
import com.example.quan_ly_kho_hang.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class userList extends Fragment implements OnImageSelectedListener {
    private ImageView IIMG;
    RecyclerView recyclerView;
    FloatingActionButton fltAdd;
    userDao userDao;
    userAdapter adapter;
    int RESQUEST_CODE_FORDER = 456;
    private ArrayList<User> list = new ArrayList<>();
    EditText edtAddUserName, edtAddPassword, edtAddNumberPhone, edtAddPosition, edtAddProfile, edtAddLastLogin, edtCreateDate, edtAddLastAction;
    ImageButton ibAddIMG;
    ImageView addIIMG;
    Button btnAddUser;
    public void setIIMG(ImageView IIMG) {
        this.IIMG = IIMG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView = view.findViewById(R.id.rscUser);
        fltAdd = view.findViewById(R.id.fltAddUser);
        userDao = new userDao(getActivity());
        list = userDao.selectAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new userAdapter(getActivity(),list,this);
        // Tạo một đối tượng CustomItemDecoration và thêm nó vào RecyclerView
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new CustomItemDecoration(getActivity(), spacingInPixels));

        recyclerView.setAdapter(adapter);
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser();
            }
        });
        return view;

    }

    public void AddUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // taolay out
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_user, null);
        // gan layout len view
        builder.setView(view);
        // tao hop thoai
        Dialog dialog = builder.create();
        dialog.show();
        edtAddUserName = view.findViewById(R.id.edtAddUserName);
        edtAddPassword = view.findViewById(R.id.edtAddPassword);
        edtAddNumberPhone = view.findViewById(R.id.edtAddNumberPhone);
        edtAddPosition = view.findViewById(R.id.edtAddPosition);
        edtAddProfile = view.findViewById(R.id.edtAddProfile);
        edtAddLastLogin = view.findViewById(R.id.edtAddLastLogin);
        edtCreateDate = view.findViewById(R.id.edtCreateDate);
        edtAddLastAction = view.findViewById(R.id.edtAddLastAction);
        ibAddIMG = view.findViewById(R.id.ibAddIMG);
        addIIMG = view.findViewById(R.id.addIIMG);
        ibAddIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
                pickImageIntent.setType("image/*");
                //chi  lay anh trong thu muc image
                startActivityForResult(pickImageIntent, RESQUEST_CODE_FORDER);
                addIIMG.setVisibility(View.VISIBLE);
            }
        });

        btnAddUser = view.findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtAddUserName.getText().toString();
                String password = edtAddUserName.getText().toString();
                String numberPhone = edtAddUserName.getText().toString();
                String position = edtAddUserName.getText().toString();
                String profile = edtAddUserName.getText().toString();
                String lastLogin = edtAddUserName.getText().toString();
                String createDate = edtAddUserName.getText().toString();
                String lastAction = edtAddUserName.getText().toString();
                // chuyen data cua img sang byte
                BitmapDrawable drawable = (BitmapDrawable) addIIMG.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // chuyen hinh ve
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                // khai bao mang chua du lieu
                byte[] img = stream.toByteArray();
                User user = new User(userName, password, numberPhone, position, img, profile, lastLogin, createDate, lastAction);
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(numberPhone) || TextUtils.isEmpty(position) || TextUtils.isEmpty(profile) || TextUtils.isEmpty(lastLogin) || TextUtils.isEmpty(lastAction) || TextUtils.isEmpty(createDate)) {
                    Toast.makeText(getActivity(), "vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                } else if (userDao.insertUser(user)) {
                    list.clear();
                    list.addAll(userDao.selectAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "ban da them thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "ban da them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESQUEST_CODE_FORDER && resultCode == RESULT_OK && data != null) {
            // lay du lieu uri laf duong dan trong android
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                if (IIMG == null) {
//                    addIIMG.setImageBitmap(bitmap);
//                } else {
//                    IIMG.setImageBitmap(bitmap);
//                }
                if(!(IIMG == null)){
                    IIMG.setImageBitmap(bitmap);
                }
                else {
                    addIIMG.setImageBitmap(bitmap);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onImageSelected(User user, ImageView IIMG) {
        if (IIMG == null) {
            IIMG = addIIMG;
        }
        this.IIMG = IIMG;
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESQUEST_CODE_FORDER);
    }
}
