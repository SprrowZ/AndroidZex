package com.example.myappsecond.activity;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/11/8.
 */

public class DrawableMainActivity extends BaseActivity implements View.OnClickListener{
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private View.OnClickListener listener;
    public void setMyOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable);
        initView();
    }

    private void initView() {
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.btn1:
        Intent  intent1=new Intent(this,LayerDrawableActivity.class);
        startActivity(intent1);
        break;
    case R.id.btn2:
        TransitionDrawable transition= (TransitionDrawable) btn2.getBackground();
        transition.startTransition(1000);
        break;
    case R.id.btn3:
        break;
    case R.id.btn4:
        break;
    case R.id.btn5:
        break;
    case R.id.btn6:
        break;


}
    }
}
