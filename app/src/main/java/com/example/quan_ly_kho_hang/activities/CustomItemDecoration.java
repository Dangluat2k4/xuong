package com.example.quan_ly_kho_hang.activities;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public CustomItemDecoration(Context context, int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Thêm khoảng cách vào các item, có thể điều chỉnh theo nhu cầu
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = space;

        // Đừng thêm khoảng cách vào item đầu tiên để tránh khoảng cách đằng trước
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0;
        } else {
            outRect.top = space;
        }
    }
}
