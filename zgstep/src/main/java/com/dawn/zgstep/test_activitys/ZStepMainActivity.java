package com.dawn.zgstep.test_activitys;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dawn.zgstep.R;

import com.dawn.zgstep.mvvm.model.ConfigViewModel;
import com.rye.base.BaseActivity;
import com.rye.base.utils.ToastHelper;

public class ZStepMainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ZStepMainActivity";
    TextView btn1;

    TextView btn2;


    public static void start(Context context){
        Intent intent = new Intent(context,ZStepMainActivity.class);
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
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
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
}
