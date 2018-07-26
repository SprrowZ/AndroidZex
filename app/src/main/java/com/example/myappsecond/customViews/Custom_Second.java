package com.example.myappsecond.customViews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by Zzg on 2017/11/9.
 */

public class Custom_Second extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_color);
        //去掉状态栏
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
