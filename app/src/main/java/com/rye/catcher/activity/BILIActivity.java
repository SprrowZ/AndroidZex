package com.rye.catcher.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


import com.catcher.zzsdk.AutoCompletedEditTextStaticCursor;
import com.dawn.zgstep.others.GuideView;
import com.dawn.zgstep.others.MaskView;
import com.rye.base.ui.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.utils.ImageUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Zzg on 2018/7/5.
 */
public class BILIActivity extends BaseActivity {

    private static final String TAG = "BILIActivity";

    private static int COUNT = 5;

    public static final int REFERENCE_SQUARE_LENGTH = 600;

    @ColorInt
    static final int DEFAULT_BACKGROUND_COLOR = 0xFFFB7299;

    @ColorInt
    static final int DEFAULT_TEXT_COLOR = Color.WHITE;


    static final String DEFAULT_TEXT = "b站热门";


    @BindView(R.id.big)
    ImageView big;
    @BindView(R.id.small)
    ImageView small;
    @BindView(R.id.rotateView)
    FrameLayout rotateView;

    @BindView(R.id.centerContent)
    RelativeLayout centerContent;


    @BindView(R.id.showRotate)
    Button showRotate;
    @BindView(R.id.showBitmap)
    Button showBitmap;
    @BindView(R.id.imageContent)
    ImageView imageContent;
    @BindView(R.id.edit)
    EditText mEdit;
    @BindView(R.id.staticEditText)
    AutoCompletedEditTextStaticCursor mStaticEdit;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.maskView)
    MaskView mMaskView;

    @BindView(R.id.marquee)
    TextView marquee;


    ValueAnimator valueAnimator;
    Timer timer;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    rotateAnimator(true);
                    break;
                case 2:
                    rotateAnimator(false);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.bili_test;
    }

    @Override
    public void initEvent() {
        mStaticEdit.setCursorColor(getResources().getColor(R.color.red));
        mStaticEdit.setCursorStroke(7);
//     mEdit.requestFocus();
////     mEdit.setFocusable(false);
        marquee.setSelected(true);

        centerContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "hasFocus:" + hasFocus);
                if (hasFocus) {

                }
            }
        });
        centerContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "OnClick:");
            }
        });

        bottom.setOnClickListener(v -> Log.i(TAG, "bottom OnClick:"));

        bottom.setOnFocusChangeListener((v, hasFocus) -> Log.i(TAG, "bottom focusable:" + hasFocus));


        addMask();

    }

    private void addMask() {
        View maskView = LayoutInflater.from(this).inflate(R.layout.layout_mask, null);
        TextView leadText = maskView.findViewById(R.id.lead_txt);
        String lead = "按【上键】可进入个人中心";
        SpannableString ss = new SpannableString(lead);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#e97479"));
        ss.setSpan(span, lead.indexOf("【"), lead.indexOf("】"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        leadText.setText(ss);
        FrameLayout rootView = (FrameLayout) getWindow().getDecorView();
        rootView.addView(maskView);
        rootView.postDelayed(() -> {
            rootView.removeView(maskView);
        }, 5000);

        showGuideView();

    }

    @Override
    public void beforeCreate() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Slide());
//        }
    }


    /**
     * 设置EditText的hint字体的大小
     */
