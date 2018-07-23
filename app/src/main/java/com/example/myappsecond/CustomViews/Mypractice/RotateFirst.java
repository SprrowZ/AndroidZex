package com.example.myappsecond.CustomViews.Mypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.amap.api.maps2d.model.Circle;
import com.example.myappsecond.Utils.MeasureUtil;

/**
 * Created by ZZG on 2018/5/3.
 */

public class RotateFirst extends View {
   private  Paint mPaint;
   private  int  centerX;
   private  int  centerY;
    public RotateFirst(Context context) {
        super(context);
    }

    public RotateFirst(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        //获取屏幕宽高
       centerX=(int)(MeasureUtil.getScreenWidth(context)/2);
       centerY=(int)(MeasureUtil.getScreenHeight(context)/3);
        init(context);
    }

    private void init(Context context) {
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#8600ff"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //矩形
        Rect rect1=new Rect(centerX-50,centerY-150,centerX+50,centerY+150);
        Rect rect2=new Rect(centerX-150,centerY-50,centerX+150,centerY+50);
        Rect rect3=new Rect(centerX-100,centerY-100,centerX+100,centerY+100);
        Path path=new Path();
        path.addCircle(centerX,centerY,80, Path.Direction.CCW);
        Region region1=new Region(rect1);
        Region region2=new Region(rect2);
        Region region3=new Region(rect3);
        region1.op(region2, Region.Op.XOR);
        region1.op(region3, Region.Op.DIFFERENCE);
        drawRegion(canvas,region1,mPaint);
        canvas.drawPath(path,mPaint);

    }

    private void drawRegion(Canvas canvas, Region region, Paint mPaint) {
        RegionIterator iterator=new RegionIterator(region);
        Rect rect=new Rect();
        while (iterator.next(rect)) {
            canvas.drawRect(rect,mPaint);
        }
    }


}
