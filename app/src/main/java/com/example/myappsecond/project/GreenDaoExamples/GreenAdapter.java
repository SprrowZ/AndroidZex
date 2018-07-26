package com.example.myappsecond.project.GreenDaoExamples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/7/7.
 */
public class GreenAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Cartoons> list=new ArrayList<Cartoons>();
    private Context context;
    public GreenAdapter(Context context,List<Cartoons> list){
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }
    Cartoons cartoons=new Cartoons();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cartoons=list.get(position);
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.greendao_item,null);
            viewHolder.iv=convertView.findViewById(R.id.iv);
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.hero=convertView.findViewById(R.id.hero);
            viewHolder.heroine=convertView.findViewById(R.id.heroine);
            viewHolder.detail=convertView.findViewById(R.id.detail);
            viewHolder.status=convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
      //进行赋值操作
        viewHolder.iv.setImageResource(R.drawable.my1);
        viewHolder.name.setText(cartoons.getNAME());
        viewHolder.hero.setText(cartoons.getHERO());
        viewHolder.heroine.setText(cartoons.getHEROINE());
        if (cartoons.getIS_END()){
            viewHolder.status.setText(R.string.end);
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.red_theme));
        }else{
            viewHolder.status.setText(R.string.keeping);
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.green3));
        }

        return convertView;
    }

}
class ViewHolder{
    public ImageView iv;
    public TextView  name;
    public TextView  hero;
    public TextView  heroine;
    public TextView  detail;
    public TextView  status;//状态
}