package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ZZG on 2018/8/12.
 */
public class MainView extends RelativeLayout{
    private  CoordinatorMenuEx mCoordinatorMenu;

    public MainView(Context context) {
        this(context, (AttributeSet)null, 0);
    }

    public MainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setParent(CoordinatorMenuEx coordinatorMenu) {
        this.mCoordinatorMenu = coordinatorMenu;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.mCoordinatorMenu.isOpened() ? true : super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mCoordinatorMenu.isOpened()) {
            if (event.getAction() == 1) {
                this.mCoordinatorMenu.closeMenu();
            }

            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }
}
