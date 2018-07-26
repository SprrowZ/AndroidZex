package com.example.myappsecond.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by Zzg on 2018/2/4.
 */

public class setXfermode3 extends View {
    private Paint mPaint;
    private Bitmap dstBmp,srcBmp,revertBmp;
    public setXfermode3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE,null);//关闭硬件加速
        dstBmp= BitmapFactory.decodeResource(getResources(), R.drawable.touming);
        srcBmp= BitmapFactory.decodeResource(getResources(),R.drawable.bk);
        //利用matrix将图像做一个翻转
        Matrix matrix= new Matrix();
        matrix.setScale(1F,-1F);
        //生成倒影图
        revertBmp=Bitmap.createBitmap(srcBmp,0,0,srcBmp.getWidth(),dstBmp.getHeight(),matrix,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(srcBmp,0,0,mPaint);
        int LayerId=canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.translate(0,srcBmp.getHeight());//这个是什么鬼？
        canvas.drawBitmap(dstBmp,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBmp,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(LayerId);
    }
}
