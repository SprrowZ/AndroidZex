package com.example.myappsecond.project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myappsecond.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZZG on 2017/10/30.
 */

public class AmapTestActivity extends Activity {
    public TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amap);
        textView = findViewById(R.id.textView);

    }
}