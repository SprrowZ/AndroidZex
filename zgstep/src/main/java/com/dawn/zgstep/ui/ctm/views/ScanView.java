package com.dawn.zgstep.ui.ctm.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dawn.zgstep.R;

/**
 * Create by rye
 * at 2020-09-07
 *
 * @description:
 */
public class ScanView extends FrameLayout {
    private float mRadius;

    public ScanView(@NonNull Context context) {
        this(context, null);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    private void initLayout(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ScanView);
        int bitmapId = typedArray.getResourceId(R.styleable.ScanView_scan_src, -1);
        mRadius = typedArray.getDimension(R.styleable.ScanView_scan_center_radius, 20.0f);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) mRadius * 2, (int) mRadius * 2);
        params.gravity = Gravity.CENTER;

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_alpha_scan);
        Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_alpha_scan);
        Animation anim3 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_alpha_scan);
        Animation anim4 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_alpha_scan);


        ImageView img1 = buildImgView();
        ImageView img2 = buildImgView();
        ImageView img3 = buildImgView();
        ImageView img4 = buildImgView();

        addView(img1);
        addView(img2);
        addView(img3);
        addView(img4);


        CircleRectView circleRectView = new CircleRectView(getContext());
        circleRectView.setLayoutParams(params);
        circleRectView.setDrawableId(bitmapId);

        addView(circleRectView);


        img1.startAnimation(animation);

        anim2.setStartOffset(600);
        img2.startAnimation(anim2);

        anim3.setStartOffset(1200);
        img3.startAnimation(anim3);

        anim4.setStartOffset(1800);
        img4.startAnimation(anim4);


        typedArray.recycle();
    }

    private ImageView buildImgView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) mRadius * 2, (int) mRadius * 2);
        params.gravity = Gravity.CENTER;
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.scan_item_bg);
        imageView.setLayoutParams(params);
        return imageView;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (int) mRadius * 6;
        int height = (int) mRadius * 6;
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

//    public Animation getAnimation() {
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2,
//                Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT, 0.5f);
//        scaleAnimation.setRepeatCount(Animation.INFINITE);
//
//        AlphaAnimation alphaAnimation = new AlphaAnimation( 0.3f, 1);
//        alphaAnimation.setRepeatCount(Animation.INFINITE);
//
//        AnimationSet set = new AnimationSet(true);
//        set.addAnimation(scaleAnimation);
//        set.addAnimation(alphaAnimation);
//        set.setDuration(3000);
//        set.setFillAfter(false);
//        return set;
//    }


}
