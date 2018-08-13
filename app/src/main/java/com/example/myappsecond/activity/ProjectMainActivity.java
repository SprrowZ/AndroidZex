package com.example.myappsecond.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.project.MenuActivity;
import com.example.myappsecond.project.SlidingConflict;
import com.example.myappsecond.project.animations.AnimShapeActivity;
import com.example.myappsecond.project.dialog.CommonDialogActivity;
import com.example.myappsecond.project.sqlLiteDatabase.DBActivity;
import com.example.myappsecond.utils.MeasureUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZZG on 2017/11/13.
 */

public class ProjectMainActivity extends BaseActivity {
    private LinearLayout search;
    private TextView search_bar;
    private TextView center;

    private LinearLayout parent;
    private Button popup;
    private Button sliding;
    private Button dbtest;
    private Button file;
    private Button menu;
    private Button drawable;
    private Button shape;
    private Button service;
    private  SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        setBarTitle("Mix");
        initView();
        initEvent();
    }

    private void initView() {
        search=findViewById(R.id.search);
        search_bar=findViewById(R.id.search_bar);
        center=findViewById(R.id.center);
        popup=findViewById(R.id.popup);
        sliding=findViewById(R.id.sliding);
        dbtest=findViewById(R.id.dbtest);
        file=findViewById(R.id.file);
        menu=findViewById(R.id.menu);
        drawable=findViewById(R.id.drawable);
        shape=findViewById(R.id.shape);
        service=findViewById(R.id.service);
    }

    private void initEvent() {

        MeasureUtil.setLeftScale(this,search,search_bar,R.mipmap.icon_title_bar_edit_search);
    //点击textView弹出Dialog
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialogActivity topDialog=
                        new CommonDialogActivity(ProjectMainActivity.this,R.layout.project_dialog_first);


                parent= (LinearLayout) topDialog.findViewById(R.id.parent);
                //设置drawleft的大小

                topDialog.getWindow().setBackgroundDrawable(null);
                //editText绑定键盘
//                son.setFocusable(true);
//                son.setFocusableInTouchMode(true);
//                son.requestFocus();
//                InputMethodManager inputManager = (InputMethodManager)son.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(son, 0);

            }
        });
//显示PopupWindow

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1=View.inflate(ProjectMainActivity.this,R.layout.popupwindow,null);
                PopupWindow pop=new PopupWindow(ProjectMainActivity.this);
                pop.setContentView(view1);
                pop.setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
                pop.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
                pop.setFocusable(true);
               // pop.setOutsideTouchable(false);
               // ObjectAnimator  animator=ObjectAnimator.ofFloat(view1,"translationY",0F,100F);
               // animator.setDuration(100).start();
                pop.showAsDropDown(popup);
            }
        });
//滑动冲突

        sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMainActivity.this,SlidingConflict.class));
            }
        });
//数据库相关

        dbtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMainActivity.this, DBActivity.class));
            }
        });
//File & Storage

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMainActivity.this, FileStorageTest.class));
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMainActivity.this,MenuActivity.class));
            }
        });
       drawable.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent8=new Intent(ProjectMainActivity.this, DrawableMainActivity.class);
               startActivity(intent8);
           }
       });
       shape.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent6=new Intent(ProjectMainActivity.this, AnimShapeActivity.class);
               startActivity(intent6);
           }
       });

       service.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent11=new Intent(ProjectMainActivity.this,TestCoorActivity.class);
               startActivity(intent11);
           }
       });
    }
    private void setClock(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
             final Date date=new Date();
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                   sliding.setText(sdf.format(date));
                 }
             });
            }
        },0,1000);
    }
    @Override
    protected void onResume() {
        super.onResume();
      setClock();
    }
}
