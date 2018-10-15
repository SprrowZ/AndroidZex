package com.rye.catcher.project.ctmviews.Mypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ZZG on 2017/12/19.
 */

public class PracticeCanvas extends View {
    private Paint paint;
    public PracticeCanvas(Context context) {
        super(context);
    }

    public PracticeCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();//保存当前画布状态
        Rect rect=new Rect(80,80,300,300);
        canvas.drawRect(rect,paint);
        canvas.translate(100,100);//平移画布
        canvas.drawRect(rect,paint);
        canvas.rotate(45,300,300);//旋转画布
        canvas.save();//保存当前画布状态
        canvas.drawRect(rect,paint);
        canvas.scale(0.5f,0.8f);//缩放画布
        canvas.drawRect(rect,paint);
        canvas.skew(1.732f,0);//扭曲画布，角度为倾斜角度的tan值
        canvas.drawRect(rect,paint);

        //裁剪尺寸
//        Rect rect1=new Rect(20,20,700,700);
//        canvas.restore();//恢复
//        canvas.drawColor(getResources().getColor(R.color.blue));
//        canvas.clipRect(rect1);
//         canvas.drawColor(getResources().getColor(R.color.red));



    }
}
