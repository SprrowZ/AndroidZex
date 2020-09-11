package com.dawn.zgstep.ctm.views.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.dawn.zgstep.R;
import com.rye.base.utils.DensityUtil;

/**
 * Create by rye
 * at 2020-09-08
 *
 * @description:
 */
public class TestCanvasView extends View {
    private static final String TAG = "TestCanvasView";

    private Paint mPaint;

    public TestCanvasView(Context context) {
        this(context, null);
    }

    public TestCanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DensityUtil.px2dip(getContext(),10));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.yellow1));
        int count = canvas.saveLayer(0,0,canvas.getWidth(),canvas.getHeight(),mPaint);
        canvas.translate(canvas.getWidth()/2,0);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 4, mPaint);
        Log.i(TAG, String.valueOf(canvas.getSaveCount()));
        canvas.restoreToCount(count);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 4, mPaint);
    }
}
