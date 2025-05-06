package com.dawn.zgstep.ui.ctm.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.dawn.zgstep.R;

/**
 * Create by  [Rye]
 * <p>
 * at 2025/4/17 16:42
 * //整个类的操作记录：
 * 1.继承linear，implements NestedScrollingParent2
 * 2.声明顶部所需要的属性（在此之前实现xml文件）
 * 3.在FinishInflate后找到对应的字View
 * 4.重写 #onStartNestedScroll、 #onNestedScrollAccepted 、#onStopNestedScroll #onNestedPreFling要返回false，true的话，子View就无法处理Fling事件了
 * 5.确定父控件可以滑动的范围：topView - 顶部栏高度；在onSizeChanged里计算出来
 * 6.嵌套滑动逻辑处理，判断什么时候是父容器滑，什么时候子viWE(这里是Recyclerview)滑动，在#onNestedPreScroll中处理
 *  ①向上滑动时：getScrollY()是正值，当其小于mParentScrollDistance时，父容器处理滑动；
 *  ②向下滑动时：子view不能向下滑动（Recyclerview处于顶部，）时，交给父容器处理
 * 7.处理onNestedScroll：根据嵌套滑动机制，当父控件预处理后，子控件会再消耗剩余的距离，如果子控件消耗后，还有剩余的距离。那么就又会传递给父控件。也就是会走onNestedScroll方法
 * 8.重写scrollTo？
 * 9.重写onMeasure?
 *
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent2 {

    private NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    private View mTopView;//占位用，在嵌套父容器中预留出父容器可滑动的空间，漏出嵌套父容器外，与之同级的真正的topView

    private View mNavView; //tabLayout导航栏
    private ViewPager mViewPager;

    private float mParentScrollDistance = 0f;

    private ScrollChangeListener mScrollChangeListener;


    public StickyNavLayout(Context context) {
        this(context, null);
    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.top_placeholder);
        mNavView = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        getScrollY();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mParentScrollDistance = mTopView.getMeasuredHeight() - getResources().getDimension(R.dimen.normal_title_height);
        //这里的顶部栏高度最好动态获取
    }

    /**
     * 此容器接受垂直方向的嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     *startNestedScroll返回true时才会调用
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mNestedScrollingParentHelper.onStopNestedScroll(target,type);
    }



    //子view处理完后，还有剩余距离的话，就会走到此方法中
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (dyUnconsumed<0 && type == ViewCompat.TYPE_NON_TOUCH) {//向下滑动，且为fling //TODO 为什么要加这个逻辑？？还有向上的呢？
            scrollBy(0,dyUnconsumed);
        }
    }

    //需要注意的是在onNestedPreScroll方法中，我们并没有区分是手势滑动还是fling，也就是区分type为TYPE_TOUCH(0)还是TYPE_NON_TOUCH(1)。因为不管是手势滑动还是fling。在Demo效果中父控件都需要处理。所以我们并没有进行判断。
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //子View欲向上滑动
       boolean scrollToTop = dy > 0 && getScrollY() < mParentScrollDistance;
       //子View欲向下滑动
       boolean scrollToBottom = dy < 0 && !target.canScrollVertically(-1);//子view位于容器顶部，不能向上滑动
       if (scrollToTop || scrollToBottom) {//检测到父容器拦截事件
           scrollBy(0,dy);//父容器滑动
           consumed[1] = dy;//consumed[0] 水平消耗的距离 ,consumed[1] 垂直消耗的距离；    //如果父容器里处理了dx或者dy，要设置consumed值，通知子view处理剩下的值
       }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if (mScrollChangeListener != null) { //TODO 验证是否要过滤
            mScrollChangeListener.onScroll(y / mParentScrollDistance);
        }
    }

    public interface ScrollChangeListener {
        /**
         * 移动监听
         *
         * @param moveRatio 移动比例
         */
        void onScroll(float moveRatio);
    }
    public void setScrollChangeListener(ScrollChangeListener scrollChangeListener) {
        mScrollChangeListener = scrollChangeListener;
    }
}
