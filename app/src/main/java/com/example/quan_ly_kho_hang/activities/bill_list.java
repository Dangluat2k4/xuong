package com.example.quan_ly_kho_hang.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.adapter.billAdapter;
import com.example.quan_ly_kho_hang.dao.billDao;
import com.example.quan_ly_kho_hang.dao.productDao;
import com.example.quan_ly_kho_hang.dao.userDao;
import com.example.quan_ly_kho_hang.model.Bill;
import com.example.quan_ly_kho_hang.model.Product;
import com.example.quan_ly_kho_hang.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class bill_list extends Fragment {
    Calendar calendar = Calendar.getInstance();
    RecyclerView recyclerView;
    FloatingActionButton fltAddBill;
    billAdapter adapter;
    billDao billDao;
    EditText edtAProductIDB,edtANameB,edtAQuantityB,edtACreateByB,edtACreatedDateB,edtANoteB;
    Spinner spnMaSP,spnSLSP,spnCreateBy;
    Button btnABill, btnACancel;
    TextView txtSL,txtCreateBy,txtPriceBl;
    private ArrayList<Bill> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_list, container, false);
        recyclerView = view.findViewById(R.id.rcvBill);
        fltAddBill = view.findViewById(R.id.fltAddBill);
        billDao = new billDao(getActivity());
        list = billDao.selectAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new billAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        fltAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBill();
            }
        });
        return view;
    }
    public void addBill() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // taolay out
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_bill, null);
        // gan layout len view
        builder.setView(view);
        // tao hop thoai
        Dialog dialog = builder.create();
        dialog.show();
//        EditText edtAProductIDB,edtANameB,edtAQuantityB,edtACreateByB,edtACreatedDateB,edtANoteB;
//        Button btnABill, btnACancel;
        edtANameB = view.findViewById(R.id.edtANameB);
        spnMaSP = view.findViewById(R.id.spnMaSP);
        edtACreatedDateB = view.findViewById(R.id.edtACreatedDateB);
        edtANoteB = view.findViewById(R.id.edtANoteB);
        txtPriceBl = view.findViewById(R.id.txtPriceBl);
        btnABill = view.findViewById(R.id.btnABill);
        btnACancel = view.findViewById(R.id.btnACancel);
        txtSL = view.findViewById(R.id.txtSL);
        txtCreateBy = view.findViewById(R.id.txtCreateBy);
        edtACreatedDateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themlich();
            }
        });
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getPR(),
                android.R.layout.simple_list_item_1,
                new String[]{"id"},
                new int[]{android.R.id.text1}
        );


        spnMaSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy thông tin từ item được chọn
                HashMap<String, Object> selectedProduct = (HashMap<String, Object>) parentView.getItemAtPosition(position);

                // Lấy giá trị từ HashMap
                int productId = (int) selectedProduct.get("id");

                // Truy vấn cơ sở dữ liệu để lấy thông tin cần thiết
                Product product = productDao.getProductById(String.valueOf(productId));

                // Hiển thị thông tin lên giao diện
                txtSL.setText(String.valueOf(product.getQuantity()));
                txtCreateBy.setText(String.valueOf(product.getUserID()));
                txtPriceBl.setText(String.valueOf(product.getPrice()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Đưa ra xử lý khi không có mục nào được chọn
            }
        });



        SimpleAdapter simpleAdapter1 = new SimpleAdapter(
                getContext(),
                getPR(),
                android.R.layout.simple_list_item_1,
                new String[]{"quantity"},
                new int[]{android.R.id.text1}
        );
        SimpleAdapter simpleAdapter2 = new SimpleAdapter(
                getContext(),
                getPR(),
                android.R.layout.simple_list_item_1,
                new String[]{"userID"},
                new int[]{android.R.id.text1}
        );
        SimpleAdapter simpleAdapter3 = new SimpleAdapter(
                getContext(),
                getPR(),
                android.R.layout.simple_list_item_1,
                new String[]{"price"},
                new int[]{android.R.id.text1}
        );
        spnMaSP.setAdapter(simpleAdapter);
        btnABill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtANameB.getText().toString();
                String quanlity = txtSL.getText().toString();
                String creaateBy = txtCreateBy.getText().toString();
                String createDate = edtACreatedDateB.getText().toString();
                String note = edtANoteB.getText().toString();
                String price = txtPriceBl.getText().toString();
                HashMap<String, Object> hsLS = (HashMap<String, Object>) spnMaSP.getSelectedItem();
                int maSP = (int) hsLS.get("id");
                Bill bill = new Bill (Integer.valueOf(maSP), name, quanlity, creaateBy, createDate, note,price);
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quanlity) || TextUtils.isEmpty(creaateBy) || TextUtils.isEmpty(createDate) || TextUtils.isEmpty(note)) {
                    Toast.makeText(getActivity(), "vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                } else if (billDao.insert(bill)) {
                    list.clear();
                    list.addAll(billDao.selectAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "ban da them thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "ban da them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private ArrayList<HashMap<String, Object>> getPR() {
        productDao productDao = new productDao(getContext());
        ArrayList<Product> list1 = productDao.selectAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Product product : list1) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id", product.getId());
//            hs.put("name", product.getName());
            hs.put("quantity", product.getQuantity());
            hs.put("userID", product.getUserID());
            hs.put("price",product.getPrice());
            listHM.add(hs);
        }
        return listHM;
    }
    protected void themlich() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String ngay = "";
                String thang = "";
                if (dayOfMonth < 10) {
                    ngay = "0" + dayOfMonth;
                } else {
                    ngay = String.valueOf(dayOfMonth);
                }
                if ((month + 1) < 10) {
                    thang = "0" + (month + 1);
                } else {
                    thang = String.valueOf(month + 1);
                }
                edtACreatedDateB.setText(year + "/" + (thang) + "/" + ngay);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}