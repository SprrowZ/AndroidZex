package com.rye.catcher.project.ctmviews.dellist;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by ZZG on 2018/8/15.
 */
public class TMoveLinear extends LinearLayout {
    Context mContext;
    private int lastX=0;
    private int lastY=0;
    private int x;
    private int y;
    private int maxLength=100;
    private Scroller scroller;
    public TMoveLinear(Context context) {
        this(context,null,0);
    }

    public TMoveLinear(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TMoveLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        scroller=new Scroller(context,new LinearInterpolator(context,null));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                 x= (int) event.getX();
                  y= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:{//还必须得括起来
                int scrollX=this.getScrollX();
                int newScrollX=scrollX+lastX-x;//每次移动的距离
                if (newScrollX<0){
                    newScrollX=0;
                }else{

                }
                this.scrollTo(newScrollX,0);
            }
                break;
            case MotionEvent.ACTION_UP:{
                int scrollX=getScrollX();
                int newScrollX=scrollX+lastX-x;
                if (newScrollX>maxLength/2){//长度大于一半就弹性滑动了
                     newScrollX=maxLength;
                }else if (newScrollX<0){
                    newScrollX=0;
                }
                scroller.startScroll(scrollX,0,newScrollX-scrollX,0);//实际上并没有
                invalidate();
            }
                break;
        }
        lastX=x;
        lastY=y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            this.scrollTo(scroller.getCurrX(),scroller.getCurrY());//这个才是真正的移动
        }
        postInvalidate();
    }

    private void smoothScrollTo(){

    }
}
