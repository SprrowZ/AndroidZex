package com.example.myappsecond.project.ctmviews.Mypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/12/6.
 */

public class PracticeFirst extends View {
    Paint zPaint;
    Paint mPaint;
    Paint paint;
    Context mContext;

    public PracticeFirst(Context context) {
        super(context);
    }

    public PracticeFirst(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        //给三个画笔分别初始化
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(getResources().getColor(R.color.red));

        zPaint=new Paint();
        zPaint.setAntiAlias(true);
        zPaint.setStyle(Paint.Style.STROKE);
        zPaint.setStrokeWidth(5);
        zPaint.setColor(getResources().getColor(R.color.black));

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(getResources().getColor(R.color.blue3));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        各种图形
         */
        canvas.drawLine(10,20,1100,20,mPaint);//直线
        float [] pts={10,40,50,40,100,40,300,40};
        canvas.drawLines(pts,0,8,zPaint);//多条直线
        canvas.drawPoint(75,40,paint);//圆点
        float [] dots={60,40,90,40};
        canvas.drawPoints(dots,0,4,zPaint);//多个点
        RectF rectF=new RectF(10,80,100,180);
        canvas.drawRect(rectF,mPaint);//绘制矩形
        RectF rectF1=new RectF(110,80,200,180);
        canvas.drawRoundRect(rectF1,10,10,paint);//绘制圆角矩形
        canvas.drawCircle(100,300,100,zPaint);//绘制圆形
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(350,200,750,400,mPaint);
        }//绘制椭圆形
        //弧就不画了
        /*...........................................画路径和文字.................................................*/
          //一个闭合的直线路径
        Path path1=new Path();
        path1.moveTo(10,420);//开始点
        path1.lineTo(1050,420);
        path1.lineTo(1050,800);
        path1.lineTo(10,800);
        path1.close();
        canvas.drawPath(path1,zPaint);
        //矩形路径
        Path path2=new Path();
        path2.addRect(20,440,1180,780, Path.Direction.CCW);
        //圆角矩形
        Path path3=new Path();
        RectF rectF2=new RectF(60,480,1000,740);
        canvas.drawPath(path3,paint);
        //圆角大小可以定制，float [] radii,换一个构造函数即可
        path3.addRoundRect(rectF2,15,15,Path.Direction.CCW);
        canvas.drawPath(path3,mPaint);
        //绘制圆形路径
        Path path4=new Path();
        path4.addCircle(100,600,100, Path.Direction.CCW);
        canvas.drawPath(path4,zPaint);
        //绘制椭圆路径
        Path path5=new Path();
        RectF rectF3=new RectF(300,500,600,700);
        path5.addOval(rectF3, Path.Direction.CCW);
        canvas.drawPath(path5,mPaint);
        /*******************************文字***********************************************/
        //还是再开一个吧

        super.onDraw(canvas);
    }
}
