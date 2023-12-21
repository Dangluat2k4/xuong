package com.example.quan_ly_kho_hang.activities.admin;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quan_ly_kho_hang.Interface.OnImageSelectListtenerPR;
import com.example.quan_ly_kho_hang.Interface.OnImageSelectedListener;
import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.activities.CustomItemDecoration;
import com.example.quan_ly_kho_hang.adapter.productAdapter;
import com.example.quan_ly_kho_hang.dao.productDao;
import com.example.quan_ly_kho_hang.dao.userDao;
import com.example.quan_ly_kho_hang.model.Product;
import com.example.quan_ly_kho_hang.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class productList extends Fragment implements OnImageSelectListtenerPR {
    private ImageView IIMGPR;
    RecyclerView recyclerView;
    productDao productDao;
    productAdapter adapter;
    private ArrayList<Product> list = new ArrayList<>();
    int RESQUEST_CODE_FORDER = 456;
    FloatingActionButton fltAddProduct;
    EditText edtANamePR, edtAQuanlityPR, edtAPricePR, edtAUserPR;
    Spinner spnUR;
    ImageButton IBAIMGPR;
    ImageView IAIMGPR;
    Button btnAPR;

    public productList() {
        // Required empty public constructor
    }

    public void setIIMG(ImageView IIMG) {
        this.IIMGPR = IIMG;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        recyclerView = view.findViewById(R.id.rcv);
        productDao = new productDao(getActivity());
        list = productDao.selectAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // Tạo một đối tượng CustomItemDecoration và thêm nó vào RecyclerView
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new CustomItemDecoration(getActivity(), spacingInPixels));

        adapter = new productAdapter(getActivity(), list, this);
        recyclerView.setAdapter(adapter);
        fltAddProduct = view.findViewById(R.id.fltAddProduct);
        fltAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        return view;
    }

    public void addProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // taolay out
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_product, null);
        // gan layout len view
        builder.setView(view);
        // tao hop thoai
        Dialog dialog = builder.create();
        dialog.show();

//    EditText edtANamePR,edtAQuanlityPR,edtAPricePR,edtAuserPR;
//    ImageButton IBAIMGPR;
//    Button btnAPR;
        edtANamePR = view.findViewById(R.id.edtANamePR);
        edtAQuanlityPR = view.findViewById(R.id.edtAQuanlityPR);
        edtAPricePR = view.findViewById(R.id.edtAPricePR);
        spnUR = view.findViewById(R.id.spnUR);
        IBAIMGPR = view.findViewById(R.id.IBAIMGPR);
        IAIMGPR = view.findViewById(R.id.IAIMGPR);
        btnAPR = view.findViewById(R.id.btnAPR);
        IBAIMGPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
                pickImageIntent.setType("image/*");
                //chi  lay anh trong thu muc image
                startActivityForResult(pickImageIntent, RESQUEST_CODE_FORDER);
                IAIMGPR.setVisibility(View.VISIBLE);
            }
        });
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLS(),
                android.R.layout.simple_list_item_1,
                new String[]{"userName"},
                new int[]{android.R.id.text1}
        );
        spnUR.setAdapter(simpleAdapter);
        btnAPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namePR = edtANamePR.getText().toString();
                String quantityPR = edtAQuanlityPR.getText().toString();
                String pricePR = edtAPricePR.getText().toString();
                HashMap<String, Object> hsLS = (HashMap<String, Object>) spnUR.getSelectedItem();
                String tenUR = (String) hsLS.get("userName");
                // chuyen data cua img sang byte
                BitmapDrawable drawable = (BitmapDrawable) IAIMGPR.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // chuyen hinh ve
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                // khai bao mang chua du lieu
                byte[] img = stream.toByteArray();
                Product product = new Product(namePR, quantityPR, pricePR, img, tenUR);
                if (TextUtils.isEmpty(namePR) || TextUtils.isEmpty(quantityPR) || TextUtils.isEmpty(pricePR) || TextUtils.isEmpty(tenUR)) {
                    Toast.makeText(getActivity(), "vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                } else if (productDao.insert(product)) {
                    list.clear();
                    list.addAll(productDao.selectAll());
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
                if (!(IIMGPR == null)) {
                    IIMGPR.setImageBitmap(bitmap);
                } else {
                    IAIMGPR.setImageBitmap(bitmap);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelected(Product product, ImageView IIMGPR) {
        if (IIMGPR == null) {
            IIMGPR = IAIMGPR;
        }
        this.IIMGPR = IIMGPR;
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESQUEST_CODE_FORDER);
    }

    private ArrayList<HashMap<String, Object>> getDSLS() {
        userDao userDao = new userDao(getContext());
        ArrayList<User> list1 = userDao.selectAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (User user : list1) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("userName", user.getUserName());
            listHM.add(hs);
        }
        return listHM;
    }

}
