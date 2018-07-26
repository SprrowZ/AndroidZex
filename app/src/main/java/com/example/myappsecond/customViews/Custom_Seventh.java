package com.example.myappsecond.customViews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.utils.DensityUtil;

/**
 * Created by ZZG on 2017/12/1.
 */

public class Custom_Seventh extends BaseActivity {
    private LinearLayout root;
    private TextView del;
    int LastX=0;//全局声明，要不然每次都会更新为0
    //int LastY=0;
    int Max_Width=100;//目前单位是dp
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_deletenew);
        root=findViewById(R.id.root);
        del=findViewById(R.id.del);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int ScrollX=root.getScrollX();
        int startX= (int) event.getX();
        int newScrollX=LastX-startX+ScrollX;
        int maxLength= DensityUtil.dip2px(this,Max_Width);//dp转化为px
        //获取Y坐标
       /*  int ScrollY=root.getScrollY();
      int startY=(int)event.getY();
       int newScrollY=LastY-startY+ScrollY;*/
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                if (newScrollX<0){
                    newScrollX=0;
                }else if (newScrollX>maxLength){
                    newScrollX=maxLength;
                }
                root.scrollTo(newScrollX,0);
                break;
            case MotionEvent.ACTION_UP:
              //  root.scrollTo(newScrollX,0);
                break;
        }
        LastX=startX;
       // LastY=startY;
        return super.onTouchEvent(event);
    }

}
