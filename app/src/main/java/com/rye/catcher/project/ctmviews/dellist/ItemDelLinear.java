package com.rye.catcher.project.ctmviews.dellist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by ZZG on 2017/11/30.
 */

public class ItemDelLinear extends LinearLayout {
    private static final String TAG="ItemDelLinear";
    private int mlastX = 0;
    private final int MAX_WIDTH = 100;
    private Context mContext;
    private Scroller mScroller;
    //滑动监听
    private OnScrollListener mScrollListener;
    public ItemDelLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }
    public void disPatchTouchEvent(MotionEvent event){
        int maxLength = dipToPx(mContext, MAX_WIDTH);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                int scrollX = this.getScrollX();//距离父布局原点X轴的绝对位置(滑动前)，从左到右为负，反之~
                int newScrollX = scrollX + mlastX - x;//本次偏移量
                Log.i(TAG, "mLastX:"+mlastX);
                Log.i(TAG, "X:"+x);
                Log.i(TAG, "scrollX:"+scrollX);
                Log.i(TAG, "new ScrollX:"+newScrollX);
                if (newScrollX < 0) {
                    newScrollX = 0;
                } else if (newScrollX > maxLength) {
                    newScrollX = maxLength;
                }
               this.scrollTo(newScrollX, 0);
            }
            break;
            case MotionEvent.ACTION_UP: {
                int scrollX = this.getScrollX();
                int newScrollX = scrollX + mlastX - x;
                if (scrollX > maxLength / 2) {
                    newScrollX = maxLength;
                    //当完全展开时，通知出去
                    mScrollListener.OnScroll(this);
                } else {
                    newScrollX = 0;
                }
                mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
                invalidate();
            }
            break;
        }
        mlastX = x;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
        invalidate();

    }

    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public  interface OnScrollListener {
        void OnScroll(ItemDelLinear var1);
    }

    //设置监听器
    public void setOnScrollListener(ItemDelLinear.OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    //缓慢将ITEM滚动到指定位置
    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0);
        invalidate();
    }

}
