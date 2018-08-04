package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by ZZG on 2017/10/31.
 */

public class MyButton extends RelativeLayout {
    //未完工
    private MyOnClickListener listener;
    public interface  MyOnClickListener{
        public void myClick();
    }
    public MyButton(Context context){
        super(context);
    }
    public void setMyOnClickListener(MyOnClickListener listener){
        this.listener=listener;
    }
    public void onClick(){
        listener.myClick();
    }
}