//    public static void  setEditTextHintSize(EditText editText,String hintText,int size){
//        SpannableString ss = new SpannableString(hintText);//定义hint的值
//        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#fb7299"));//设置字体大小 true表示单位是sp
//        AbsoluteSizeSpan ass=new AbsoluteSizeSpan(size,true);
//        ss.setSpan(ass, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(fcs,0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        editText.setHint(new SpannedString(ss));
//    }
    @OnClick({R.id.showRotate, R.id.showBitmap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showRotate:
                startTimer();
                break;
            case R.id.showBitmap:
                showBitmap();
                break;
        }
    }


    /**
     * 显示循环Demo
     */
    private void startTimer() {
        if (timer != null) timer.cancel();
        if (rotateView.getVisibility() == View.VISIBLE) {
            rotateView.setVisibility(View.GONE);
        } else {
            rotateView.setVisibility(View.VISIBLE);
            if (timer == null) timer = new Timer();
            timer.schedule(new TimerTask() {
                int step = 0;

                @Override
                public void run() {
                    step++;
                    if (step == 5) {
                        mHandler.sendEmptyMessage(1);
                    } else if (step == 6) {
                        step = 0;
                        COUNT--;
                        mHandler.sendEmptyMessage(2);
                        if (COUNT == 0) {
                            cancel();
                        }
                    }
                }
            }, 3000, 1000);
        }


    }

    private void rotateAnimator(boolean orientation) {
        final boolean[] hasDisapper = {false};
        valueAnimator = ValueAnimator.ofFloat(0F, 180F).setDuration(300);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                Log.i("zzz", "--VALUE:" + value);
                if (Math.abs(value) > 90 && !hasDisapper[0]) {
                    hasDisapper[0] = true;
                    if (orientation) {//big--->small,大图消失，小图展现
                        big.setVisibility(View.GONE);
                        small.setVisibility(View.VISIBLE);
                    } else {//small--->big
                        small.setVisibility(View.GONE);
                        big.setVisibility(View.VISIBLE);
                    }
                }
                if (orientation) {//big-->small
                    small.setRotationY(-180 - value);//small转完相当于转了360度，大图只转了180度，
                    //所以需要将大图的状态置为360
                    big.setRotationY(-value);
                } else {
                    big.setRotationY(-180 - value);
                    small.setRotationY(-value);
                }

            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //结束后，将旋转180度的，再转180度
                if (orientation) {//big-->small
                    big.setRotationY(-180);
                } else {
                    small.setRotationY(-180);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }
        });
        valueAnimator.start();


    }

    /**
     * 展示内容区域
     */
    private void showBitmap() {
        if (centerContent.getVisibility() == View.VISIBLE) {
            centerContent.setVisibility(View.GONE);
        } else {
            centerContent.setVisibility(View.VISIBLE);
            dealBitmap();
        }
    }

    private void dealBitmap() {
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.my3)
                .copy(Bitmap.Config.ARGB_8888, true);//复制一份

        Bitmap dealedBitmap = drawTag(originBitmap, DEFAULT_TEXT, DEFAULT_TEXT_COLOR, DEFAULT_BACKGROUND_COLOR);
        imageContent.setImageBitmap(dealedBitmap);

    }

    private Bitmap drawTag(Bitmap originBitmap, String text, int textColor, int backgroundColor) {

        Bitmap bitmap = ImageUtils.cropImage(originBitmap, REFERENCE_SQUARE_LENGTH, REFERENCE_SQUARE_LENGTH);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
        //绘制字体
        Paint textPaint = new Paint();
        textPaint.setTextSize(100);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);

        //绘制Tag背景
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        //底部绘制Tag
        RectF rectF = new RectF(0, bitmap.getHeight() - 100, bitmap.getWidth(), bitmap.getHeight());
        //   drawOneRoundRect(canvas, rectF, 30, 30, backgroundPaint);
        drawRect(canvas, rectF, backgroundPaint);
        drawTextCenter(text, canvas, rectF, textPaint);
        return bitmap;
    }


    /**
     * 在指定区域的正中绘制文字
     */
    private static void drawTextCenter(String text, Canvas canvas, @NonNull RectF rect, @NonNull Paint paint) {
        paint.setTextAlign(Paint.Align.CENTER);
        //计算baseline
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = rect.centerY() + distance;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setLetterSpacing(0.3f);
        }
        canvas.drawText(text, rect.centerX(), baseline, paint);
    }

    private static void drawRect(Canvas canvas, @NonNull RectF rectF, @NonNull Paint paint) {
        canvas.drawRect(rectF, paint);
    }

    /**
     * 绘制仅右下角是圆角的背景-----需要修改，现在全部底部
     */
    private static void drawOneRoundRect(Canvas canvas, @NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
        canvas.drawRoundRect(rect, rx, ry, paint);
        canvas.drawRect(rect.left, rect.top, rect.left + rx, rect.top + ry, paint); //覆盖左上角
        canvas.drawRect(rect.left, rect.bottom - ry, rect.left + rx, rect.bottom, paint); //覆盖左下角
        canvas.drawRect(rect.right - rx, rect.top, rect.right, rect.top + ry, paint); //覆盖右上角
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (valueAnimator != null) valueAnimator.cancel();
    }


    public void showGuideView() {
        View maskView = LayoutInflater.from(this).inflate(R.layout.mask_demo1, null);

        GuideView.Builder
                .newInstance(this)      // 必须调用
                .setTargetView(imageContent)    // 必须调用，设置需要Guide的View
                .setCustomGuideView(maskView)  // 必须调用，设置GuideView，可以使任意View的实例，比如ImageView 或者TextView
                // 设置GuideView 相对于TargetView的位置，有八种，不设置则默认在屏幕左上角,其余的可以显示在右上，右下等等
                .setShape(GuideView.MyShape.RECTANGULAR)   // 设置显示形状，支持圆形，椭圆，矩形三种样式，矩形可以是圆角矩形，
                .setBgColor(R.color.red)
                .build()                // 必须调用，Buider模式，返回GuideView实例
                .show();                // 必须调用，显示GuideView
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_finish_exit);
    }
}
