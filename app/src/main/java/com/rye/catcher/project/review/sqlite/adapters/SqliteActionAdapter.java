package com.rye.catcher.project.review.sqlite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rye.base.interfaces.OnItemClickListener;
import com.rye.catcher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-10-09
 *
 * @description:
 */
public class SqliteActionAdapter extends RecyclerView.Adapter<ActionVH> {
    private OnItemClickListener onItemClickListener;
    private List<String> dataList = new ArrayList<>();

    @NonNull
    @Override
    public ActionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ActionVH.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionVH holder, int position) {
        holder.mAction.setText(dataList.get(position));
        holder.mAction.setOnClickListener(v -> {
            onItemClickListener.onItemClick(position, v);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(List<String> datas) {
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}


class ActionVH extends RecyclerView.ViewHolder {
    public TextView mAction;

    public ActionVH(@NonNull View itemView) {
        super(itemView);
        mAction = itemView.findViewById(R.id.tv_action);
    }

    public static ActionVH create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_sqlite_action, parent, false);
        return new ActionVH(root);
    }
}
