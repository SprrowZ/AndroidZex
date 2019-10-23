package com.rye.catcher.project.ctmviews;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created at 2018/11/1.
 *ViewDragHelper的简单使用
 * @author Zzg
 */
public class ViewDragHelperView extends FrameLayout {
    //...
    ViewDragHelper viewDragHelper;
    public ViewDragHelperView(@NonNull Context context) {
        this(context,null);

    }

    public ViewDragHelperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewDragHelperView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        viewDragHelper=ViewDragHelper.create(this,1.0f,callback);
    }
    private ViewDragHelper.Callback callback=new ViewDragHelper.Callback() {
        /**
         *此方法用于判断是否捕获当前child的触摸事件
         * @param child 用于捕获的子View
         * @param pointerId
         * @return true：处理这个触摸事件；false：不处理
         */
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            //return capturedView=view;
            return true;
        }

        /**
         * 在tryCaptureView的返回值为true的时候调用（用处不大）
          * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 以下两个位具体滑动方法，分别对应水平和垂直方向上的移动，要想子View移动，此方法必须重写实现！
         * @param child
         * @param left 表示即将移动到的位置!!正常返回这个left就可以，
         * @param dx 建议的移动的x轴的距离
         * @return   移动距离
         */
        //只在ViewGroup的内部移动，即：最小>=paddingleft，最大<=ViewGroup.getWidth()-paddingright-child.getWidth
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            //两个if主要是为了让ViewGroup里
            if (getPaddingLeft()>left){//控制左边
                return getPaddingLeft();
            }
            if (getWidth()-child.getWidth()<left){//控制右边
                return getWidth()-child.getWidth();
            }
            return left;
        }

        /**
         * 垂直方向上的移动
         * @param child
         * @param top  表示即将移动到的位置！！！！
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {

            if (getPaddingTop()>top){
                return getPaddingTop();
            }
            if (getHeight()-child.getHeight()>top){
                return getHeight()-child.getHeight();
            }
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
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**
         * 当释放View的时候调用，可以做一些松手后的操作
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        /**
         * 当拖拽状态改变时回调
         * @param state 新的状态
         */
        @Override
        public void onViewDragStateChanged(int state) {
            switch(state){
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
