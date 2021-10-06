package com.rye.opengl.demos.other;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rye.opengl.R;

/**
 * Create by  [Rye]
 * <p>
 * at 2021/10/3 1:04 下午
 */
class ShapeViewHolder extends RecyclerView.ViewHolder {
    FrameLayout mRoot;
    TextView mContent;
    public ShapeViewHolder(@NonNull View itemView, IOnItemClickListener itemClickListener) {
        super(itemView);
        mContent = itemView.findViewById(R.id.content);
        mRoot = itemView.findViewById(R.id.root);
        mRoot.setOnClickListener(v -> itemClickListener.onClick(v));
    }

    public static ShapeViewHolder create(ViewGroup parent, IOnItemClickListener itemClickListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_shape, parent, false);
        return new ShapeViewHolder(itemView,itemClickListener);
    }
}