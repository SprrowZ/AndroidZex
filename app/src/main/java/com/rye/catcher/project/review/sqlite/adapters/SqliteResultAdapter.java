package com.rye.catcher.project.review.sqlite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.rye.catcher.R;
import com.rye.catcher.agocode.beans.PgcBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-10-09
 *
 * @description: 查询结果适配器
 */
public class SqliteResultAdapter extends RecyclerView.Adapter<ResultVH> {



    private List<PgcBean> dataList = new ArrayList<>();

    @NonNull
    @Override
    public ResultVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ResultVH.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultVH holder, int position) {
        holder.tvId.setText(dataList.get(position).pgcId);
        holder.tvName.setText(dataList.get(position).pgcName);
        holder.tvNation.setText(dataList.get(position).pgcNation);
        holder.tvNumber.setText(String.valueOf(dataList.get(position).pgcNumber));
    }

    @Override
    public int getItemCount() {
        return dataList.size() +1;
    }



    public void setData(List<PgcBean> datas) {
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

//    public void diffUpdate(List<String> newDataList) {
//        SqliteDiffCallback diffCallback = new SqliteDiffCallback(newDataList, dataList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        diffResult.dispatchUpdatesTo(this);
//        this.dataList = newDataList;
//    }

}

class ResultVH extends RecyclerView.ViewHolder {
    TextView tvId;
    TextView tvName;
    TextView tvNation;
    TextView tvNumber;

    public ResultVH(@NonNull View itemView) {
        super(itemView);
        tvId = itemView.findViewById(R.id.tv_id);
        tvName = itemView.findViewById(R.id.tv_name);
        tvNation = itemView.findViewById(R.id.tv_nation);
        tvNumber = itemView.findViewById(R.id.tv_number);
    }

    public static ResultVH create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_sqlite_result, parent, false);
        return new ResultVH(itemView);
    }
}