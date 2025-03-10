package com.dawn.zgstep.ui.ctm.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * Create by  [Rye]
 * <p>
 * at 2025/1/7 15:58
 */
public class DragFrameLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;
    private float mLastX;
    private float mLastY;
    private long lastDownTime;
    public DragFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (mDragHelper == null) {
            mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                lastDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isLongPressed(mLastX,mLastY,event.getX(),event.getY(),lastDownTime,System.currentTimeMillis(),100)) {
                    Log.i("RRye","长按事件。。。。。");
                    mDragHelper.processTouchEvent(event);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isLongPressed(float lastX, float lastY, float thisX, float thisY,
                                  long lastDownTime, long thisEventTime, long longPressTime) {

        float offsetX = Math.abs(thisX - lastX);

        float offsetY = Math.abs(thisY - lastY);

        long intervalTime = thisEventTime -

                lastDownTime;

        if (offsetX <= 10

                && offsetY <= 10

                && intervalTime >=

                longPressTime) {

            return true;

        }

        return false;

    }

    private static class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }
    }
}
