package com.dawn.zgstep.others.views.xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class XferModeTwoView extends View {
    private Paint mPaint;
    public XferModeTwoView(Context context) {
        super(context,null);
    }

    public XferModeTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,-1);
    }

    public XferModeTwoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clearXfer(canvas);
    }
    private void normalDraw(Canvas canvas){
        canvas.drawARGB(255,139,197,186);
        int canvasWidth = canvas.getWidth();
        int r = canvasWidth/3;
        //黄色
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(r,r,r,mPaint);
        //蓝色
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(r,r,r*2.7f,r*2.7f,mPaint);
    }

    private void clearXfer(Canvas canvas){
        canvas.drawARGB(255,139,197,186);
        int canvasWidth = canvas.getWidth();
        int r = canvasWidth/3;
        //黄色
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(r,r,r,mPaint);
        //CLEAR --MODE  :会显示白色矩形 （Activity默认背景色是白色）
        // 画笔Paint已经设置Xfermode的值为PorterDuff.Mode.CLEAR，（规则就是清空区域内项目）
        // 此时Android首先是在内存中绘制了这么一个矩形，所绘制的图形中的像素称作源像素（source，简称src），
        //个人理解，这个源像素就是开始设置mode之前已经显示在屏幕上的像素
        // 所绘制的矩形在Canvas中对应位置的矩形内的像素称作目标像素（destination，简称dst）。

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //蓝色
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(r,r,r*2.7f,r*2.7f,mPaint);
        //去除XferMode
        mPaint.setXfermode(null);
    }

}
