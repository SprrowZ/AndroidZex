package com.rye.catcher.activity.adapter.recycler.samedata;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public abstract class TypeAbstractViewHolder<T> extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T dataModel);
}
