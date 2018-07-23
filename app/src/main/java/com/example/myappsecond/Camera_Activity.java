package com.example.myappsecond;


import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myappsecond.Fragments.Camera_Fragment;

/**
 * Created by ZZG on 2017/10/18.
 */

public class Camera_Activity extends BaseActivity {
    private TextView textView;
    private LinearLayout linear1;
    private LinearLayout linear2;
    private Fragment camera1;
 private LinearLayout window1;
    AlertDialog dialog1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        //先获取从跳转页面传来的值
        textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("info").toString());
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction transaction = fg.beginTransaction();
        camera1 = new Camera_Fragment();
        transaction.add(R.id.linear1, camera1);
        transaction.commit();
        showWindow();
        showWindow2();

    }

    private void showWindow2() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton("test",new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("positive");
                    }
                })
                .setNegativeButton("tttt", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("negative");
                    }
                }).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();

    }

    private void showWindow() {
        Button floatingButton = new Button(this);
        floatingButton.setText("button");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0,
                PixelFormat.TRANSPARENT
        );
        // flag 设置 Window 属性
        layoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        // type 设置 Window 类别（层级）
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.gravity = Gravity.CENTER;
        WindowManager windowManager = this.getWindowManager();
        windowManager.addView(floatingButton, layoutParams);


//        Window window = getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.height =1500;
//        window.setAttributes(params);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
//dialog 测试
        dialog1= new  AlertDialog.Builder(this).create();
        View view=this.getLayoutInflater().inflate(R.layout.top,null);
        TextView text=new TextView(this);
        text=view.findViewById(R.id.textView);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.setTitle("fk....");
        dialog1.setCancelable(false);
        dialog1.setView(view);
        dialog1.show();
        WindowManager.LayoutParams paramss=dialog1.getWindow().getAttributes();
        paramss.height= (int) (height*0.5);
        //paramss.x=100;
       // paramss.y= (int) (0.25*height);
       dialog1.getWindow().setGravity(Gravity.BOTTOM);
        dialog1.getWindow().setAttributes(paramss);
        //测量控件的高度




    }


}