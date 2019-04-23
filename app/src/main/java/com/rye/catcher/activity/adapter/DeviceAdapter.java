package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.beans.binding.DeviceBean;
import com.rye.catcher.databinding.DeviceItemBinding;

import java.util.List;

/**
 * Created at 2019/4/23.
 *
 * @author Zzg
 * @function: DeviceAdapter--DataBinding
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private Context mContext;
    private List<DeviceBean> dataList;

    public DeviceAdapter(Context context,List<DeviceBean> list){
       this.mContext=context;
        this.dataList=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

      DeviceItemBinding binding=  DataBindingUtil.inflate(LayoutInflater.from(mContext),
              R.layout.fragment_deviceitem,viewGroup,
                false);
      ViewHolder viewHolder=new ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //数据绑定
        viewHolder.bind(dataList.get(i));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        DeviceItemBinding dataBinding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataBinding=DataBindingUtil.bind(itemView);
        }
        //绑定数据
        public void bind(DeviceBean deviceBean){
            dataBinding.setDevice(deviceBean);
        }
    }
}
