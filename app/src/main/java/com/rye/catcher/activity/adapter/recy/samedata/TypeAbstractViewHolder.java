package com.rye.catcher.activity.adapter.recy.samedata;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.rye.catcher.activity.adapter.recy.recybean.DataModelEx;

import butterknife.ButterKnife;

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
