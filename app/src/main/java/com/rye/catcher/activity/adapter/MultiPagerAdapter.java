package com.rye.catcher.activity.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
