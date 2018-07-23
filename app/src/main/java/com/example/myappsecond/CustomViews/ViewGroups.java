package com.example.myappsecond.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ZZG on 2018/3/21.
 */

public class ViewGroups extends LinearLayout {
    public ViewGroups(Context context) {
        super(context);
    }

    public ViewGroups(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroups(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
        int measureHeightMode=MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureWidthMode=MeasureSpec.getMode(widthMeasureSpec);
        //测量子控件,height和width是父控件的宽高
        int height=0;
        int width=0;
        int count=getChildCount();
        for (int i=0;i<count;i++){
            View child=getChildAt(i);
            //加底下这一行就支持了margin属性,一个一个测量
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childHeight=child.getMeasuredHeight()+lp.bottomMargin+lp.topMargin;
            int childWidth=child.getMeasuredWidth()+lp.rightMargin+lp.leftMargin;
           height+=childHeight;
           width=Math.max(childWidth,width);
        }
        setMeasuredDimension((measureWidthMode==MeasureSpec.EXACTLY?measureWidth:width),
                (measureHeightMode==MeasureSpec.EXACTLY?measureHeight:height));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int top=0;
        int count=getChildCount();
        for (int i=0;i<count;i++){
            View child=getChildAt(i);
            //别顾首不顾尾
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            int childWidth=child.getMeasuredWidth()+lp.rightMargin+lp.leftMargin;
            child.layout(lp.leftMargin,top,childWidth,top+childHeight);
            top+=childHeight;
        }
    }
}
