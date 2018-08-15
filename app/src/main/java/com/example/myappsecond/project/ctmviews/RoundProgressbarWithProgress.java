package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.example.myappsecond.R;
import com.example.myappsecond.utils.DensityUtil;

/**
 * Created by Zzg on 2018/8/15.
 */
public class RoundProgressbarWithProgress extends HorizontalProgressbarWithProgress {
    private int mRadius;
    private Context mContext;
    private int mMaxPaintWidth;
    public RoundProgressbarWithProgress(Context context) {
        this(context,null,0);
    }

    public RoundProgressbarWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public RoundProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //为了好看
        mReachHeight= (int) (mUnReachHeight*2.5f);
        mRadius=DensityUtil.dip2px(mContext,30);
        TypedArray typedArray=mContext.obtainStyledAttributes(attrs,R.styleable.RoundProgressbarWithProgress);
        mRadius= (int) typedArray.getDimension(R.styleable.RoundProgressbarWithProgress_radius,30);
        //回收
        typedArray.recycle();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);//??
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth=Math.max(mReachHeight,mUnReachHeight);
        //默认四个padding一致
        int expect=mRadius*2+mMaxPaintWidth+getPaddingLeft()+getPaddingRight();
        int height=resolveSize(expect,heightMeasureSpec);//这个方法很骚的，内部判定了三种模式
        int width=resolveSize(expect,widthMeasureSpec);
        int readWidth=Math.min(width,height);//有可能高度宽度不一样，需要取最小值画圆
        mRadius=(readWidth-getPaddingRight()-getPaddingLeft()-mMaxPaintWidth)/2;
        setMeasuredDimension(width,height);
    }
}
