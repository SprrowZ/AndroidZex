package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Canvas;
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
import com.rye.catcher.utils.MeasureUtil;

/**
 * Created at 2019/1/11.
 *
 * @author Zzg
 * @function:
 */
public class zPullToRefreshView extends ViewGroup {
    private static final String TAG="zPullToRefreshView";
    /**
     * 上下加载视图
     */
    //头部、底部
    private View zHeaderView;
    private View zBottomView;
    private View mainView;
    //此ViewGroup的高宽
    private int mHeight=-1;
    private int mWidth=-1;
    //主视图高度
    private int contentHeight;

    private Scroller mScroller;
    public zPullToRefreshView(Context context) {
        this(context,null);
    }

    public zPullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public zPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //先实例化头部尾部布局
        zHeaderView= LayoutInflater.from(getContext()).inflate(R.layout.top,null,false);
        zBottomView= LayoutInflater.from(getContext()).inflate(R.layout.bottom,null,false);
        //滑动
        mScroller=new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
       zHeaderView.setLayoutParams(params);
       zBottomView.setLayoutParams(params);
       addView(zHeaderView);
       addView(zBottomView);
       contentHeight=MeasureUtil.getScreenHeight(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count=getChildCount();

        for (int i=0;i<count;i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            mHeight+=child.getMeasuredHeight();
            if (child.getMeasuredWidth()>mWidth){
                mWidth=child.getMeasuredWidth();
            }
        }
        int finalWidth=resolveSize(mWidth,widthMeasureSpec);
        int finalHeight=resolveSize(mHeight,heightMeasureSpec);
        setMeasuredDimension(finalWidth,finalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      int count=getChildCount();
       for (int i=0;i<count;i++){
           View child=getChildAt(i);
           if (child==zHeaderView){
               child.layout(0,-100,child.getMeasuredWidth(),0);
           }else if (child==zBottomView){
               child.layout(0,contentHeight,child.getWidth(),contentHeight+100);
           }else{
              child.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
           }
       }
    }

    private int mLastMoveY;
    private int originY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        Log.i(TAG, "onTouchEvent: yyyy"+y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = y;
                originY=y;
                Log.i(TAG, "onTouchEvent:cccc ");
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastMoveY - y;
                if (Math.abs(originY-y)>200){
                    break;
                }
                Log.i(TAG, "onTouchEvent: "+dy);
                scrollBy(0, dy);
                break;
            case MotionEvent.ACTION_UP:
                //方向要搞清
                int wholeDistance=y-originY;
                if (Math.abs(wholeDistance)>200){
                    if (wholeDistance>0){//向上滑动
                        scrollBy(0,200);
                    }else{
                        scrollBy(0,-200);
                    }
                     break;
                }
                scrollBy(0,wholeDistance);
                break;
        }

        mLastMoveY = y;
        Log.i(TAG, "onTouchEvent: xxxxxxx");
        //不要忘了返回为true消耗此次事件
        return true;
    }



}
