package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.rye.catcher.R;

/**
 * Created by ZZG on 2018/8/12.
 */
public class HorizontalProgress extends ProgressBar {
    private final String TAG=this.getClass().getSimpleName();
    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACH = 0XFFD3D6DA;//sp
    private static final int DEFAULT_HEIGHT_UNREACH = 2;//dp
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_REACH = 2;//dp
    private static final int DEFAULT_TEXT_OFFSET = 10;//Dp

    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);//圆形progressbar也需要用
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mUnReachColor = DEFAULT_COLOR_UNREACH;
    protected int mUnReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    protected int mReachColor = DEFAULT_COLOR_REACH;
    protected int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint = new Paint();
    private int mRealWidth;


    public HorizontalProgress(Context context) {
        this(context, null, 0);//骚操作，一个参数的构造方法调用两个参数的
    }

    public HorizontalProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        obtainStyledAttrs(attrs);

    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.HorizontalProgress);
        mTextSize = (int) typedArray.getDimension(R.styleable.HorizontalProgress_progress_text_size,
                -1);
        mTextColor = typedArray.getColor(R.styleable.HorizontalProgress_progress_text_color,
                -1);
        mReachColor = typedArray.getColor(R.styleable.HorizontalProgress_progress_reach_color,
                -1);
        mReachHeight = (int) typedArray.getDimension(R.styleable.HorizontalProgress_progress_reach_height,
                -1);
        mUnReachColor = typedArray.getColor(R.styleable.HorizontalProgress_progress_unreach_color,
                -1);
        mUnReachHeight = (int) typedArray.getDimension(R.styleable.HorizontalProgress_progress_unreach_height,
                -1);
        mTextOffset = (int) typedArray.getDimension(R.styleable.HorizontalProgress_progress_text_offset,
                -1);
        typedArray.recycle();//
        mPaint.setTextSize(mTextSize);//指定Text高度
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);//用户必须指定宽度值，不能wrapcontent
        int heightVal = measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthVal, heightVal);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {//自己测量
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom() +//这两个必须加，别忘了
                    Math.max(Math.max(mReachHeight, mUnReachColor), Math.abs(textHeight));
            if (mode == MeasureSpec.AT_MOST) {//最大也不能超过给的值
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ...");
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);//挪到正中间
        boolean noNeedUnReach=false;//不需要绘制右边
        //draw bar
        String text=getProgress()+"%";
        //measureText返回文字宽度信息，getTextBounds可以获取宽和高
        int textWidth=(int)mPaint.measureText(text);
        //获取当前进度
        float radio=getProgress()*1.0f/getMax();
        //得到unReach未达长度
        float progressX=radio*mRealWidth;//也是文本绘制开始的地方
        //已经到达了最右边
        if (progressX+textWidth>mRealWidth){
            progressX=mRealWidth-textWidth;
            //不需要再绘制右边
            noNeedUnReach=true;
        }

        float endX=progressX-mTextOffset/2;//reach结束的位置

        if (endX>0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            //已完成进度条位置
            canvas.drawLine(0,0,endX,0,mPaint);
        }
        //draw Text
        mPaint.setColor(mTextColor);
        int y=(int)(-(mPaint.descent()+mPaint.ascent())/2);//这个需要搞明白
        canvas.drawText(text,progressX,y,mPaint);
        //如果上面的没有到头，那么就需要绘制未完成进度条
        if (!noNeedUnReach){
            float start=progressX+mTextOffset/2+textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, spVal,
                getResources().getDisplayMetrics());
    }
}
