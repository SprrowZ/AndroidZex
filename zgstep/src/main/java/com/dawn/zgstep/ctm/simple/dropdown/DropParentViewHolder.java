package com.dawn.zgstep.ctm.simple.dropdown;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created at 2018/12/19.
 *
 * @author Zzg
 */
public abstract class DropParentViewHolder<T> extends RecyclerView.ViewHolder {

    public DropParentViewHolder(View itemView) {
        super(itemView);
    }
    public abstract  void bindHolder(T param);
}
