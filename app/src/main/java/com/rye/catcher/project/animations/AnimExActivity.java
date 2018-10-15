package com.rye.catcher.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZG on 2017/11/20.
 */

public class AnimExActivity extends BaseActivity implements View.OnClickListener{
    private int [] res={R.id.imageView_a,R.id.imageView_b,R.id.imageView_c,R.id.imageView_d,
            R.id.imageView_e,R.id.imageView_f,R.id.imageView_g,R.id.imageView_h};

    private int [] resource={R.id.menu,R.id.item1,R.id.item2,R.id.item3,R.id.item4,R.id.item5};

    private List<ImageView> imageViewList=new ArrayList<ImageView>();
    private List<Button> buttonList=new ArrayList<Button>();

    private boolean flag=true;
    private boolean flags=true;
    //设置圆的半径
    private int radius=500;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.animation_example);
       for (int i=0;i<res.length;i++){
           ImageView imageView=findViewById(res[i]);
           imageView.setOnClickListener(AnimExActivity.this);
         imageViewList.add(imageView);
       }
       for (int i=0;i<resource.length;i++){
           Button button=findViewById(resource[i]);
           button.setOnClickListener(this);
           buttonList.add(button);
       }




    }


    @Override
    public void onClick(View v) {
   switch (v.getId())
   {
       case R.id.imageView_a:
           if (flag==true){
               startAnim();
           }else{
               endAnim();
           }
           break;
       case R.id.menu:
           if (flags==true){
               show();
           }else{
               disappear();
           }
           break;
       case R.id.item1:
           Toast.makeText(this,"SS..First",Toast.LENGTH_LONG).show();
   }
    }

    private void show() {
        for (int i = 1; i < resource.length; i++) {
            float hor= (float) (radius*Math.sin(Math.toRadians((i-1))*22));
            float ver= (float) (radius*Math.cos(Math.toRadians((i-1)*22)));
           /*
            ObjectAnimator ob=ObjectAnimator.ofInt(buttonList.get(i),"BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff,0xff0000ff);
             if (flags==true){
                 ob.setRepeatCount(ValueAnimator.INFINITE);
             }*/
            ObjectAnimator horizontal = ObjectAnimator.ofFloat(buttonList.get(i), "translationX", 0, -hor);
            ObjectAnimator vertical=ObjectAnimator.ofFloat(buttonList.get(i),"translationY",0,-ver);
            AnimatorSet animatorSet=new AnimatorSet();
            animatorSet.play(horizontal).with(vertical);
            animatorSet.setDuration(50*i);
            animatorSet.start();
        }
        flags=false;
    }
    private void disappear(){
         for (int i=1;i<resource.length;i++){
             float hor= (float) (radius*Math.sin(Math.toRadians((i-1))*22));
             float ver= (float) (radius*Math.cos(Math.toRadians((i-1)*22)));
             ObjectAnimator horizontal = ObjectAnimator.ofFloat(buttonList.get(i), "translationX", -hor, 0);
             ObjectAnimator vertical=ObjectAnimator.ofFloat(buttonList.get(i),"translationY",-ver, 0);
             AnimatorSet animatorSet=new AnimatorSet();
             animatorSet.play(horizontal).with(vertical);
             animatorSet.setDuration(100*i);
             animatorSet.start();
         }
         flags=true;
    }




    private void endAnim() {
        for (int i=1;i<res.length;i++){
            ObjectAnimator animator=ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",i*200,0F);
            animator.setDuration(500);
            //模仿小球自由落体
            animator.setInterpolator(new BounceInterpolator());
            //延时开始，依次收回
            animator.setStartDelay(i*300);
            animator.start();
        }
        flag=true;
    }

    private void startAnim() {
        for (int i=1;i<res.length;i++){
            ObjectAnimator animator=ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",0F,i*200);
            animator.setDuration(500);
            //模仿小球自由落体
            animator.setInterpolator(new BounceInterpolator());
            //延时开始，依次弹出
            animator.setStartDelay(i*300);
            animator.start();
        }
       flag=false;
    }





























}
