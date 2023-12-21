package com.example.quan_ly_kho_hang.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.adapter.statisicAdapter;
import com.example.quan_ly_kho_hang.dao.statisicDao;
import com.example.quan_ly_kho_hang.model.Product;

import java.util.ArrayList;


public class statisic extends Fragment {
    RecyclerView rcvStatisic;
    statisicDao dao;
    statisicAdapter adapter;
    ArrayList<Product> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statisic, container, false);
        rcvStatisic = view.findViewById(R.id.rcvStatisic);
        dao = new statisicDao(getActivity());
        list = dao.getTop10();
        Log.d("statisicFragment", "Number of items in the list: " + list.size()); // Thêm log để kiểm tra số lượng phần tử trong danh sách
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvStatisic.setLayoutManager(layoutManager);
        // Tạo một đối tượng CustomItemDecoration và thêm nó vào RecyclerView
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        rcvStatisic.addItemDecoration(new CustomItemDecoration(getActivity(), spacingInPixels));

        adapter = new statisicAdapter(getActivity(), list);
        rcvStatisic.setAdapter(adapter);
        return view;
    }
}