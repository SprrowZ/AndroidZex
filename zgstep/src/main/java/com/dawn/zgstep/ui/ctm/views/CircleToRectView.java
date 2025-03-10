package com.dawn.zgstep.ui.ctm.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Create by  [Rye]
 * <p>
 * at 2024/6/13 17:25
 */
public class CircleToRectView extends View {
    private static final int DEFAULT_ANIMATION_DURATION = 500; // 动画持续时间，默认500毫秒
    private float radius; // 圆形半径
    private float cornerRadius; // 矩形圆角半径
    private int viewWidth, viewHeight; // View的宽高
    private Paint paint;
    private ValueAnimator animator;
    private boolean isCircle = true; // 初始为圆形

    public CircleToRectView(Context context) {
        this(context, null);
    }

    public CircleToRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleToRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE); // 设置颜色，可以根据需要更改
        animator = ValueAnimator.ofFloat(1f, 2f);
        animator.setDuration(DEFAULT_ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = viewWidth / 2 * (float) animation.getAnimatedValue();
                cornerRadius = radius / 2;
                invalidate(); // 触发重绘
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        setMeasuredDimension(viewWidth, (int) (isCircle ? viewWidth : viewWidth * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            RectF rectF = new RectF(0, 0, viewWidth, viewHeight * 2);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
    }


}
