package com.rye.catcher.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.project.ImageLoader;
import com.rye.catcher.beans.NewsBean;


import java.util.List;


/**
 * Created by ZZG on 2017/11/1.
 */

public class AsyncAdapter extends BaseAdapter {
    private List<NewsBean> newsBeanList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    public AsyncAdapter(List<NewsBean> newsBeanList, Context context){
         this.newsBeanList=newsBeanList;
         mInflater=LayoutInflater.from(context);
        mImageLoader=new ImageLoader();
    }
    @Override
    public int getCount() {
        return newsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
           viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.asyncloading_item,null);
            viewHolder.iv_Icon=convertView.findViewById(R.id.iv_Icon);
            viewHolder.tv_title=convertView.findViewById(R.id.tv_title);
            viewHolder.tv_content=convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String url=newsBeanList.get(position).newsIconUrl;
        viewHolder.iv_Icon.setTag(url);
       // NewsBean bean=newsBeanList.get(position);
       // viewHolder.iv_Icon.setImageResource(R.mipmap.ic_launcher);
//吧图片的url给imageview

        //底下这个是多线程加载
       //new   ImageLoader().showImageByThread(viewHolder.iv_Icon,newsBeanList.get(position).newsIconUrl);

        //AsyncTask加载,体验效果没多线程加载的效果好
      // new ImageLoader().showImageByAsyncTask(viewHolder.iv_Icon,url);
        //使用了LRU之后，上面这句话就该修改为
        mImageLoader.showImageByAsyncTask(viewHolder.iv_Icon,url);

        viewHolder.tv_title.setText(newsBeanList.get(position).newsTitle);
        viewHolder.tv_content.setText(newsBeanList.get(position).newsContent);
        return convertView;
    }
    class  ViewHolder{
       public ImageView iv_Icon;
        public TextView tv_title;
        public TextView tv_content;
    }
}
