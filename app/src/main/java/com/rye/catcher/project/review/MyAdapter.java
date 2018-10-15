package com.rye.catcher.project.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;

import java.util.List;

/**
 * Created by ZZG on 2017/10/25.
 */

public class MyAdapter extends BaseAdapter {
    private  List<ItemBean> mList;
    private LayoutInflater mInflater;
    public MyAdapter(List<ItemBean> list,Context context){
        mList=list;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
   ViewHolder viewHolder ;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.review_baseadapter_item,null);
            viewHolder.iv=convertView.findViewById(R.id.iv);
            viewHolder.title=convertView.findViewById(R.id.title);
            viewHolder.content=convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }
        else{
           viewHolder= (ViewHolder) convertView.getTag();
        }
   ItemBean bean=mList.get(position);
        viewHolder.iv.setImageResource(bean.ImageID);
        viewHolder.title.setText(bean.title);
        viewHolder.content.setText(bean.content);
        return convertView;
    }
}
class  ViewHolder{
    public ImageView iv;
    public TextView title;
    public TextView content;
}