package com.rye.catcher.activity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rye.base.rxmvp.RxBaseActivity;
import com.rye.base.ui.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.DeviceInfoFragment;
import com.rye.catcher.activity.fragment.LMFragment;
import com.rye.catcher.activity.fragment.SettingsFragment;



public class LeftDetailActivityRx extends BaseActivity {


    private Fragment currentFragment;
    private int currentPos = -1;


    public static final String TYPE="type";

    public static final int DEVICE_INFO=0;

    private int type=-1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_left_detail;
    }

    @Override
    public void initEvent() {
        //判断是哪个fragment
        type=getIntent().getIntExtra(TYPE,DEVICE_INFO);
        selectItem(type);
    }

    private void selectItem(int pos) {
        //点击的正是当前正在显示的，直接返回
        if (currentPos == pos) return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null) {
            //隐藏当前正在显示的fragment
            transaction.hide(currentFragment);
        }
        currentPos = pos;
        Fragment fragment = manager.findFragmentByTag(getTag(pos));
        //通过findFragmentByTag判断是否已存在目标fragment，若存在直接show，否则去add
        if (fragment != null) {
            currentFragment = fragment;
            transaction.show(fragment);
        } else {
            transaction.add(R.id.container, getFragment(pos), getTag(pos));//加TAG
        }
        transaction.commitAllowingStateLoss();
        //改变颜色值
        //  setSelect(pos);
    }

    private String getTag(int pos) {
        return "Zzg_" + pos;
    }

    private Fragment getFragment(int pos) {
        switch (pos) {
            case 0:
                currentFragment = new DeviceInfoFragment();
                break;
            case 1:
                currentFragment = new LMFragment();
                break;
            case 2:
                currentFragment = new SettingsFragment();
                break;
            default:
                currentFragment = new DeviceInfoFragment();
                break;
        }
        return currentFragment;
    }



}
