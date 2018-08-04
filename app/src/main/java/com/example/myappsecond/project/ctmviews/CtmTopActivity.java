package com.example.myappsecond.project.ctmviews;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/10/30.
 */

public class CtmTopActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_first);
        Topbar topbar=findViewById(R.id.topbar);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(CtmTopActivity.this,"LeftClick...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(CtmTopActivity.this,"RightClick...",Toast.LENGTH_SHORT).show();
            }
        });
        //使用自定义的Button
//        MyButton myButton=new MyButton(this);
//        myButton.setMyOnClickListener(new MyButton.MyOnClickListener() {
//            @Override
//            public void myClick() {
//                Toast.makeText(Custom_Top.this,"fuck..",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
