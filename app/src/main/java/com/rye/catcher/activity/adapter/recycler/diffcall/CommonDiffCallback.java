package com.rye.catcher.activity.adapter.recycler.diffcall;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public abstract class CommonDiffCallback<T> extends DiffUtil.Callback {
    protected List<T> oldList;
    protected List<T> newList;
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }


    public void setDatas(List<T> newDataList, List<T> oldDataList) {
         this.newList = newDataList;
         this.oldList = oldDataList;
    }

//    @Override
//    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        return false;
//    }
//
//    @Override
//    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        return false;
//    }
}
