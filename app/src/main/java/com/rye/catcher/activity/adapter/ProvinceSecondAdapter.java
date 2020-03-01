package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dawn.zgstep.datas.FakeDatas;
import com.dawn.zgstep.datas.ProvinceModel;
import com.rye.catcher.R;

import java.util.List;

/**
 * Created by Zzg on 2018/8/20.
 */
public class ProvinceSecondAdapter extends RecyclerView.Adapter<ProvinceSecondAdapter.ProvinceViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ProvinceModel> mList ;
    private Context mContext;
    public ProvinceSecondAdapter(Context context){
        this.mContext=context;
        this.mList = FakeDatas.Companion.getProvinceLists();
        mLayoutInflater=LayoutInflater.from(context);
    }

    //引入xml
    @Override
    public ProvinceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= mLayoutInflater.inflate(R.layout.province_second_item,viewGroup,false);
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
    }
    //ViewHolder用来初始化控件
    class ProvinceViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;

        public ProvinceViewHolder(View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.avatar);

        }
    }
}
