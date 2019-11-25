package com.rye.catcher.activity;


import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.FrameLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.ZviewPagerAdapter;
import com.rye.catcher.activity.fragment.orr.ORRFragment;
import com.rye.catcher.activity.fragment.orr.OkhttpFragment;
import com.rye.catcher.activity.fragment.orr.RetrofitFragment;
import com.rye.catcher.activity.fragment.orr.RxjavaFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ORRActivity extends BaseActivity {
 private Fragment currentFragment;
 private int  currentPos=-1;
 @BindView(R.id.container)
 FrameLayout container;
// @BindView(R.id.okhttp)
// Button okhttp;
// @BindView(R.id.retrofit)
// Button retrofit;
// @BindView(R.id.rxjava)
// Button rxjava;
// @BindView(R.id.orr)
// Button orr;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    //下标及Fragments
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ZviewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orr);
        ButterKnife.bind(this);
       // doSelect(0);
        initDatas();
        initEvent();
    }

    private void initDatas() {
        tabIndicators=new ArrayList<>();
        tabFragments=new ArrayList<>();
        tabIndicators.add(getString(R.string.okhttp));
        tabIndicators.add(getString(R.string.retrofit));
        tabIndicators.add(getString(R.string.rxjava));
        tabIndicators.add(getString(R.string.orr));
        tabFragments.add(new OkhttpFragment());
        tabFragments.add(new RetrofitFragment());
        tabFragments.add(new RxjavaFragment());
        tabFragments.add(new ORRFragment());
    }

    private void initEvent() {
       //先不显示item
       //    toolbar.inflateMenu(R.menu.rx);
         //tabLayout
         adapter=new ZviewPagerAdapter(getSupportFragmentManager(),tabIndicators,tabFragments);
         viewPager.setAdapter(adapter);
         tabLayout.setupWithViewPager(viewPager);//将tabLayout和viewpager绑定


    }




 /*********************************底下方法暂且不用，使用MD***************************************/


//    @OnClick({R.id.okhttp,R.id.retrofit,R.id.rxjava,R.id.orr})
//    public void onViewClicked(View view){
//        switch (view.getId()){
//            case R.id.okhttp:
//                doSelect(0);
//                break;
//            case R.id.retrofit:
//                doSelect(1);
//                break;
//            case R.id.rxjava:
//                doSelect(2);
//                break;
//            case R.id.orr:
//                doSelect(3);
//                break;
//        }
//    }

    private void doSelect(int pos) {
        if (pos==currentPos){
            return ;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment!=null){
            transaction.hide(currentFragment);
        }
        currentPos=pos;
        Fragment fragment=manager.findFragmentByTag(getTag(pos));
        if (fragment!=null){
            currentFragment=fragment;
            transaction.show(currentFragment);
        }else{
            transaction.add(R.id.container,getFragment(pos),getTag(pos));
        }
        transaction.commitAllowingStateLoss();
    }

    private Fragment getFragment(int pos) {
        switch (pos){
            case 0:
                currentFragment=new OkhttpFragment();
                break;
            case 1:
                currentFragment=new RetrofitFragment();
                break;
            case 2:
                currentFragment=new RxjavaFragment();
                break;
            case 3:
                currentFragment=new ORRFragment();
                break;
        }
        return currentFragment;
    }

    private String getTag(int pos) {
        return "Zzg_"+pos;
    }
}
