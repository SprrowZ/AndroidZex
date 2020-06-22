package com.rye.base;

import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rye.base.interfaces.FragmentActions;
import com.rye.base.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragmentActivity extends BaseActivity
        implements FragmentActions {
    protected Fragment mCurrentFragment;
    protected FragmentTransaction mFragmentTransaction;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_fragment;
    }

    @Override
    public void initEvent() {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    @Override
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void addFragment(@NotNull Fragment fg) {
        if (fg == mCurrentFragment) return;
        mCurrentFragment = fg;
        mFragmentTransaction.add(R.id.container, fg)
                .commitAllowingStateLoss();
    }

    @Override
    public void removeFragment(@NotNull Fragment fg) {
        mFragmentTransaction.remove(fg)
                .commitAllowingStateLoss();
        mCurrentFragment = null;
    }

    @Override
    public void hideFragment() {
        if (mCurrentFragment == null) return;
        mFragmentTransaction.hide(mCurrentFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void showFragment() {
        if (mCurrentFragment == null) return;
        mFragmentTransaction.hide(mCurrentFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(@NotNull Fragment fg) {
        mFragmentTransaction.replace(R.id.container, fg)
                .commitAllowingStateLoss();
        mCurrentFragment = fg;
    }
}
