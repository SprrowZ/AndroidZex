package com.example.myappsecond.project.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by Zzg on 2018/2/4.
 */

public class SetXfermode extends View {
    Paint mPaint;
    Bitmap dstBmp;
    Bitmap srcBmp;
    private int width=400;
    private int height=400;

    public SetXfermode(Context context) {
        super(context);
    }

    public SetXfermode(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.accent));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        dstBmp=makeDst(width,height);
        srcBmp=makeSrc(width,height);

        //关闭硬件加速
        //setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }
private static  Bitmap makeDst(int w,int h){
    Bitmap bm=Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
    Canvas c=new Canvas(bm);
    Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
    p.setColor(0XFFFFCC44);
    c.drawOval(new RectF(0,0,w,h),p);
    return bm;
}
private static Bitmap makeSrc(int w,int h){
    Bitmap bm=Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
    Canvas c=new Canvas(bm);
    Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
    p.setColor(0xFF66AAFF);
    c.drawRect(new RectF(0,0,w,h),p);
    return bm;
}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int LayerID=canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstBmp,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBmp,width/2,height/2,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(LayerID);

    }
}
