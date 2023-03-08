package com.dawn.zgstep.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.dawn.zgstep.R;

public class DemoLottieActivity extends AppCompatActivity {
   private LottieAnimationView mDemoLottie1;

   public static  void jump(Context context){
       Intent intent = new Intent(context,DemoLottieActivity.class);
       context.startActivity(intent);
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_lottie);
        loadLottie();
    }
    private void loadLottie(){
        mDemoLottie1 = findViewById(R.id.lottie_demo1);
        mDemoLottie1.setAnimationFromUrl("https://1688-live-auto-highlight.oss-cn-beijing.aliyuncs.com/zhenrenshichuan.json");
        mDemoLottie1.setRepeatCount(LottieDrawable.INFINITE);
        mDemoLottie1.playAnimation();
        Log.i("RRye","playAnimation");
        mDemoLottie1.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i("RRye","onAnimationEnd");
                mDemoLottie1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.i("RRye","onAnimationRepeat");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.i("RRye","onAnimationStart");
            }
        });

    }
}