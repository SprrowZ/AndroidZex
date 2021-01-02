package com.dawn.zgstep.ui.ctm.simple.dropdown;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dawn.zgstep.R;

import java.util.List;

/**
 * Created at 2018/12/19.
 *
 * @author Zzg
 */
public class DropDownAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private LayoutInflater mInflater;
    private List<DropBean> dataList;

    public DropDownAdapter(List<DropBean> dataList, Context context) {
        this.dataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new CityViewHolder(mInflater.inflate(R.layout.drop_down_menu_item,
                        parent, false));
            case TYPE_TWO:
                return new AsViewHolder(mInflater.inflate(R.layout.drop_down_menu_item2,
                        parent, false));
            case TYPE_THREE:
                return new ConstellationViewHolder(mInflater.inflate(R.layout.drop_down_menu_item3,
                        parent, false));
        }
        return null;
    }

    /**
     * 毕竟ViewHolder都有一个基类，相同的方法，直接用基类即可
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DropParentViewHolder) holder).bindHolder(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }
}
