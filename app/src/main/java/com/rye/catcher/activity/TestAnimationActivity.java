package com.rye.catcher.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rye.catcher.BaseActivity;

import com.rye.catcher.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zzg on 2018/7/5.
 */
public class TestAnimationActivity extends BaseActivity {

    private static   int COUNT=5;

    ImageView big;
    ImageView small;
    ValueAnimator valueAnimator;
    Timer timer;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    rotateAnimator(true);
                    break;
                case 2:
                    rotateAnimator(false);
                    break;
            }
        }
    };
     //方向
    private static boolean ORIENTATION=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_test);
        initView();
    }


    private void initView(){
        big=findViewById(R.id.big);
        small=findViewById(R.id.small);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            int step=0;
            @Override
            public void run() {
                step++;
                if (step==5){
                   mHandler.sendEmptyMessage(1);
                }else if (step==6){
                    step=0;
                    COUNT--;
                    mHandler.sendEmptyMessage(2);
                    if (COUNT==0){
                        cancel();
                    }
                }
            }
        },3000,1000);
    }

    private void rotateAnimator(boolean orientation){
        final boolean[] hasDisapper = {false};
        valueAnimator=ValueAnimator.ofFloat(0F,180F).setDuration(300);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                 float value= (float) animation.getAnimatedValue();
                  Log.i("zzz","--VALUE:"+value);
                     if (Math.abs(value)>90&&!hasDisapper[0]){
                         hasDisapper[0]=true;
                         if (orientation) {//big--->small,大图消失，小图展现
                             big.setVisibility(View.GONE);
                             small.setVisibility(View.VISIBLE);
                         }else{//small--->big
                             small.setVisibility(View.GONE);
                             big.setVisibility(View.VISIBLE);
                         }
                     }
                     if (orientation){//big-->small
                         small.setRotationY(-180-value);//small转完相当于转了360度，大图只转了180度，
                         //所以需要将大图的状态置为360
                         big.setRotationY(-value);
                     }else{
                         big.setRotationY(-180-value);
                         small.setRotationY(-value);
                     }

            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //结束后，将旋转180度的，再转180度
                if (orientation){//big-->small
                    big.setRotationY(-180);
                }else{
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




    @Override
    protected void onDestroy() {
        super.onDestroy();
       valueAnimator.cancel();
    }
}
