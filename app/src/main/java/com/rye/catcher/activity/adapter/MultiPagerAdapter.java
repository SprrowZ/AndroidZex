package com.rye.catcher.activity.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created at 2019/3/31.
 *
 * @author Zzg
 * @function:
 */
public class MultiPagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private List<Fragment> fragmentList;
    public MultiPagerAdapter(FragmentManager fm,List<String> titles,
                             List<Fragment> fragmentList) {
        super(fm);
        this.titles=titles;
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
