package com.example.myappsecond.project.ctmviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ZZG on 2017/11/10.
 */

public class Fonts extends View {
    private static final String TEXT = "死の風は、常に我が身を伴う";
    private Paint textPaint, linePaint;// 文本的画笔和中心线的画笔

    private int baseX, baseY;// Baseline绘制的XY坐标

    public Fonts(Context context) {
        this(context, null);
    }

    public Fonts(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(70);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.BLACK);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 计算Baseline绘制的起点X轴坐标
        baseX = (int) (canvas.getWidth() / 2 - textPaint.measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标
        baseY = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));

        canvas.drawText(TEXT, baseX, baseY, textPaint);

        // 为了便于理解我们在画布中心处绘制一条中线
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, linePaint);
    }
}
