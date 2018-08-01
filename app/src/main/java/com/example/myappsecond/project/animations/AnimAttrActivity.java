package com.example.myappsecond.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by Zzg on 2017/11/19.
 */

public class AnimAttrActivity extends BaseActivity {
    private Button btn1;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_attribute);
        btn1=findViewById(R.id.btn1);
        imageView=findViewById(R.id.imageView);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ObjectAnimator.ofFloat(imageView,"translationX",0F,200f).setDuration(1000).start();
                //ObjectAnimator.ofFloat(imageView,"translationY",0F,200f).setDuration(1000).start();
                //ObjectAnimator.ofFloat(imageView,"rotation",0F,720F).setDuration(1000).start();

                //PropertyValuesHolder p1=PropertyValuesHolder.ofFloat("rotation",0,720F);
                //PropertyValuesHolder p2=PropertyValuesHolder.ofFloat("translationX",0,200F);
                //PropertyValuesHolder p3=PropertyValuesHolder.ofFloat("translationY",0,200F);
                //ObjectAnimator.ofPropertyValuesHolder(imageView,p1,p2,p3).setDuration(1000).start();

                ObjectAnimator animator1=ObjectAnimator.ofFloat(imageView,"translationX",0F,200f);
                ObjectAnimator animator2= ObjectAnimator.ofFloat(imageView,"translationY",0F,200f);
                ObjectAnimator animator3= ObjectAnimator.ofFloat(imageView,"rotation",0F,720F);
                AnimatorSet set=new AnimatorSet();
                //set.playTogether(animator1,animator2,animator3);
                //set.play(animator1).with(animator2);
                //set.play(animator3).after(animator1);
                //set.play(animator1).before(animator3);
                set.playSequentially(animator1,animator2,animator3);
                set.setDuration(1000);
                set.start();




            }
        });
    }
}
