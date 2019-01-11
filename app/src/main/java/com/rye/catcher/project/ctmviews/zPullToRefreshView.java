package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rye.catcher.R;
import com.rye.catcher.utils.MeasureUtil;

/**
 * Created at 2019/1/11.
 *
 * @author Zzg
 * @function:
 */
public class zPullToRefreshView extends ViewGroup {
    /**
     * 上下加载视图
     */
    private View zHeaderView;
    private View zBottomView;
    private View mainView;
    private int mHeight;
    private int mWidth;
    private int contentHeight= MeasureUtil.getScreenHeight(getContext());
    public zPullToRefreshView(Context context) {
        this(context,null);
    }

    public zPullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public zPullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        zHeaderView= LayoutInflater.from(getContext()).inflate(R.layout.top,null,false);
        zBottomView= LayoutInflater.from(getContext()).inflate(R.layout.bottom,null,false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
       zHeaderView.setLayoutParams(params);
       zBottomView.setLayoutParams(params);
       addView(zHeaderView);
       addView(zBottomView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count=getChildCount();

        for (int i=0;i<count;i++){
            mHeight+=getChildAt(i).getHeight();
        }
        int finalHeight=resolveSize(mHeight,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,finalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      int count=getChildCount();
       for (int i=0;i<count;i++){
           View view=getChildAt(i);
           if (view==zHeaderView){
               layout(0,-view.getHeight(),view.getWidth(),0);
           }else if (view==zBottomView){
               layout(0,contentHeight,view.getWidth(),contentHeight+view.getHeight());
           }else{
              layout(0,0,MeasureUtil.getScreenWidth(getContext()),
                      MeasureUtil.getScreenHeight(getContext()));
           }
       }
    }
}
