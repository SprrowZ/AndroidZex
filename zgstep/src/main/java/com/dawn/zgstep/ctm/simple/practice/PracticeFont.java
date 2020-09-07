package com.dawn.zgstep.ctm.simple.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dawn.zgstep.R;

/**
 * Created by ZZG on 2017/12/7.
 */

public class PracticeFont extends View {
    private Paint paint;
    private Paint mPaint;
    private Paint zPaint;
    private Context mContext;
    public PracticeFont(Context context) {
        super(context);
    }

    public PracticeFont(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextAlign(Paint.Align.CENTER);//文字对齐方式
        paint.setFakeBoldText(false);//设置是否为粗体文字
        paint.setTextSize(100);//文字大小
        paint.setStrikeThruText(false);//是否带有删除线效果
        paint.setTextSkewX((float) -0.25);//设置文字倾斜角度，这个是正常的倾斜的

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(getResources().getColor(R.color.red));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setFakeBoldText(true);//设置是否为粗体文字
        mPaint.setTextSize(150);//文字大小
        mPaint.setStrikeThruText(false);//是否带有删除线效果
        mPaint.setTextSkewX((float) 0);//设置文字倾斜角度

        zPaint=new Paint();
        zPaint.setAntiAlias(true);
        zPaint.setStyle(Paint.Style.FILL);
        zPaint.setStrokeWidth(7);
        zPaint.setColor(getResources().getColor(R.color.black_deep));
        zPaint.setTextAlign(Paint.Align.RIGHT);
        zPaint.setFakeBoldText(false);//设置是否为粗体文字
        zPaint.setTextSize(180);//文字大小
        zPaint.setStrikeThruText(true);//是否带有删除线效果
        zPaint.setTextSkewX((float) -0.25);//设置文字倾斜角度
        //引入TypeFace字体样式
        /*
        String filename="宋体";
        Typeface typeface=Typeface.create(filename,Typeface.NORMAL);
        zPaint.setTypeface(typeface);
         */
        //引入外部文字样式，重头戏！
//         AssetManager mgr=mContext.getAssets();
//         Typeface typeface=Typeface.createFromAsset(mgr,"fonts/No3.ttf");
        Typeface typeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/No2.ttf");
         zPaint.setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawText("长路漫漫，唯剑作伴",450,100,paint);
        char [] text=new char[]{'断','剑','重','铸','之','日'};
        canvas.drawText(text,2,4,450,200,mPaint);
        canvas.drawText(text,0,4,800,400,zPaint);


        super.onDraw(canvas);
    }
}
