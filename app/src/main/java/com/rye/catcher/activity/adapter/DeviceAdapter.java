package com.rye.catcher.activity.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.binding.DeviceBean;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created at 2019/4/23.
 *
 * @author Zzg
 * @function: DeviceAdapter--DataBinding
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private  final String TAG=this.getClass().getSimpleName();
    private Context mContext;
    private List<DeviceBean> dataList;
    private LayoutInflater inflater;
    public DeviceAdapter(Context context,List<DeviceBean> list){
       this.mContext=context;
        this.dataList=list;
        inflater=LayoutInflater.from(context);
        Log.i(TAG, "DeviceAdapter:dataSize: "+list.size());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

      View view=inflater.inflate(R.layout.fragment_device_item,viewGroup,
                false);
      ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //数据绑定
         viewHolder.name.setText(dataList.get(i).getTitle());
         viewHolder.content.setText(dataList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
