package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Canvas;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.rye.catcher.R;
import com.rye.catcher.utils.DensityUtil;
import com.zyao89.view.zloading.ZLoadingView;

import java.util.Stack;

/**
 * Created at 2019/1/11.
 *
 * @author Zzg
 * @function:
 */
public class zPullToRefreshView extends ViewGroup {
    private static final String TAG = "zPullToRefreshView";
    /**
     * 上下加载视图
     */
    //头部、底部
    private View zHeaderView;
    private View zBottomView;
    //头部处理
    private RelativeLayout pullDown;
    private RelativeLayout pullUp;
    private ZLoadingView loadingView;
    //此ViewGroup的高宽
    private int mHeight = -1;
    private int mWidth = -1;
    //主视图高度
    private int contentHeight;
    //下滑最大距离
    private float maxScrollHeight = DensityUtil.dip2px(getContext(), 150f);
    //有效滑动距离
    private int effectiveHeight;
    //下滑过程中上滑最大距离限制
    private int downUpHeight=-1;
    private Scroller mScroller;

    //监听接口
    private RefreshListener listener;
    //是否拦截
    private boolean intercept=false;
    public zPullToRefreshView(Context context) {
        this(context, null);
    }

    public zPullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public zPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //先实例化头部尾部布局
        zHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_custom_header, null, false);
        //  zBottomView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_custom_footer, null, false);
        findViews();
        //滑动
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        zHeaderView.setLayoutParams(params);
        // zBottomView.setLayoutParams(params);
        addView(zHeaderView);
        //   addView(zBottomView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            mHeight += child.getMeasuredHeight();
            if (child.getMeasuredWidth() > mWidth) {
                mWidth = child.getMeasuredWidth();
            }
        }
        int finalWidth = resolveSize(mWidth, widthMeasureSpec);
        int finalHeight = resolveSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child == zHeaderView) {

                child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            }
