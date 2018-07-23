package com.example.myappsecond.CustomViews.Mypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/12/14.
 */

public class PracticeRange extends View {
    private Paint mPaint;
    private Paint zPaint;
    public PracticeRange(Context context) {
        super(context);
    }

    public PracticeRange(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.red));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
      /*
        //1.先构造一个椭圆路径
        Path ovalPath=new Path();
        RectF rectF=new RectF(50,50,200,500);
        ovalPath.addOval(rectF, Path.Direction.CCW);
        //2.构建Region
        Region rgn=new Region();
        rgn.setPath(ovalPath,new Region(50,50,200,200));//setPath方法，取交集显示
        drawRegion(canvas,rgn,mPaint);
        */
        //先来两个矩形,我曹，这应该是个边界border
        Rect rect1=new Rect(100,100,400,200);
        Rect rect2=new Rect(200,0,300,300);
         canvas.drawRect(rect1,mPaint);
         canvas.drawRect(rect2,mPaint);
        //构造两个区域
        Region region=new Region(rect1);
        Region region2=new Region(rect2);
        region.op(region2, Region.Op.XOR);//还有其他参数
        //DIFFERENCE  INTERSECT UNION XOR REVERSE_DIFFERENCE REPLACE
        // 构造一个画笔，填充Region的操作结果
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.blue3));
        drawRegion(canvas,region,paint);

        super.onDraw(canvas);

    }
    /***************************构造区域通用方法**********************************/
    //若矩形足够小，就能组成任何形状，RegionIterator就实现了这个功能。
    private void drawRegion(Canvas canvas,Region rgn,Paint paint) {
        RegionIterator iter=new RegionIterator(rgn);
        Rect r=new Rect();
        while (iter.next(r)){
            canvas.drawRect(r,paint);
        }
    }
}
