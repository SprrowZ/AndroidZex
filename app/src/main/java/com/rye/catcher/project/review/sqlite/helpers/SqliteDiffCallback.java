package com.rye.catcher.project.review.sqlite.helpers;

import com.rye.base.common.CommonDiffCallback;

import java.util.List;

/**
 * Create by rye
 * at 2020-10-10
 *
 * @description:
 */
public class SqliteDiffCallback extends CommonDiffCallback<String> {
    public SqliteDiffCallback(List<String> newDataList, List<String> oldDataList) {
        super(newDataList, oldDataList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
