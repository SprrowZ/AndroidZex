package com.example.myappsecond.project.ctmviews.ListView_Delete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myappsecond.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZG on 2017/11/29.
 */
public class List_MergeListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<DataHolder> mDataList = new ArrayList<DataHolder>();
    private View.OnClickListener mDelClickListener;
    //
    private List_LinearLayout.OnScrollListener mScrollListener;
    public List_MergeListAdapter(Context context, List<DataHolder> dataList, View.OnClickListener delClickListener, List_LinearLayout.OnScrollListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
        }
        //传进来监听器
        mDelClickListener=delClickListener;
        //
        mScrollListener= listener;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        //通过位置获取指定的Item
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.bcustom_listview_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DataHolder item = mDataList.get(position);
        holder.title.setText(item.title);
        //可滑动的ListView添加
        item.rootView = (List_LinearLayout)convertView.findViewById(R.id.lin_root);
        item.rootView.scrollTo(0,0);
        //
        item.rootView.setOnScrollListener(this.mScrollListener);
        //删除
        TextView delTv = (TextView) convertView.findViewById(R.id.del);
        delTv.setOnClickListener(this.mDelClickListener);

        return convertView;
    }

    private static class ViewHolder {
        public TextView title;
    }

    public static class DataHolder {
        public String title;
        //Item的父布局，因为滑动的是父布局
        public List_LinearLayout rootView;
    }
    public void removeItem(int position){//删除Item
        mDataList.remove(position);
        notifyDataSetChanged();
    }
}
