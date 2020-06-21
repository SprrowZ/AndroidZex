package com.rye.catcher.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dawn.zgstep.demos.annotations.BindView;
import com.dawn.zgstep.demos.annotations.OnClick;
import com.dawn.zgstep.demos.annotations.zButterKnife;
import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.R;

/**
 * Created by ZZG on 2018/8/12.
 * 用于测试自定义注解--
 */
public class ReflectActivity extends BaseOldActivity {
    @BindView(R.id.btn1)
    Button btn1;

   private static  DataListener dataListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        zButterKnife.bind(this);
        initView();
    }

    public static void setListener(DataListener listener){
        dataListener=listener;
    }

    private void initView(){
        btn1.setText("Hello MyWorld!");
    }

    @OnClick({R.id.btn1})
    private void click(){
        Toast.makeText(this,"xxxxxx--xxxxxx",Toast.LENGTH_SHORT).show();
        dataListener.dataLoad("content");
    }

    interface DataListener{
        void dataLoad(String content);
    }

}
