package com.rye.catcher.activity.fragment.ctm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rye.base.interfaces.OnItemClickListener;
import com.rye.catcher.R;

import java.util.List;

/**
 * Create by rye
 * at 2020-09-04
 *
 * @description:
 */
public class CtmFragmentAdapter extends RecyclerView.Adapter<CtmVH> {
    private static final String TAG = "CtmFragmentAdapter";
    private List<CtmData> mDataList;
    private OnItemClickListener itemClickListener;


    public CtmFragmentAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CtmVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CtmVH.create(parent);
    }

    public void setDatas(List<CtmData> datas) {
        this.mDataList = datas;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CtmVH holder, int position) {
        holder.mTitle.setText(mDataList.get(position).title);
        holder.mTitle.setOnClickListener(v -> {
            Log.i(TAG, "onClick....");
            itemClickListener.onItemClick(position, v);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

}

class CtmVH extends RecyclerView.ViewHolder {
    TextView mTitle;

    public CtmVH(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.title);
    }

    public static CtmVH create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.fragment_ctm_main_item, parent, false);

        return new CtmVH(root);
    }
}

class CtmData {
    public String title;

    public CtmData(String title) {
        this.title = title;
    }
}
