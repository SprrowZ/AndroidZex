package com.example.myappsecond.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/3/7.
 */
/*
* 在此基础上，我们可以做一个点击某个点，圆圈逐渐扩大，且用ValueAnimator实现透明度逐渐清晰的效果。
* */
public class Telescopes extends View {
    private Paint mPaint;
    private Bitmap mBgBmp;
    private Bitmap mBitmap;
    private Bitmap topBitmap;
    private int mDx=-1, mDy=-1;
    int src,bg;
//    AttributeSet attrs;

    public Telescopes(Context context, AttributeSet attrs) throws Exception{
        super(context, attrs);
        init(context,attrs);
    }
    public Telescopes(Context context, AttributeSet attrs, int defStyle) throws Exception {
        super(context, attrs, defStyle);
//        this.attrs=attrs;
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) throws Exception {
        //setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint=new Paint();
//        mPaint.setStrokeWidth(3);
//        mPaint.setColor(Color.YELLOW);
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint.setTextSize(30);

       // topBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.scenery);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.Telescope);
        src=typedArray.getResourceId(R.styleable.Telescope_Telesrc,-1);
        if (src!=-1){
            mBitmap=BitmapFactory.decodeResource(getResources(),src);
        }else{
            throw new Exception ("src不能为空");
        }
        bg=typedArray.getResourceId(R.styleable.Telescope_TeleBg,-1);
        if (bg!=-1){
            topBitmap=BitmapFactory.decodeResource(getResources(),bg);
        }else{
//            topBitmap=Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            topBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.my6);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDx= (int) event.getX();
                mDy= (int) event.getY();
                postInvalidate();
                return true;//返回true，就可以在本View中处理MOVE,UP等以下事件，而不会传入到父容器中
            //返回true表明onTouch事件在本View中处理，而不会向上传递
            case MotionEvent.ACTION_MOVE:
                mDx= (int) event.getX();
                mDy= (int) event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDx=-1;
                mDy=-1;
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (mBgBmp==null){
//        mBgBmp=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
//
//        Canvas canvas1=new Canvas(mBgBmp);
//        canvas1.drawBitmap(mBitmap,null,new Rect(0,0,getWidth(),getHeight()),mPaint);
//        }
        canvas.drawBitmap(topBitmap,null,new Rect(0,0,getWidth(),getHeight()),mPaint);
        if (mDx!=-1&&mDy!=-1){
            mPaint.setShader(new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle((float) mDx,(float) mDy,250,mPaint);
        }
    }
}
