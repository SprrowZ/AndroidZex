package com.example.myappsecond.project.ctmviews.dellist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by ZZG on 2018/8/14.
 */
public class ParentLinear extends LinearLayout {
    public  static  final  String TAG="ParentLinear";
    private int startX;
    private int startY;
    private int deltaX;
    private int deltaY;
    public ParentLinear(Context context) {
        this(context,null,0);
    }

    public ParentLinear(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ParentLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX= (int) ev.getX();
                startY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX= (int) (ev.getX()-startX);
                deltaY= (int) (ev.getY()-startY);
                if (Math.abs(deltaX)>Math.abs(deltaY)){//左右滑動
                    return false;
                }else {
                    return true;//上下滑动就拦截了
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent:down ");
                startY= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:


                scrollBy(0,-deltaY);
                Log.i(TAG, "onTouchEvent: move");
                return true;
        }
        return super.onTouchEvent(event);
    }
}
