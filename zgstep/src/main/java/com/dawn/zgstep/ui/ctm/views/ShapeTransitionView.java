package com.dawn.zgstep.ui.ctm.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Create by  [Rye]
 * <p>
 * at 2024/6/13 16:54
 */
public class ShapeTransitionView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint shapePaint;
    private float cornerRadius;
    private RectF rectF;

    public ShapeTransitionView(Context context) {
        this(context, null);
    }

    public ShapeTransitionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeTransitionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        shapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shapePaint.setColor(getResources().getColor(android.R.color.holo_blue_dark)); // 设置颜色
        cornerRadius = 50f; // 初始为圆形
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        // 更新RectF的大小和圆角半径
        rectF.set(0, 0, width, height);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, shapePaint);
    }

    public void setCornerRadius(float radius) {
        this.cornerRadius = radius;
        invalidate(); // 请求重新绘制
    }
}
