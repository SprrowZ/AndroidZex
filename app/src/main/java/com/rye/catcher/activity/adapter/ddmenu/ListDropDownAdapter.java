package com.rye.catcher.activity.adapter.ddmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;

import java.util.List;

public class ListDropDownAdapter extends RecyclerView.Adapter<ListDropDownAdapter.DropDownViewHolder> {
    //数据源
    private List<String> dataList;

    private LayoutInflater inflater;
    private ConstellationAdapter.zOnClickListener zonClickListener;
    public  ListDropDownAdapter(Context context,List<String> datas){
        this.dataList=datas;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ListDropDownAdapter.DropDownViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.drop_down_menu_item,null,false);
        DropDownViewHolder viewHolder=new DropDownViewHolder(view);
        return viewHolder;
    }
    //可在这里指定类型
    @Override
    public void onBindViewHolder(@NonNull ListDropDownAdapter.DropDownViewHolder holder, int position) {
          holder.content.setOnClickListener(data->{
                zonClickListener.onClick();
          });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class DropDownViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        public DropDownViewHolder(View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.content);
        }

    }
    public interface  zOnClickListener{
        void onClick();
    }
    public void setOnClickListenerZ(ConstellationAdapter.zOnClickListener onClickListener){
        this.zonClickListener=onClickListener;
    }
}
