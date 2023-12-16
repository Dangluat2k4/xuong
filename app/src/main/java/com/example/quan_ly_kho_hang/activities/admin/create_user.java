package com.example.quan_ly_kho_hang.activities.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.activities.login;
import com.example.quan_ly_kho_hang.adapter.userAdapter;
import com.example.quan_ly_kho_hang.dao.userDao;
import com.example.quan_ly_kho_hang.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class create_user extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fltAdd;
    userDao userDao;
    userAdapter adapter;
    TextInputEditText edtDKTK, edtDKMK, edtDKNLMK, txtTenTT;
    Button btnDK;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_user, container, false);
        edtDKMK = view.findViewById(R.id.edtDKMK);
        edtDKTK = view.findViewById(R.id.edtDKTK);
        edtDKNLMK = view.findViewById(R.id.edtNLMK);
        txtTenTT = view.findViewById(R.id.txtTenTT);
        btnDK = view.findViewById(R.id.btnDK);
        userDao = new userDao(getActivity()); // or however you initialize it
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTT = txtTenTT.getText().toString();
                String userName = edtDKTK.getText().toString();
                String password = edtDKMK.getText().toString();
                String passwordNL = edtDKNLMK.getText().toString();
                if (TextUtils.isEmpty(tenTT) ) {
                    Toast.makeText(getActivity(), "vui long nhap ten thu thu", Toast.LENGTH_SHORT).show();
                }else if ( TextUtils.isEmpty(userName)){
                    Toast.makeText(getActivity(), "vui lòng nhập vào mã  thủ thử", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "vui lòng nhập vào mật khẩu", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(passwordNL)){
                    Toast.makeText(getActivity(), "vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(passwordNL)){
                    Toast.makeText(getActivity(), "mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }

                else {
                    try {
                        User user = new User(userName,password);
                        if (userDao.insert(user)) {
                            Toast.makeText(getActivity(), "đăng ký thành công", Toast.LENGTH_SHORT).show();
                         //   gửi dữ liệu đi
//                            Intent intent = new Intent(create_user.this, login.class);
//                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(), "đăng ký thấy bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}