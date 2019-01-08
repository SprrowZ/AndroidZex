package com.rye.catcher.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.orr.ORRFragment;
import com.rye.catcher.activity.fragment.orr.OkhttpFragment;
import com.rye.catcher.activity.fragment.orr.RetrofitFragment;
import com.rye.catcher.activity.fragment.orr.RxjavaFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ORRActivity extends AppCompatActivity {
 private Fragment currentFragment;
 private int  currentPos=-1;
 @BindView(R.id.container)
 FrameLayout container;
 @BindView(R.id.okhttp)
 Button okhttp;
 @BindView(R.id.retrofit)
 Button retrofit;
 @BindView(R.id.rxjava)
 Button rxjava;
 @BindView(R.id.orr)
 Button orr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orr);
        ButterKnife.bind(this);
        doSelect(0);
    }

    @OnClick({R.id.okhttp,R.id.retrofit,R.id.rxjava,R.id.orr})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.okhttp:
                doSelect(0);
                break;
            case R.id.retrofit:
                doSelect(1);
                break;
            case R.id.rxjava:
                doSelect(2);
                break;
            case R.id.orr:
                doSelect(3);
                break;
        }
    }

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
