package com.rye.catcher.activity.adapter.recycler.diffcall;

import com.rye.base.common.CommonDiffCallback;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModel;

import java.util.List;

/**
 * ---数据判断
 * DiffUtil使用测试
 */
public class ZDiffCallback extends CommonDiffCallback<DataModel> {
    public ZDiffCallback(List<DataModel> newDataList, List<DataModel> oldDataList) {
        super(newDataList, oldDataList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return  oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
          DataModel oldModel = oldList.get(oldItemPosition);
          DataModel newModel = oldList.get(newItemPosition);

        return oldModel.mBgUrl == newModel.mBgUrl;
    }
}
