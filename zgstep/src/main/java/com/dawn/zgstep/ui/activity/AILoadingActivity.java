package com.dawn.zgstep.ui.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawn.zgstep.R;
import com.dawn.zgstep.ui.activity.adapters.PopupListAdapter;
import com.dawn.zgstep.ui.ctm.views.CircleToRectView;
import com.dawn.zgstep.ui.ctm.views.ShapeTransitionView;
import com.rye.base.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class AILoadingActivity extends AppCompatActivity {

    private Button mBtn1;
    public static void jumpTarget(Context context) {
        Intent intent = new Intent(context, AILoadingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_loading);
        mBtn1 = findViewById(R.id.btn_pop);
        //替换成popUpWindow
//        CircleToRectView circleToRectView = findViewById(R.id.circle_to_rect_view);
//        circleToRectView.setOnClickListener(v -> circleToRectView.toggleShape());
        PopupWindow popupWindow = createPopupWindow();
        mBtn1.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAsDropDown(mBtn1,0,-350);
            }
        });
    }

    // 在你的Activity或Fragment中
    private PopupWindow createPopupWindow() {
        // 引入布局 inflater

        // 创建RecyclerView的布局
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null);
        RecyclerView recyclerView = popupView.findViewById(R.id.recycler_view);

        // 设置RecyclerView的LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 准备数据集和Adapter
        List<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3"); // ...更多项
        items.add("Item 4");
        items.add("Item 5");
        items.add("Item 6");
        items.add("Item 7");

        PopupListAdapter adapter = new PopupListAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // 创建PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(this,80));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        return popupWindow;

    }


}