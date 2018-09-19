package com.example.myappsecond.activity.adapter.recyadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myappsecond.activity.testdata.DataModel;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public abstract class TypeAbstractViewHolder extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(DataModel dataModel);
}
