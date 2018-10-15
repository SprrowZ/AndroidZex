package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ZZG on 2018/4/27.
 */

public class ViewGroupsEx extends LinearLayout {
    private int columns=3;
    private int height=0;
    private int  width=0;
    public ViewGroupsEx(Context context) {
        super(context);
    }

    public ViewGroupsEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupsEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //size和mode
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

     //   measureChildren(widthMeasureSpec,heightMeasureSpec);//测量子控件
        int  childCount=getChildCount();
        int childHeight=0;
        for (int i=0;i<childCount;i++){
            View child=getChildAt(i);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            width+=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            if (height<childHeight){
                height=childHeight;
            }

           setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:width,
                   heightMode==MeasureSpec.EXACTLY?heightSize:height);

        }




    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
     int count=getChildCount();
     int leftMargin=0;
     for (int i=0;i<count;i++){
     View child=getChildAt(i);
     MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
     //左上右下定义在for循环中
         int left=leftMargin+lp.leftMargin;
         int right=left+child.getMeasuredWidth()+lp.rightMargin;
         int top=lp.topMargin;
         int bottom=lp.topMargin+child.getMeasuredHeight();
        child.layout(left,top,right,bottom);
         leftMargin=right;
     }



    }



}
