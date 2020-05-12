package com.rye.catcher.activity.adapter.recycler.samedata;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModel;
import com.rye.catcher.activity.adapter.recycler.recybean.DataModelEx;

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
        mList.clear();
        mList.addAll(list);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         switch (viewType){
             case DataModelEx.TYPE_ONE:
                 return  new TypeOneViewHolder(mInflater.inflate(
                         R.layout.recycleview_type_one_normal,parent,false));
             case DataModelEx.TYPE_TWO:
                 return  new TypeTwoViewHolder(mInflater.inflate(
                         R.layout.recycleview_type_two_normal,parent,false));
             case DataModelEx.TYPE_THREE:
                 return  new TypeThreeViewHolder(mInflater.inflate(
                         R.layout.recycleview_type_three_normal,parent,false));
         }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        int viewType = holder.getItemViewType();
        //如果不封装的，就要像上面一样，很繁琐
        ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));
        Log.i("DemoAdapter","---onBindViewHolder:"+position);

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 全部更新
     */
    public void notifyItemChanged() {
        notifyDataSetChanged();
    }

    /**
     * 更新一个
     *
     * @param pos
     */
    public void notifyItemChangedEx(int pos) {
        notifyItemChanged(pos);
    }

    /**
     * @param startPos
     * @param itemCount
     */
    public void notifyItemRanged(int startPos, int itemCount) {
        notifyItemRangeChanged(startPos, itemCount);
    }

    public void notifyItemInserted(int startPos, int itemCount) {
        notifyItemRangeInserted(startPos, itemCount);
    }



    @Override
    public int getItemViewType(int position) {
        return mList.get(position).mType;
    }
}
