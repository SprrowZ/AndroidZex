package com.example.myappsecond.project.customViews;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/3/8.
 */

public class CtmEighthActivity extends Activity {
    private String origin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        origin = getIntent().getStringExtra("origin");
        if (origin.equals("Telescope")) {
            setContentView(R.layout.bcustom_telescope);
        } else if (origin.equals("DistortionView")) {
            setContentView(R.layout.bcustom_distortionview);
        }
    }
}