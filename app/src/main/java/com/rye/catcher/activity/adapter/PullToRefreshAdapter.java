package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rye.catcher.R;
import com.rye.catcher.beans.ImageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created at 2019/1/15.
 *
 * @author Zzg
 * @function:
 */
public class PullToRefreshAdapter extends RecyclerView.Adapter<PullToRefreshAdapter.zViewHolder> {
    private List<ImageBean> dataList;
    private LayoutInflater inflater;
    private Context mContext;
    public PullToRefreshAdapter(Context context){
        this.mContext=context;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public zViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_pull_refresh_item,parent,false);
        zViewHolder viewHolder=new zViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull zViewHolder holder, int position) {
        //加载图片
       Glide.with(mContext).load(dataList.get(position).getUrl()).into(holder.img);
       //添加文字
       holder.description.setText(dataList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataList==null?0:dataList.size();
    }

    /**
     * 添加数据
     * @param mList
     */
    public void setDataList(List<ImageBean> mList){
     this.dataList=mList;
     notifyDataSetChanged();
    }

    class zViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img) ImageView img;
        @BindView(R.id.description) TextView description;
        public zViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
