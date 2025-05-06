package com.dawn.zgstep.ui.ctm.views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dawn.zgstep.R;

/**
 * Created at 2018/11/1.
 * ViewDragHelper的简单使用
 * https://www.cnblogs.com/guanxinjing/p/17463237.html
 *
 * https://www.51cto.com/article/681440.html
 *
 * @author Zzg
 */
public class ViewDragHelperView extends FrameLayout {
    //...
    //ViewDragHelper 的滑动中共有三个方法可以调用，smoothSlideViewTo、settleCapturedViewAt、flingCapturedView
    // ，动画移动会回调 continueSettling(boolean) 方法
    private ViewDragHelper viewDragHelper;
    private View mContentView;

    private int mDefaultX = 0;
    private int mDefaultY = 0;

    public ViewDragHelperView(@NonNull Context context) {
        this(context, null);

    }

    public ViewDragHelperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }
    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**
         这个方法的作用是用于控制哪些View可以被捕获，默认值返回true, 则代表可以捕获ViewGroup中的所有View,
         也就是所有的View均可以被拖动，如果只想让指定的View被拖动，则可以将指定View的返回值设置成true
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            //return capturedView=viewRx;  //捕获指定view
            return true; //默认捕获所有view
        }

        /**
         * 在tryCaptureView的返回值为true的时候调用（用处不大）
         */
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 以下两个位具体滑动方法，分别对应水平和垂直方向上的移动，要想子View移动，此方法必须重写实现！
         * 可以不重写horizontal，限制其只在垂直方向移动；也可以不重写vertical，限制其只在水平方向移动
         */
        //只在ViewGroup的内部移动，即：最小>=paddingleft，最大<=ViewGroup.getWidth()-paddingright-child.getWidth
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        /**
         * 垂直方向上的移动
         * @param child
         * @param top  表示即将移动到的位置！！！！
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        /**
         * 用来控制垂直移动的边界范围，单位是像素【放置View拖出边界】
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return super.getViewVerticalDragRange(child);
        }

        /**
         * 当changedView的位置发生变化时调用，我们可以在这里面控制VIEW的显示位置和移动
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**
         * 当释放View的时候调用，可以做一些松手后的操作，比如：回弹，吸附
         * releasedChild – 捕获的子视图现在被释放
         * xvel – 指针离开屏幕时的X速度，单位为像素每秒。
         * yvel – 指针离开屏幕时的Y速度，单位为像素/秒。
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (mContentView == releasedChild) {//拖拽回弹到原位置
                viewDragHelper.settleCapturedViewAt(mDefaultX,mDefaultY); //必须重写computeScroll方法
                invalidate();
            } else {
                super.onViewReleased(releasedChild, xvel, yvel);
            }
        }

        /**
         * 当拖拽状态改变时回调
         * @param state 新的状态
         */
        @Override
        public void onViewDragStateChanged(int state) {
            switch (state) {
                case ViewDragHelper.STATE_DRAGGING://正在被拖动
                    break;
                case ViewDragHelper.STATE_IDLE://View没有被拖拽或者正在fling/snap?????????
                    break;
                case ViewDragHelper.STATE_SETTLING://fling完毕后被放置到一个位置
                    break;

            }
            super.onViewDragStateChanged(state);
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.i("RRye","computeScroll...");
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i("RRye","布局渲染完成，findViewId");
        mContentView = findViewById(R.id.test1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mDefaultX = mContentView.getLeft();
        mDefaultY = mContentView.getRight();
        Log.i("RRye","onLayout,left:"+mDefaultX+",right:"+mDefaultY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //拦截与否交给ViewDragHelper，如果有滑动冲突的话，可以重写
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将点击事件传递给ViewDragHelper
        viewDragHelper.processTouchEvent(event);
        return true;
    }
}
