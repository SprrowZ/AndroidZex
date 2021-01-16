package com.rye.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rye.base.interfaces.FragmentActions;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragmentActivity extends BaseActivity
        implements FragmentActions {
    protected Fragment mCurrentFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_fragment;
    }

    @Override
    public void beforeCreate() {
        super.beforeCreate();

    }
    private  FragmentTransaction getFragmentTransaction(){
       return  getSupportFragmentManager().beginTransaction();
    }

    @Override
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void addFragment(@NotNull Fragment fg) {
        if (fg == mCurrentFragment) return;
        mCurrentFragment = fg;
        getFragmentTransaction().add(R.id.base_container, fg)
                .commitAllowingStateLoss();
    }

    @Override
    public void removeFragment(@NotNull Fragment fg) {
        getFragmentTransaction().remove(fg)
                .commitAllowingStateLoss();
        mCurrentFragment = null;
    }

    @Override
    public void hideFragment() {
        if (mCurrentFragment == null) return;
        getFragmentTransaction().hide(mCurrentFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void showFragment() {
        if (mCurrentFragment == null) return;
        getFragmentTransaction().hide(mCurrentFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void replaceFragment(@NotNull Fragment fg) {
        replaceFragment(R.id.base_container, fg);
    }

    @Override
    public void replaceFragment(int containerId, Fragment fg) {
        if (fg==null){
            throw new IllegalArgumentException("Fragment 不能为空！");
        }
        if (mCurrentFragment == fg) return;
        getFragmentTransaction().replace(containerId, fg)
                .commitAllowingStateLoss();
        mCurrentFragment = fg;
    }
}
