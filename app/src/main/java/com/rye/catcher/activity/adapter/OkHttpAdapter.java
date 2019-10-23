package com.rye.catcher.activity.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created at 2019/3/15.
 *
 * @author Zzg
 * @function:
 */
public class OkHttpAdapter extends RecyclerView.Adapter<OkHttpAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> dataList;
    private  OnItemClick mLister;
    public OkHttpAdapter(Context context,List<String> list ){
        mInflater=LayoutInflater.from(context);
        dataList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =mInflater.inflate(R.layout.okhttp_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.tv.setText(dataList.get(position));
       holder.itemView.setOnClickListener(v -> mLister.onClick(v,position));
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
         @BindView(R.id.iv) ImageView iv;
         @BindView(R.id.tv) TextView tv;
         public ViewHolder(View itemView) {
            super(itemView);
             ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 子Item点击事件
     */
    public  interface OnItemClick{
        void onClick(View view,int pos);
    }
    public void setOnItemClickLister(OnItemClick lister){
         this.mLister=lister;
    }
}
