package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.ProjectBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created By RyeCatcher
 * at 2019/9/3
 * Project的列表适配
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private List<ProjectBean> dataList;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    public ProjectListAdapter(Context context, List<ProjectBean> datas){
        this.dataList=datas;
        mInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view =mInflater.inflate(R.layout.project_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.item.setText(dataList.get(i).getName());
            viewHolder.item.setOnClickListener(v -> onItemClickListener.onItemClick(dataList.get(i).getAction()));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
      @BindView(R.id.item) TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener =listener;
    }
   public interface OnItemClickListener {
        void onItemClick(String  action);
    }


}
