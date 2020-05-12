package com.rye.catcher.activity.adapter;

import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawn.zgstep.datas.FakeDatas;
import com.dawn.zgstep.datas.ProvinceModel;
import com.rye.catcher.R;

import java.util.List;

/**
 * Created by Zzg on 2018/8/20.
 */
public class ProvinceListAdapter extends RecyclerView.Adapter<ProvinceListAdapter.ProvinceViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ProvinceModel> mList ;
    private Context mContext;
    public ProvinceListAdapter(Context context){
        this.mContext=context;
        this.mList = FakeDatas.Companion.getProvinceLists();
        mLayoutInflater=LayoutInflater.from(context);
    }


    public void addList(List<ProvinceModel> list){
        mList.addAll(list);
    }


   //type,可写可不写，但是这个我觉得还是应该得写。这个得查查


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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ProvinceViewHolder viewHolder, int i) {
        //操作
        ProvinceModel dataModel= mList.get(i);
        viewHolder.avatar.setImageDrawable(mContext.getDrawable(dataModel.getAvatar()));
        viewHolder.province.setText(dataModel.getProvinceName());
        viewHolder.provincialCapital.setText(dataModel.getProvinceCapital());
        viewHolder.population.setText(String.valueOf(dataModel.getPopulation()));

    }
    //ViewHolder用来初始化控件
    class ProvinceViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView province;
        TextView provincialCapital;
        TextView population;
        public ProvinceViewHolder(View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.avatar);
            province=itemView.findViewById(R.id.province);
            provincialCapital=itemView.findViewById(R.id.provincial_capital);
            population=itemView.findViewById(R.id.population);
        }
    }
}
