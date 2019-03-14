package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rye.catcher.activity.fragment.orr.OkhttpFragment;

import java.util.List;

/**
 * ViewPager For Fragments
 */
public class ZviewPagerAdapter extends FragmentPagerAdapter {
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private Context mContext;
     public ZviewPagerAdapter(FragmentManager fm,List<String> indictors,
                              List<Fragment> fragments) {
        super(fm);
       this.tabIndicators=indictors;
       this.tabFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position);
    }
}
