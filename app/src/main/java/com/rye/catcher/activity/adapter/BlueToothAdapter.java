package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private static final String TAG = "BlueToothAdapter";
    private Context mContext;
    private ArrayList<BTBean> dataList;
    private LayoutInflater inflater;

    public BlueToothAdapter(Context context, ArrayList<BTBean> list) {
        mContext = context;
        dataList = list;
        inflater = LayoutInflater.from(context);
        Log.i(TAG, "BlueToothAdapter: " + list.toString());
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + dataList.size());
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
        Log.i(TAG, "getView: " + dataList.size());
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.blue_tooth_item, null);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.address = convertView.findViewById(R.id.address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BTBean bean=dataList.get(position);
        if (bean.getName()==null){
            viewHolder.name.setText("-BlueDevice-");
        }else{
            viewHolder.name.setText(bean.getName());
        }
        viewHolder.address.setText(bean.getAddress());
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView address;
    }

}
