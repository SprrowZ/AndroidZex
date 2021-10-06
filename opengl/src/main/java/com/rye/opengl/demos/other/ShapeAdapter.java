package com.rye.opengl.demos.other;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ShapeAdapter extends RecyclerView.Adapter<ShapeViewHolder> {
    private List<ShapeItemData> dataList = new ArrayList<>();
    private IOnItemClickListener itemClickListener;
    public ShapeAdapter(IOnItemClickListener listener){
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ShapeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ShapeViewHolder.create(parent,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShapeViewHolder holder, int position) {
         holder.mContent.setText(dataList.get(position).content);
         holder.mRoot.setTag(dataList.get(position));
    }

    public void setData(List<ShapeItemData> datas){
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


}



