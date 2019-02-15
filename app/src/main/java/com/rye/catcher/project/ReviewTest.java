package com.rye.catcher.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.rye.catcher.R;
import com.rye.catcher.activity.ctmactivity.CtmTopActivity;
import com.rye.catcher.activity.AsyncMainActivity;
import com.rye.catcher.project.review.BaseAdapterTest;
import com.rye.catcher.project.review.SpannableStringTest;
import com.rye.catcher.project.review.myAsyncTask;
import com.rye.catcher.project.review.myAsyncTask_pg;

/**
 * Created by ZZG on 2017/10/25.
 */

public class ReviewTest extends Activity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    AlertDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        initView();
    }

    private void initView() {
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        btn8=findViewById(R.id.btn8);
        btn9=findViewById(R.id.btn9);
        btn10=findViewById(R.id.btn10);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
               Intent intent1=new Intent(this, BaseAdapterTest.class);
                startActivity(intent1);
            break;
            case R.id.btn2:
                //获取屏幕宽高
                WindowManager manager = this.getWindowManager();
                DisplayMetrics outMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(outMetrics);
                int width = outMetrics.widthPixels;
                int height = outMetrics.heightPixels;
//dialog 测试
                mDialog= new  AlertDialog.Builder(this).create();
                View view=this.getLayoutInflater().inflate(R.layout.review_dialog,null);
                //mDialog.setCancelable(false);
               mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mDialog.setView(view);
                mDialog.show();
                //设置Dialog的大小
                WindowManager.LayoutParams paramss=mDialog.getWindow().getAttributes();
                paramss.height= (int) (height*0.8);
               // mDialog.getWindow().setGravity(Gravity.BOTTOM);
                mDialog.getWindow().setAttributes(paramss);
                break;
            case R.id.btn3:
                Intent intent2=new Intent(this, CtmTopActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn4:

                break;
            case R.id.btn5:
                Intent intent5=new Intent(this,myAsyncTask.class);
                startActivity(intent5);
                break;
            case R.id.btn6:
                Intent intent6=new Intent(this,myAsyncTask_pg.class);
                startActivity(intent6);
                break;
            case R.id.btn7:
                Intent intent7=new Intent(this, AsyncMainActivity.class);
                startActivity(intent7);
                break;
            case  R.id.btn8:
                startActivity(new Intent(this,SpannableStringTest.class));
                break;

        }
    }
}
