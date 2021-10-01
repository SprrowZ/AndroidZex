package com.rye.opengl.about;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "Rye";
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private String[] colors = new String[]{"#ff4ab1", "#e84626", "#3e18e8"};

    private SurfaceRunnable mSurfaceRunnable;
    private boolean mRendering = false;

    public CustomSurfaceView(Context context) {
        this(context, null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //添加回调监听，这样surfaceCrated/surfaceChanged/surfaceDestroyed才能监听到
        mSurfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        //初始画笔
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(colors[0]));
        mPaint.setStrokeWidth(10f);

        startRenderThread();
    }

    private void startRenderThread() {
        mSurfaceRunnable = new SurfaceRunnable();
        new Thread(mSurfaceRunnable).start();
        mRendering = true;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "onSurfaceChanged");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");
        mRendering = false;
    }

    public void drawSth() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        if (null != canvas) {
            synchronized (mSurfaceHolder) {
                //清空画布
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                mPaint.setColor(Color.parseColor(colors[(int) (Math.random() * 3)]));
                //绘制一个变色方块
                canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
            }
        }
        if (null != mSurfaceHolder && null != canvas) {
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    class SurfaceRunnable implements Runnable {

        @Override
        public void run() {
            while (mRendering) {
                drawSth();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
