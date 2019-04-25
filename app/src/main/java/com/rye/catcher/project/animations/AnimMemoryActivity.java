package com.rye.catcher.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.ImageView;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.utils.ExtraUtil.CommonPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZG on 2017/11/16.
 */

public class AnimMemoryActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<ImageView> viewList=new ArrayList<ImageView>();
    private CommonPagerAdapter myadapter;
    //头部填一张，尾部填一张，实现无线循环
    int [] ResId={R.mipmap.my6,R.mipmap.my1,R.mipmap.my2,R.mipmap.my3,
            R.mipmap.my4,R.mipmap.my5,
            R.mipmap.my6,R.mipmap.my1};
    private  int maxIndex=ResId.length-1;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_memory);
            viewPager=findViewById(R.id.viewPager);
            for (int i=0;i<ResId.length;i++){
                ImageView view=getView(ResId[i]);
                viewList.add(view);
            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                   // Toast.makeText(Memory.this,"ddd",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPageSelected(int i) {
               if(viewPager.getCurrentItem()==0){
                  viewPager.setCurrentItem(ResId.length-2,false);
                   //实现动画
                   ObjectAnimator animator=ObjectAnimator.ofFloat(viewPager,"rotation",0,360F)
                           .setDuration(1000);
                   animator.start();
               }else if (viewPager.getCurrentItem()==ResId.length-1){
                   viewPager.setCurrentItem(1,false);
                   //实现动画
                   ObjectAnimator animator=ObjectAnimator.ofFloat(viewPager,"rotationX",0,360F)
                           .setDuration(1000);
                   animator.start();
               }else if (viewPager.getCurrentItem()==1){
                   //属性动画集合一
                   PropertyValuesHolder p1=PropertyValuesHolder.ofFloat("alpha",0F,1F);
                   PropertyValuesHolder p2=PropertyValuesHolder.ofFloat("rotationX",0,720F);
                   PropertyValuesHolder p3=PropertyValuesHolder.ofFloat("scaleX",0f,2f,1f);
                   ObjectAnimator.ofPropertyValuesHolder(viewPager,p1,p2,p3).setDuration(1000).start();
               }else if (viewPager.getCurrentItem()==2){
                   //属性动画集合二
                   ObjectAnimator p1=ObjectAnimator.ofFloat(viewPager,"alpha",0F,1F);
                   ObjectAnimator p2=ObjectAnimator.ofFloat(viewPager,"rotationX",0,360F);
                   ObjectAnimator p3=ObjectAnimator.ofFloat(viewPager,"scaleX",0f,2f,1f);
                   AnimatorSet set=new AnimatorSet();
                   set.playSequentially(p1,p2,p3);
                   set.setDuration(2000);
                   set.start();
               }


                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (viewPager.getCurrentItem()==5){
                        //Toast.makeText(Memory.this,"ddd",Toast.LENGTH_SHORT).show();
                    }
                }
            });

//            viewList.add(View.inflate(this,R.layout.viewpager_first,null));
//            viewList.add(View.inflate(this,R.layout.viewpager_second,null));
//            viewList.add(View.inflate(this,R.layout.viewpager_third,null));
//            viewList.add(View.inflate(this,R.layout.viewpager_fourth,null));
            myadapter=new CommonPagerAdapter(viewList,ResId);
            viewPager.setAdapter(myadapter);
    }
  public ImageView getView(int ID){
      ImageView view=new  ImageView(this);
      view.setBackgroundColor(getResources().getColor(R.color.black));
      view.setScaleType(ImageView.ScaleType.FIT_XY);
      return view;
  }


}
