package com.rye.catcher.activity.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RxjavaAdapter extends RecyclerView.Adapter<RxjavaAdapter.ViewHolder> {
    private List<String> dataList;
    private LayoutInflater inflater;
    private OnItemClick mListener;
    public  RxjavaAdapter(Context context,List<String> list){
        this.dataList=list;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rxjava_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(dataList.get(position));
        holder.itemView.setOnClickListener(v -> mListener.OnClick(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        tv=itemView.findViewById(R.id.tv);
        }
    }

    public  interface  OnItemClick{
        void OnClick(int pos);
    }

    public void setOnClickListener(OnItemClick listener){
        this.mListener=listener;
    }
}
