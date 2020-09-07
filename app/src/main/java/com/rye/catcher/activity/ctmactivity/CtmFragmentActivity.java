package com.rye.catcher.activity.ctmactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rye.base.BaseActivity;
import com.rye.catcher.R;

import java.lang.reflect.Constructor;

/**
 * Created by ZZG on 2018/3/8.
 */

public class CtmFragmentActivity extends BaseActivity {
    private static final String TAG = "CtmFragmentActivity";
    public static final String FRAGMENT_NAME = "FRAGMENT_NAME";
    private String mFragmentName;
    private FragmentTransaction mFragmentTransaction;

    public static void start(Context context, String fragmentName) {
        Intent intent = new Intent();
        intent.setClass(context, CtmFragmentActivity.class);
        intent.putExtra(FRAGMENT_NAME,fragmentName);
        context.startActivity(intent);
        Log.i(TAG,"start..");
    }

    @Override
    public int getLayoutId() {
        return R.layout.ctm_eighth;
    }

    @Override
    public void initEvent() {
        mFragmentName = getIntent().getStringExtra(FRAGMENT_NAME);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        addFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mFragmentName = getIntent().getStringExtra(FRAGMENT_NAME);
        addFragment();
    }

    private void addFragment() {
        Fragment currentFragment = getFragment();
        Log.i(TAG,"addFragment--fragmentName:"+currentFragment.getClass().getSimpleName());
        if (mFragmentTransaction == null || currentFragment == null) return;
        mFragmentTransaction.replace(R.id.container, currentFragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    private Fragment getFragment() {
        try {
            Class<?> fragmentClass = Class.forName(mFragmentName);
            Constructor constructor = fragmentClass.getDeclaredConstructor();
            Fragment fragment = (Fragment) constructor.newInstance();
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}