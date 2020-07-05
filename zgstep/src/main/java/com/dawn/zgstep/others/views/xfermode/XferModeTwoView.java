package com.dawn.zgstep.others.views.xfermode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class XferModeTwoView extends View {
    private Paint mPaint;
    private Canvas mCanvas;
    private int canvasWidth;
    private int canvasHeight;
    int radius;
    private int yellow;
    private int blue;

    public XferModeTwoView(Context context) {
        super(context, null);
    }

    public XferModeTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, -1);
    }

    public XferModeTwoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        radius = canvasWidth / 3;
        yellow = 0xFFFFCC44;
        blue = 0xFF66AAFF;
        mCanvas = canvas;
        clearXferByLayer();
    }

    private void normalDraw() {
        mCanvas.drawARGB(255, 139, 197, 186);
        int canvasWidth = mCanvas.getWidth();
        int r = canvasWidth / 3;
        //黄色
        mPaint.setColor(yellow);
        mCanvas.drawCircle(r, r, r, mPaint);
        //蓝色
        mPaint.setColor(blue);
        mCanvas.drawRect(r, r, r * 2.7f, r * 2.7f, mPaint);
    }

    private void clearXfer() {
        mCanvas.drawARGB(255, 139, 197, 186);
        int canvasWidth = mCanvas.getWidth();
        int r = canvasWidth / 3;
        //黄色
        mPaint.setColor(0xFFFFCC44);
        mCanvas.drawCircle(r, r, r, mPaint);
        //CLEAR --MODE  :会显示白色矩形 （Activity默认背景色是白色）
        // 画笔Paint已经设置Xfermode的值为PorterDuff.Mode.CLEAR，（规则就是清空区域内项目）
        //先画的叫目标图，后画的叫源图

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //蓝色
        mPaint.setColor(0xFF66AAFF);
        mCanvas.drawRect(r, r, r * 2.7f, r * 2.7f, mPaint);
        //去除XferMode
        mPaint.setXfermode(null);
    }

    @SuppressLint("NewApi")
    public void clearXferByLayer() {
        mCanvas.drawARGB(255, 139, 197, 186);
        //黄色
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, mPaint);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        //先画的叫目标图，后画的叫源图

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //蓝色
        mPaint.setColor(blue);
        mCanvas.drawRect(radius, radius, radius * 2.7f, radius * 2.7f, mPaint);
        //去除XferMode
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }

    @SuppressLint("NewApi")
    public void src() {
        mCanvas.drawARGB(255, 139, 197, 186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, mPaint);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mCanvas.drawRect(radius, radius, 2.7f * radius, 2.7f * radius, mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }

    @SuppressLint("NewApi")
    public void srcIn() {
        mCanvas.drawARGB(255, 139, 197, 186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, mPaint);
        mCanvas.drawCircle(radius, radius, radius, mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawRect(radius, radius, 2.7f * radius, 2.7f * radius, mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void srcOut(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void srcAtop(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void srcOver(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mCanvas.drawRect(radius,radius,2.7f*canvasWidth,2.7f*canvasHeight,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }

    @SuppressLint("NewApi")
    public void dst(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void dstIn(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void dstOut(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void dstAtop(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void dstOver(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void xor(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void darken(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void lighten(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mPaint.setColor(yellow);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void multiply(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }
    @SuppressLint("NewApi")
    public void screen(){
        mCanvas.drawARGB(255,139,197,186);
        mPaint.setColor(yellow);
        int layerId = mCanvas.saveLayer(0,0,canvasWidth,canvasHeight,mPaint);
        mCanvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setColor(blue);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        mCanvas.drawRect(radius,radius,2.7f*radius,2.7f*radius,mPaint);
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
        postInvalidate();
    }




}
