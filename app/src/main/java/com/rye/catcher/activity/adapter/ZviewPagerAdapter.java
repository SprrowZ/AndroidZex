package com.rye.catcher.activity.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
