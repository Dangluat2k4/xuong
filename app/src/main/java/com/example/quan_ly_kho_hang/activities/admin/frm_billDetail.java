package com.example.quan_ly_kho_hang.activities.admin;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.activities.CustomItemDecoration;
import com.example.quan_ly_kho_hang.adapter.billDetailAdapter;
import com.example.quan_ly_kho_hang.dao.billDao;
import com.example.quan_ly_kho_hang.dao.billDetailDao;
import com.example.quan_ly_kho_hang.dao.productDao;
import com.example.quan_ly_kho_hang.model.Bill;
import com.example.quan_ly_kho_hang.model.BillDetail;
import com.example.quan_ly_kho_hang.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class frm_billDetail extends Fragment {
    RecyclerView rcvBillDT;
    FloatingActionButton fltAddBillDT;
    billDetailAdapter adapter;
    billDetailDao detailDao;
    Spinner spnABillDT;
    TextView txtQuanLityDT, txtCreateDateDT;
    Button btnABillDT, btnCancel;
    EditText edtPriceDT;
    private ArrayList<BillDetail> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_bill_detail, container, false);
        rcvBillDT = view.findViewById(R.id.rcvBillDetail);
        fltAddBillDT = view.findViewById(R.id.fltAddBillDetail);
        detailDao = new billDetailDao(getActivity());
        list = detailDao.selectAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvBillDT.setLayoutManager(layoutManager);
        // Tạo một đối tượng CustomItemDecoration và thêm nó vào RecyclerView
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        rcvBillDT.addItemDecoration(new CustomItemDecoration(getActivity(), spacingInPixels));

        adapter = new billDetailAdapter(getActivity(), list);
        rcvBillDT.setAdapter(adapter);
        fltAddBillDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBillDetail();
            }
        });
        return view;
    }

    public void addBillDetail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // taolay out
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_billdeltal, null);
        // gan layout len view
        builder.setView(view);
        // tao hop thoai
        Dialog dialog = builder.create();
        dialog.show();
        spnABillDT = view.findViewById(R.id.spnABillDT);
        txtQuanLityDT = view.findViewById(R.id.txtQuanLityDT);
        txtCreateDateDT = view.findViewById(R.id.txtCreateDateDT);
        edtPriceDT = view.findViewById(R.id.edtPriceDT);
        btnABillDT = view.findViewById(R.id.btnABillDT);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getBillDeltail(),
                android.R.layout.simple_list_item_1,
                new String[]{"id"},
                new int[]{android.R.id.text1}
        );
        spnABillDT.setAdapter(simpleAdapter);
        spnABillDT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin từ item được chọn
                HashMap<String, Object> selectedProduct = (HashMap<String, Object>) parent.getItemAtPosition(position);
                // Lấy giá trị từ HashMap
                int billDT = (int) selectedProduct.get("id");

                // Truy vấn cơ sở dữ liệu để lấy thông tin cần thiết
                // billDao product = productDao.getProductById(String.valueOf(billDT));
                Bill bill = billDao.getBillID(String.valueOf(billDT));
                // Hiển thị thông tin lên giao diện
                txtQuanLityDT.setText(String.valueOf(bill.getQuantity()));
                txtCreateDateDT.setText(String.valueOf(bill.getCreatedDate()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnABillDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quanlity = txtQuanLityDT.getText().toString();
                String createDate = txtCreateDateDT.getText().toString();
                String price  =edtPriceDT.getText().toString();
                HashMap<String, Object> hsLS = (HashMap<String, Object>) spnABillDT.getSelectedItem();
                int idBill = (int) hsLS.get("id");
                BillDetail billDetail = new BillDetail(Integer.valueOf(idBill),quanlity,createDate,price);
                if (TextUtils.isEmpty(quanlity) || TextUtils.isEmpty(createDate)) {
                    Toast.makeText(getActivity(), "vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                } else if (detailDao.insert(billDetail)) {
                    list.clear();
                    list.addAll(detailDao.selectAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "ban da them thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "ban da them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<HashMap<String, Object>> getBillDeltail() {
        billDao billDao = new billDao(getContext());
        ArrayList<Bill> list1 = billDao.selectAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Bill bill : list1) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id", bill.getId());
//            hs.put("name", product.getName());
            hs.put("quantity", bill.getQuantity());
            hs.put("userID", bill.getCreatedDate());
            listHM.add(hs);
        }
        return listHM;
    }
}