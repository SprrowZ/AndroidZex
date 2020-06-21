package com.rye.catcher.activity.adapter.recycler.diffcall;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * DiffUtil使用案例
 * @param <T>
 */
public abstract class CommonDiffCallback<T> extends DiffUtil.Callback {
    protected List<T> oldList;
    protected List<T> newList;


    public CommonDiffCallback(List<T> newDataList, List<T> oldDataList){
        this.newList = newDataList;
        this.oldList = oldDataList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }
}
