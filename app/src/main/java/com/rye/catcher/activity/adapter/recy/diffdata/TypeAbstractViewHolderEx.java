package com.rye.catcher.activity.adapter.recy.diffdata;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

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
