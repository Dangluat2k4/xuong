package com.example.quan_ly_kho_hang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.quan_ly_kho_hang.activities.admin.create_product;
import com.example.quan_ly_kho_hang.activities.admin.create_user;
import com.example.quan_ly_kho_hang.activities.admin.doanhthu;
import com.example.quan_ly_kho_hang.activities.admin.frm_billDetail;
import com.example.quan_ly_kho_hang.activities.admin.productList;
import com.example.quan_ly_kho_hang.activities.admin.userList;
import com.example.quan_ly_kho_hang.activities.bill_list;
import com.example.quan_ly_kho_hang.activities.create_bill;
import com.example.quan_ly_kho_hang.activities.login;
import com.example.quan_ly_kho_hang.activities.statisic;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView nav;
    AppBarLayout frmnav;
    DrawerLayout layout;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toorbar);
        nav = findViewById(R.id.nav);
        bottomNavigationView = findViewById(R.id.bottomnav);
        layout = findViewById(R.id.drawerlayout);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Quản lý kho hàng");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layout, toolbar, R.string.open_app, R.string.close_app);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        //hien thị luôn home
        if (savedInstanceState == null) {
            bill_list billList = new bill_list();
            replaceFrg(billList);
        }
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.createUser) {
                    create_user createUser = new create_user();
                    replaceFrg(createUser);
                }
                if (item.getItemId() == R.id.statistic) {
                    statisic statisic = new statisic();
                    replaceFrg(statisic);
                }
                if (item.getItemId() == R.id.doanhThu) {
                    doanhthu doanhthu = new doanhthu();
                    replaceFrg(doanhthu);
                }
                if (item.getItemId() == R.id.log_out) {
                    Intent  intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                }

                return false;
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    userList userList = new userList();
                    replaceFrg(userList);
                }
                if (item.getItemId() == R.id.bill) {
                    bill_list billList = new bill_list();
                    replaceFrg(billList);
                }
                if (item.getItemId() == R.id.productList) {
                    productList productList = new productList();
                    replaceFrg(productList);
                }
                if (item.getItemId() == R.id.user) {
                    frm_billDetail billDetail = new frm_billDetail();
                    replaceFrg(billDetail);
                }
                return false;
            }
        });
        SharedPreferences preferences = getSharedPreferences("thongtin", MODE_PRIVATE);
        String position = preferences.getString("position", "");
        if (!position.equals("Quản lý")) {
            Menu menu = nav.getMenu();
            menu.findItem(R.id.createUser).setVisible(false);
            Menu menu1 = bottomNavigationView.getMenu();
            menu1.findItem(R.id.home).setVisible(false);

        }
    }


    public void replaceFrg(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frmnav, fragment).commit();
    }

}