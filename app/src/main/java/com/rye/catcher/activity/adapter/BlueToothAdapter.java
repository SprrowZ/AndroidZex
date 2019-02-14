package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.BTBean;

import java.util.ArrayList;

/**
 * Created at 2019/2/14.
 *
 * @author Zzg
 * @function:
 */
public class BlueToothAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BTBean> dataList;
    private LayoutInflater inflater;
    public BlueToothAdapter(Context context,ArrayList<BTBean> list){
        mContext=context;
        dataList=list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
        if(convertView==null){
        viewHolder=new ViewHolder();
        convertView=inflater.inflate(R.layout.blue_tooth_item,null);
        viewHolder.name=convertView.findViewById(R.id.name);
        viewHolder.address=convertView.findViewById(R.id.address);
        convertView.setTag(viewHolder);
    }else{
        viewHolder=(ViewHolder) convertView.getTag();
    }
    viewHolder.name.setText(dataList.get(position).getName());
    viewHolder.address.setText(dataList.get(position).getAddress());
        return convertView;
    }
    private class ViewHolder{
     private TextView name;
     private TextView address;
    }

}
