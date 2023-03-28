package com.dawn.zgstep.ui.fragment.assist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/3/28 16:07
 */
public class LoopFragmentAdapter extends FragmentPagerAdapter {
    private List<Integer> list;
    public LoopFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setList(List<Integer> list, boolean reverse) {
        if(reverse){
            Collections.reverse(list); //倒序排序
        }
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LoopItemFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
