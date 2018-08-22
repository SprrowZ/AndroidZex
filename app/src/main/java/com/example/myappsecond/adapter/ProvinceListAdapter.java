package com.example.myappsecond.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.R;

import java.util.List;

/**
 * Created by Zzg on 2018/8/20.
 */
public class ProvinceListAdapter extends RecyclerView.Adapter<ProvinceListAdapter.ProvinceViewHolder> {
    //数据这方面暂且用Cartoons代替吧

    private List<Cartoons> cartoonsList;
    private Context context;
    public ProvinceListAdapter(Context context, List<Cartoons> list){
        this.context=context;
        this.cartoonsList=list;
    }

         //引入xml
    @Override
    public ProvinceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.province_list_item,viewGroup,false);
        ProvinceViewHolder viewHolder=new ProvinceViewHolder(view);
        return viewHolder;
    }
    //返回list size
    @Override
    public int getItemCount() {
        return cartoonsList.size();
    }
    //操作item的地方
    @Override
    public void onBindViewHolder(ProvinceViewHolder viewHolder, int i) {
        //操作
        Cartoons cartoons=cartoonsList.get(i);
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
