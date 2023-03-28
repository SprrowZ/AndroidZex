package com.dawn.zgstep.ui.fragment.assist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dawn.zgstep.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/3/27 19:06
 */
public class LoopViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> mData;
    private View mItemView;
    public LoopViewPagerAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Integer> data) {
        this.mData = data;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (mData==null || mData.size()<=0 ) return null;
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item_loop,null);
        mItemView.setFocusable(true);
        ImageView imageView = mItemView.findViewById(R.id.imageView);
        imageView.setImageResource(mData.get(position%mData.size()));

        container.addView(mItemView);
        mItemView.setClickable(false);
        return mItemView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
