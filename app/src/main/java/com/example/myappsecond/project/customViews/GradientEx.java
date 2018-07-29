package com.example.myappsecond.project.customViews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

/**
 * Created by ZZG on 2018/4/2.
 */

public class GradientEx extends Button {
    /**
     *
     * 思路来一波：1.在手指按下的时候，绘制一个默认大小的圆
     * 2.在手指移动时，所绘制的默认圆的位置要跟随手指移动
     * 3.在手指放开时，圆逐渐变大
     * 4.在动画结束时，波纹效果消失（这个动画用ObjectAnimator应该就可以）
     *
     */
    private Paint  mPaint;
    private int mDx=-1,mDy=-1;//中心点
    private int mRadius=100;//半径
    private RadialGradient gradient;
    ValueAnimator animator;
    public GradientEx(Context context) {
        super(context);
        init();
    }

    public GradientEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mDx=(int)event.getX();
                    mDy=(int)event.getY();
                    //开始之前先看看上一个动画是否结束
                    if (animator!=null&&animator.isRunning()){
                        animator.cancel();//取消动画，并不是end()
                    }
                    postInvalidate();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    mDx=(int)event.getX();
                    mDy=(int)event.getY();
                break;
                case MotionEvent.ACTION_UP:
                  ChangeRadius();
                return  false;//还能传递给其他view
                default:
                    break;

        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    public void ChangeRadius(){
        animator=ValueAnimator.ofInt(0,getWidth());//这里需要做个判断，看是宽长还是高长
        animator.setDuration(8000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                mRadius=mRadius+value;
                postInvalidate();
            }
        });
        animator.start();
        mRadius=100;//如果不重置的话，下次点击mRadius太大，根本看不到喽，很重要的一步
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       if (mDx!=-1&&mDy!=-1){
           int[] colors={0X00F8F8FF,0XffFF00FF,0Xff00FFFF,0Xffff44ff};
           float[] stops={0, 0.2f,0.7f,1f};//对应的是位置，开始为0，结束为1
           gradient=new RadialGradient(mDx,mDy,mRadius,colors,stops, Shader.TileMode.REPEAT);
           mPaint.setShader(gradient);

           canvas.drawCircle(mDx,mDy,mRadius,mPaint);
       }

    }

}
