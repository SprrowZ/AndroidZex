package com.example.myappsecond.activity.adapter.recyadapter.diffdata;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myappsecond.activity.testdata.DataModel;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public abstract class TypeAbstractViewHolderEx<T> extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolderEx(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T dataModel);
}
