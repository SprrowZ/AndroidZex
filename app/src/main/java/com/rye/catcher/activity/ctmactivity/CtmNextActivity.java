package com.rye.catcher.activity.ctmactivity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.ctm.CtmMainFragment;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmNextActivity extends BaseActivity {

    private Fragment mCurrentFragment ;



    @Override
    public int getLayoutId() {
        return R.layout.activity_ctm_main2;
    }

    @Override
    public void initEvent() {
        mCurrentFragment =  new CtmMainFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragmentContainer, mCurrentFragment);
        ft.commitAllowingStateLoss();
    }


}
