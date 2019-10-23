package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ZZG on 2018/3/29.
 */

public class Gradient extends View {
    private Paint mPaint;
    private RadialGradient gradient;
    private int mRadius=100;
    public Gradient(Context context) {
        super(context);
        init();
    }

    public Gradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Gradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint=new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //两色渐变
        //gradient=new RadialGradient(w/2,h/2,100,0Xffff0000,0Xff00ff00, Shader.TileMode.REPEAT);
        //多色渐变
        int [] colors={0Xffff0000,0Xff00ff00,0Xff0000ff,0Xff000000};
        float [] steps={0f,0.2f,0.6f,1f};
        gradient=new RadialGradient(w/2,h/2,100,colors,steps, Shader.TileMode.REPEAT);
        //如果是矩形了，可以变换一下最后的Shader的重复模式
        mPaint.setShader(gradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mPaint);
        //画一个矩形，用来测试重复模式的效果
        //canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
    }
}
