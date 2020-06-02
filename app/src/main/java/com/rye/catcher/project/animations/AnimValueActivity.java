package com.rye.catcher.project.animations;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.base.utils.MeasureUtil;

/**
 * Created by Zzg on 2017/12/3.
 */

public class AnimValueActivity extends BaseActivity implements View.OnClickListener{
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    LinearLayout  content;
    private ValueAnimator repeatAnimator;
    private ObjectAnimator objectAnimator;

    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_valueanimator);
        initView();
    }
    private void initView() {

        btn1=findViewById(R.id.orr);
        btn2=findViewById(R.id.javaMore);
        btn3=findViewById(R.id.translate);
        btn4=findViewById(R.id.rotate);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        content=findViewById(R.id.appName);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        View view1= View.inflate(this,R.layout.animation_value_one,null);
        view1.setMinimumHeight(MeasureUtil.getScreenHeight(this));
        Button btnStart=view1.findViewById(R.id.btn);
        Button btnCancel = view1.findViewById(R.id.btn_cancel);
        TextView tv=view1.findViewById(R.id.tv);
        this.tv=tv;
        switch (view.getId()){
            case R.id.orr:
                content.addView(view1);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repeatAnimator = doRepeatAnim();
                        objectAnimator= doColorAnim();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repeatAnimator.cancel();
                        objectAnimator.cancel();
                    }
                });
                break;
            case R.id.javaMore:
                break;
            case R.id.translate:
                break;
            case R.id.rotate:
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
        }
    }
    private ValueAnimator doRepeatAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0,1000);//里面可以多个值，不一定俩值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                try {
                    tv.layout(tv.getLeft(),curValue,tv.getRight(),curValue+tv.getHeight());
                }catch (Exception e){
                   // Toast.makeText(ValueAnimators.this,"ddd",Toast.LENGTH_LONG).show();
                }finally {

                }

            }
        });
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //插值器
        animator.setInterpolator(new BounceInterpolator());
        //Evaluator
        animator.setEvaluator(new IntEvaluator());
        animator.setDuration(2000);
        animator.start();
        return animator;
    }
private ObjectAnimator doColorAnim(){
    ObjectAnimator ob=ObjectAnimator.ofInt(tv,"BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff,0xff0000ff);
    ob.setRepeatCount(ValueAnimator.INFINITE);
    ob.setRepeatMode(ValueAnimator.REVERSE);
    ob.setDuration(8000);
    ob.start();
    return ob;
}



}
