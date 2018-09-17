package com.example.myappsecond.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappsecond.R;
import com.example.myappsecond.activity.testdata.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/8/20.
 */
public class ProvinceListAdapter extends RecyclerView.Adapter<ProvinceListAdapter.ProvinceViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<DataModel> mList =new ArrayList<>();
    private Context context;
    public ProvinceListAdapter(Context context, List<DataModel> list){
        this.context=context;
        this.mList =list;
        mLayoutInflater=LayoutInflater.from(context);
    }
    public void addList(List<DataModel> list){
        mList.addAll(list);
    }
   //type,可写可不写，但是这个我觉得还是应该得写。这个得查查
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    //引入xml
    @Override
    public ProvinceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= mLayoutInflater.inflate(R.layout.province_list_item,viewGroup,false);
        ProvinceViewHolder viewHolder=new ProvinceViewHolder(view);
        return viewHolder;
    }
    //返回list size
    @Override
    public int getItemCount() {
        return mList.size();
    }
    //操作item的地方
    @Override
    public void onBindViewHolder(ProvinceViewHolder viewHolder, int i) {
        //操作
        DataModel dataModel= mList.get(i);
    }
    //ViewHolder用来初始化控件
    class ProvinceViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView province;
        TextView provincial_captial;
        TextView population;
        public ProvinceViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            province=itemView.findViewById(R.id.province);
            provincial_captial=itemView.findViewById(R.id.provincial_capital);
            population=itemView.findViewById(R.id.population);
        }
    }
}
