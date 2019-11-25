package com.rye.catcher.activity.ctmactivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.WindowManager;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

/**
 * Created by Zzg on 2017/11/9.
 */

public class CtmSecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_color);
        //去掉状态栏
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
