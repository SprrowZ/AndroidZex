package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/3/6.
 */

public class Shadows extends View {
    private Paint mPaint;
    private Bitmap mBeautyBmp;
    public Shadows(Context context) {
        super(context);
        init();
    }
    public Shadows(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        mPaint=new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTextSize(30);
        //阴影
        //mPaint.setShadowLayer(100,100, 0,Color.RED);//给外部提供个方法，也就是接口，修改radius，dx，dy
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        //内发光
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
        mBeautyBmp= BitmapFactory.decodeResource(getResources(), R.drawable.yanlingji);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      canvas.drawText("焰灵姬",100,100,mPaint);
      canvas.drawCircle(200,200,50,mPaint);
     canvas.drawBitmap(mBeautyBmp,null,new Rect(200,300,200+mBeautyBmp.getWidth(),300+mBeautyBmp.getHeight()),mPaint);
        super.onDraw(canvas);
    }
}
