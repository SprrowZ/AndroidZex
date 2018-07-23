package com.example.myappsecond.Project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.Project.Dialog.CommonDialogActivity;
import com.example.myappsecond.P_SQLiteDatabase.DBActivity;
import com.example.myappsecond.Project.Files.FileStorageTest;
import com.example.myappsecond.R;
import com.example.myappsecond.Utils.MeasureUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZZG on 2017/11/13.
 */

public class ProjectMain extends BaseActivity {
    private LinearLayout search;
    private TextView search_bar;
    private TextView center;
    private EditText son;
    private LinearLayout parent;
    private Button popup;
    private Button sliding;
    private Button dbtest;
    private Button file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        drawablesize();
    }

    private void drawablesize() {
        search=findViewById(R.id.search);
        search_bar=findViewById(R.id.search_bar);
        center=findViewById(R.id.center);
        MeasureUtil.setLeftScale(this,search,search_bar,R.drawable.icon_title_bar_edit_search);
  //点击textView弹出Dialog
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialogActivity topDialog=
                        new CommonDialogActivity(ProjectMain.this,R.layout.project_dialog_first);

                son= (EditText) topDialog.findViewById(R.id.son);
                parent= (LinearLayout) topDialog.findViewById(R.id.parent);
                //设置drawleft的大小
                MeasureUtil.setLeftScale(ProjectMain.this,parent,son,R.drawable.icon_title_bar_edit_search);
                topDialog.getWindow().setBackgroundDrawable(null);
                //editText绑定键盘
//                son.setFocusable(true);
//                son.setFocusableInTouchMode(true);
//                son.requestFocus();
//                InputMethodManager inputManager = (InputMethodManager)son.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(son, 0);

                //editText绑定键盘,可封装
                Timer timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    public void run()
                    {
                        InputMethodManager inputManager = (InputMethodManager)son.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(son, 0);
                    }
                }, 100);
            }
        });
//显示PopupWindow
        popup=findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1=View.inflate(ProjectMain.this,R.layout.popupwindow,null);
                PopupWindow pop=new PopupWindow(ProjectMain.this);
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
        sliding=findViewById(R.id.sliding);
        Date date=new Date();
        DateFormat sdf= new  SimpleDateFormat("HH:mm:sss");
        sliding.setText(sdf.format(date));

        sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMain.this,SlidingConflict.class));
            }
        });
//数据库相关
        dbtest=findViewById(R.id.dbtest);
        dbtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMain.this, DBActivity.class));
            }
        });
//File & Storage
        file=findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectMain.this, FileStorageTest.class));
            }
        });




    }
}
