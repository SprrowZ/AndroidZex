package com.rye.catcher.activity.adapter.recyadapter.samedata;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.beans.recybean.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private List<DataModel> mList=new ArrayList<>();
    public DemoAdapter(Context context){
        mInflater=LayoutInflater.from(context);
    }
    public  void addList(List<DataModel> list){
        mList.addAll(list);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         switch (viewType){
             case DataModel.TYPE_ONE:
                 return  new TypeOneViewHolder(mInflater.inflate(
                         R.layout.recycleview_type_one,parent,false));
             case DataModel.TYPE_TWO:
                 return  new TypeTwoViewHolder(mInflater.inflate(
                         R.layout.recycleview_type_two,parent,false));
             case DataModel.TYPE_THREE:
                 return  new TypeThreeViewHolder(mInflater.inflate(
                         R.layout.recycleview_type_three,parent,false));
         }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        int viewType = holder.getItemViewType();
        //如果不封装的，就要像上面一样，很繁琐
        ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }
}
