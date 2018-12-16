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

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.GridDDViewHolder> {
   private List<String> dataList;
   private LayoutInflater inflater;
    private  zOnClickListener zonClickListener;
   public CityAdapter(List<String> dataList, Context context){
       this.dataList=dataList;
       inflater=LayoutInflater.from(context);
   }
    @NonNull
    @Override
    public GridDDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.drop_down_menu_item,null,false);
       GridDDViewHolder viewHolder=new GridDDViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridDDViewHolder holder, int position) {
        int type= holder.getItemViewType();
        holder.content.setOnClickListener(data->{
            zonClickListener.onClick();
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class GridDDViewHolder extends RecyclerView.ViewHolder{
       TextView content;
        public GridDDViewHolder(View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.content);
        }
    }
    public interface  zOnClickListener{
        void onClick();
    }
    public void setOnClickListenerZ(zOnClickListener onClickListener){
        this.zonClickListener=onClickListener;
    }
}
