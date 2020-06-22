package com.dawn.zgstep.others.views.xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class XferViewOne extends View {
    private Paint mPaint;
    public XferViewOne(Context context) {
        super(context,null);
    }

    public XferViewOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,-1);
    }

    public XferViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mPaint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255,139,197,186);
        int canvasWidth = canvas.getWidth();
        int r = canvasWidth/3;
        //绘制黄色的圆形
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(r,r,r,mPaint);
        //绘制蓝色矩形
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(r,r,r*2.7f,r*2.7f,mPaint);
    }
}
