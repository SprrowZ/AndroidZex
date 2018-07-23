package com.example.myappsecond.CustomViews;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myappsecond.BaseActivity;

/**
 * Created by Zzg on 2017/11/21.
 */

public class Custom_Fivth extends BaseActivity {
    private CircleImageView circleImageView=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circleImageView=new CircleImageView(this);
        setContentView(circleImageView);

    }
}
