package com.example.myappsecond.customViews.Mypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZZG on 2017/12/19.
 */

public class PracticeFiger extends View {
    private Paint mPaint;
    private Path path;
    float mPrex;
    float mPrey;
    public PracticeFiger(Context context) {
        super(context);
    }

    public PracticeFiger(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        path=new Path();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                path.moveTo(event.getX(),event.getY());
                mPrex=event.getX();
                mPrey=event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
                //path.lineTo(event.getX(),event.getY());
            {
                float  endX=(mPrex+event.getX())/2;
                float  endY=(mPrey+event.getY())/2;
                path.quadTo(mPrex,mPrey,endX,endY);
                mPrex=event.getX();
                mPrey=event.getY();
                invalidate();//最好用postInvalidate();
            }

                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(path,mPaint);

        super.onDraw(canvas);

    }
    public void reset(){//给外部留的接口
        path.reset();
        invalidate();
    }
}
