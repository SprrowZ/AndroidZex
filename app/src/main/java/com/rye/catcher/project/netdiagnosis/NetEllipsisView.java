package com.rye.catcher.project.netdiagnosis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.rye.catcher.R;

/**
 * Create by rye
 * at 2020-08-25
 *
 * @description: 省略号，带动画
 */
public class NetEllipsisView extends View {

    private static final int DEFAULT_START_COLOR = 0xFFD8D8D8;
    private static final int DEFAULT_TRANSIT_COLOR = 0xFF52C22B;
    private static final int DEFAULT_ERROR_COLOR=0XFFFA5A57;



    private Paint mPaint;
    //当前状态：0，1，2，3 -> 全灰、一绿二灰、二绿一灰、全绿
    private int mCurrentFlag = 0;

    private int mStartColor;
    private int mTransitColor;
    private int mErrorColor;
    private float mDotRadius;

    private NetState mNetState;
    //刷新间隔，默认100ms
    private int mRefreshInterval = 300;

    public NetEllipsisView(Context context) {
        this(context, null);
    }

    public NetEllipsisView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NetEllipsisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetEllipsisView);
        mStartColor = typedArray.getColor(R.styleable.NetEllipsisView_startColor, DEFAULT_START_COLOR);
        mTransitColor = typedArray.getColor(R.styleable.NetEllipsisView_transitColor, DEFAULT_TRANSIT_COLOR);
        mErrorColor = typedArray.getColor(R.styleable.NetEllipsisView_errorColor, DEFAULT_ERROR_COLOR);
        //放到tv上需要修改
        mDotRadius = typedArray.getDimension(R.styleable.NetEllipsisView_dotRadius, 10);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mStartColor);

        mNetState = NetState.DEFAULT;

        typedArray.recycle();
    }

    //开始
    public void startLoop() {
        mNetState = NetState.LOOP;
        postInvalidate();
    }

    //结束
    public void endLoop(boolean isSucceed) {
        if (isSucceed) {
            mNetState = NetState.SUCCESS;
        } else {
            mNetState = NetState.ERROR;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = Math.round(4 * mDotRadius);
        int width = Math.round(12 * mDotRadius);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    private Paint getMatchingPaint(int currentPos) {
        switch (mNetState) {
            case DEFAULT:
                mPaint.setColor(mStartColor);
                break;
            case LOOP:
                setLoopColor(currentPos);
                break;
            case SUCCESS:
                mPaint.setColor(mTransitColor);
                break;
            case ERROR:
                mPaint.setColor(mErrorColor);
                break;
        }
        return mPaint;
    }

    private void setLoopColor(int currentPos) {
        switch (mCurrentFlag) {
            case 0: //三灰
                mPaint.setColor(mStartColor);
                break;
            case 1: //一绿二灰
                if (currentPos == 0) {
                    mPaint.setColor(mTransitColor);
                } else {
                    mPaint.setColor(mStartColor);
                }
                break;
            case 2: //二绿一灰
                if (currentPos == 2) {
                    mPaint.setColor(mStartColor);
                } else {
                    mPaint.setColor(mTransitColor);
                }
                break;
            case 3: //三绿
                mPaint.setColor(mTransitColor);
                break;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(2 * mDotRadius, 2 * mDotRadius, mDotRadius, getMatchingPaint(0));

        canvas.drawCircle(6 * mDotRadius, 2 * mDotRadius, mDotRadius, getMatchingPaint(1));

        canvas.drawCircle(10 * mDotRadius, 2 * mDotRadius, mDotRadius, getMatchingPaint(2));


        if (mNetState == NetState.LOOP) {
            postInvalidateDelayed(mRefreshInterval);
            mCurrentFlag++;
            if (mCurrentFlag > 3) {
                mCurrentFlag = 0;
            }
        }
    }

    public enum NetState {
        DEFAULT, LOOP, SUCCESS, ERROR
    }
}
