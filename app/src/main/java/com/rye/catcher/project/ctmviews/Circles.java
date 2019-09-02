package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rye.base.utils.MeasureUtil;

/**
 * Created by ZZG on 2017/11/9.
 */

public class Circles extends View implements Runnable{
    private Paint mPaint;
    //圆的半径
    private int radiu;
    //
    private Context mContext;
    //
    private boolean flag=true;
    //View的坐标
    private int  X;
    private int  Y;
   // Activity mcontext;
    public Circles(Context context) {
        super(context);
    }

    public Circles(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initPaint();
    }
    public void setXY(int x,int y){
        X=x;
        Y=y;
    }

    private void initPaint() {
        //初始化，并加抗锯齿
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        //描边，还有Paint.Style.FILL_AND_STROKE,描边并填充；Paint.Style.FILL填充
        mPaint.setStyle(Paint.Style.STROKE);
        //浅灰色
        mPaint.setColor(Color.RED);
        //设置描边的粗细
        mPaint.setStrokeWidth(10);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制一个圆环,前两个参数是坐标，第三个是圆的半径，第四个是我们的画笔
        //canvas.drawCircle(MeasureUtil.getScreenSize(getContext())[0]/2,
                //MeasureUtil.getScreenSize(getContext())[1]/2,200,mPaint);
        canvas.drawCircle(X,Y,radiu,mPaint);
    }


    @Override
    public void run() {
      while (true){
          //加个随机位置
          X= (int) (Math.random()*MeasureUtil.getScreenWidth(getContext()));
          Y= (int) (Math.random()*MeasureUtil.getScreenHeight(getContext()));
          if(radiu<=300&&flag==true){
              radiu+=10;
              postInvalidate();
              if (radiu==300){
                  flag=false;
              }
          }else if(flag==false) {
              radiu-=10;
              if (radiu==0){
                  flag=true;
              }
              postInvalidate();
          }
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
    }
}
