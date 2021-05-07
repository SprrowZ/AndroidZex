package com.dawn.zgstep.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.dawn.zgstep.R;


import com.dawn.zgstep.jetpack.ConfigViewModel;
import com.rye.base.BaseActivity;
import com.rye.base.utils.ToastHelper;
import com.rye.router_annotation.Route;


@Route(
        value = "router://page/step",
        description = "step模块主页"
)
public class ZStepMainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ZStepMainActivity";
    private TextView title;
    private TextView btn1;
    private TextView btn2;

    public static void start(Context context) {
        Intent intent = new Intent(context, ZStepMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_step;
    }

    @Override
    public void initWidget() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        title = findViewById(R.id.title);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        getTitleFromRouter();
    }

    @Override
    public void initEvent() {
        new ViewModelProvider(this).get(ConfigViewModel.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn1) {
            Log.i(TAG, "BT1 Click ....");
            ToastHelper.showToastShort(this, "嘚吧嘚吧的");
        } else if (id == R.id.btn2) {
            ToastHelper.showToastShort(this, "嘀哩嘀哩呛");
            Log.i(TAG, "BT2 Click ....");
        }
    }


    private void getTitleFromRouter(){
        String titleName = getIntent().getStringExtra("stepTitle");
        title.setText(titleName);
    }

}
