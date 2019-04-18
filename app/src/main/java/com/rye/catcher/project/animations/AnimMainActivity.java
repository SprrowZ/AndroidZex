package com.rye.catcher.project.animations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.rye.catcher.R;

/**
 * Created by Zzg on 2017/11/5.
 */

public class AnimMainActivity extends Activity implements View.OnClickListener{
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private ImageView iv;
    //Animation rotate1;
    Animation alpha1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.animation_main);
        initView();
    }

    private void initView() {
        iv=findViewById(R.id.iv);
        btn1=findViewById(R.id.orr);
        btn2=findViewById(R.id.javaMore);
        btn3=findViewById(R.id.someDemo);
        btn4=findViewById(R.id.retrofit);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        btn8=findViewById(R.id.btn8);
        btn9=findViewById(R.id.btn9);
        btn10=findViewById(R.id.btn10);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        iv.setVisibility(View.VISIBLE);
        switch (view.getId()){
            case R.id.orr:
                //布局文件加载
      Animation alpha= AnimationUtils.loadAnimation(this,R.anim.alpha);
           iv.startAnimation(alpha);
                break;
            case R.id.javaMore:
                Animation scale= AnimationUtils.loadAnimation(this,R.anim.scale);
                iv.startAnimation(scale);
                break;
            case R.id.someDemo:
               // Animation translate= AnimationUtils.loadAnimation(this,R.anim.translate);
                //iv.startAnimation(translate);
                TranslateAnimation translateAnimation=new TranslateAnimation(-200,100,-200,200);
                translateAnimation.setDuration(2000);
                translateAnimation.setRepeatCount(2);
                iv.startAnimation(translateAnimation);


                break;
            case R.id.retrofit:
                Animation rotate= AnimationUtils.loadAnimation(this,R.anim.rotate);
                iv.startAnimation(rotate);
                break;
            case R.id.btn5:
                alpha1= AnimationUtils.loadAnimation(this,R.anim.scale);
                iv.startAnimation(alpha1);
              final Animation  rotate1= AnimationUtils.loadAnimation(this,R.anim.rotate);
                alpha1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        iv.startAnimation(rotate1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                break;
            case R.id.btn6:
                Animation alpha2= AnimationUtils.loadAnimation(this,R.anim.alpha2);
                iv.startAnimation(alpha2);
                break;
            case R.id.btn7:
                AlphaAnimation myAlpha=new AlphaAnimation(0.1f,1.0f);
                myAlpha.setDuration(3000);
                myAlpha.setRepeatCount(10);
                myAlpha.setRepeatMode(Animation.RESTART);
                iv.startAnimation(myAlpha);
                break;
            case R.id.btn8:
                Intent intent=new Intent(this,AnimAttrActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rotate,R.anim.alpha);
                break;
            case R.id.btn9:
                Intent intent1=new Intent(this,AnimExActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.alpha,R.anim.rotate);
                break;
            case R.id.btn10:
            startActivity(new Intent(this,AnimValueActivity.class));
                //overridePendingTransition(R.anim.alpha2,0);
        }
    }
}
