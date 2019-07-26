package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rye.catcher.R;

/**
 * Created by Zzg on 2018/2/4.
 */

public class SetXfermode2 extends View {
    private Paint mPaint;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    public SetXfermode2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.soft3));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        dstBmp= BitmapFactory.decodeResource(getResources(),R.drawable.bk);
        srcBmp= BitmapFactory.decodeResource(getResources(),R.mipmap.my4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int LayerID=canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        //先画书架，作为目标图像
        canvas.drawBitmap(dstBmp,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        //在画光点
        canvas.drawBitmap(srcBmp,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(LayerID);
    }
}
