package com.example.myappsecond.project.customViews;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.utils.MeasureUtil;

/**
 * Created by ZZG on 2017/11/9.
 */

public class CtmFirstActivity extends BaseActivity {
    //获取屏幕宽高,在onCreate外面， activity还未获取到，所以在这里定义肯定错误
   // int height=MeasureUtil.getHeight(this);
   // int width=MeasureUtil.getWidth(this);

    private Circles customView;
    private  int X;
    private  int Y;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//获取屏幕宽高
         int height=MeasureUtil.getScreenHeight(this);
         int width=MeasureUtil.getScreenWidth(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_viewcircle);
        customView=findViewById(R.id.circle);
        //随机位置

        customView.setXY(5,5);
        new Thread(customView).start();

    }
}