//            else if (child == zBottomView) {
//                Log.i(TAG, "onLayout: bottom...");
//                child.layout(0, contentHeight, child.getMeasuredWidth(), contentHeight + child.getMeasuredHeight());
//            }
            else {

                contentHeight += child.getMeasuredHeight();//计算内容视图的高度
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }
    }


    private int originY = getScrollY();
    private int lastY;
    private int y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //取消拦截
         intercept=false;
         Log.i(TAG, "onTouchEvent: --"+intercept);
        //获取每次手指的位置
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                Log.i(TAG, "onTouchEvent: down....");
                break;
            case MotionEvent.ACTION_MOVE:

                if (lastY-y>0&&getScrollY()>=0){//如果下滑之后上滑，且上滑距离大于下滑距离，就不花懂了
                    break;
                }
                int moveY = getScrollY() + lastY - y;//要滑动到的点
                if (originY-moveY> zHeaderView.getMeasuredHeight()) {//下滑距离超过有效距离
                    pullDown.setVisibility(GONE);
                    pullUp.setVisibility(VISIBLE);
                    loadingView.setVisibility(GONE);
                }
                if (originY - getScrollY() >= maxScrollHeight) {//对最大滑动距离做限制
                    moveY = (int) (originY - maxScrollHeight);
                }

                Log.i(TAG, "onTouchEvent: "+getScrollY());
                scrollTo(0, moveY);
                break;
            case MotionEvent.ACTION_UP:
                if (originY - getScrollY() > 0) {//下拉
                    if (originY - getScrollY() < zHeaderView.getMeasuredHeight()) {//小于有效距离
                        //scrollTo(0, originY);
                        mScroller.startScroll(0,getScrollY(),0,originY-getScrollY());
                        pullDown.setVisibility(VISIBLE);
                        pullUp.setVisibility(GONE);
                        loadingView.setVisibility(GONE);
                    } else {//超过有效距离
                      //  scrollTo(0, originY - zHeaderView.getMeasuredHeight());
                        mScroller.startScroll(0,getScrollY(),0,-getScrollY()-zHeaderView.getMeasuredHeight(),600);
                        pullDown.setVisibility(GONE);
                        pullUp.setVisibility(GONE);
                        loadingView.setVisibility(VISIBLE);
                        listener.refresh();
                    }
                }
                break;
        }
        lastY = y;
        return true;
    }

    private int zlastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y= (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                zlastY=y;

                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent: zLastY:"+zlastY+"y:"+y);
                 if (zlastY-y<0){//下滑操作
                     //
                     pullDown.setVisibility(VISIBLE);
                     pullUp.setVisibility(GONE);
                     loadingView.setVisibility(GONE);
                     //
                     View recycleView=getChildAt(0);
                     if (recycleView instanceof RecyclerView){
                         ((RecyclerView) recycleView).addOnScrollListener(new RecyclerView.OnScrollListener() {
                             @Override
                             public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                 super.onScrollStateChanged(recyclerView, newState);
                                 //-1代表顶部，返回true表示可以继续上滑，反之；1为底部，返回true，表示可以继续下滑，反之；
                                boolean canScroll= recyclerView.canScrollVertically(-1);
                                 Log.i(TAG, "onScrollStateChanged: can Scroll?--"+canScroll);
                                if (!canScroll){
                                    intercept=true;
                                }else{
                                    intercept=false;
                                }
                             }
                         });
                     }
                 }

                break;
            case MotionEvent.ACTION_UP://oneTime
                Log.i(TAG, "onInterceptTouchEvent: up...");
                intercept=false;
                break;
        }
        Log.i(TAG, "onInterceptTouchEvent: "+intercept);
        lastY=y;
        return intercept;
    }
    private void findViews() {
        pullDown = zHeaderView.findViewById(R.id.pullDown);
        pullUp = zHeaderView.findViewById(R.id.pullUp);
        loadingView = zHeaderView.findViewById(R.id.loadingView);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
        postInvalidate();
    }

    /**
     * 刷新操作
     */
    public interface RefreshListener {
        void refresh();
    }

    public void setRefreshListener(RefreshListener listener) {
        this.listener=listener;
    }
    public void dataCompleated(){
        mScroller.startScroll(0,getScrollY(),0,-getScrollY());
        pullDown.setVisibility(GONE);
        pullUp.setVisibility(GONE);
        loadingView.setVisibility(GONE);
    }

//    private int mLastMoveY;
//
//    private int originY = getScrollY();
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//                mLastMoveY = y;
//                //
//                pullDown.setVisibility(VISIBLE);
//                pullUp.setVisibility(GONE);
//                loadingView.setVisibility(GONE);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int dy = mLastMoveY - y;
//                //已滑动距离
//                int deltaY = originY - getScrollY();
//                if (getScrollY() + dy > 0) {//不向上滑动
//                    dy = 0;
//                }
//                if (deltaY > maxScrollHeight&&dy<0) {//超过最大距离
//                    dy = 0;
//                }
//                if (deltaY > zHeaderView.getMeasuredHeight()) {//大于有效距离
//                    pullDown.setVisibility(GONE);
//                    pullUp.setVisibility(VISIBLE);
//                    loadingView.setVisibility(GONE);
//                }
//                scrollBy(0, dy);
//                break;
//            case MotionEvent.ACTION_UP:
//                pullDown.setVisibility(GONE);
//                pullUp.setVisibility(GONE);
//                loadingView.setVisibility(VISIBLE);
//                if (originY - getScrollY()<zHeaderView.getMeasuredHeight()) {//小于有效距离
//
//                     scrollBy(0, originY - getScrollY());
//                }else{
//
//                    scrollBy(0, originY - getScrollY() - zHeaderView.getMeasuredHeight());
//                }
//                break;
//        }
//        mLastMoveY = y;
//        //不要忘了返回为true消耗此次事件
//        return true;
//    }


}
