package com.rye.catcher.activity.adapter.recycler.diffcall;

import com.rye.catcher.activity.adapter.recycler.recybean.DataModel;

/**
 * ---数据判断
 */
public class ZDiffCallback extends CommonDiffCallback<DataModel> {
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return  oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
