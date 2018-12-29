package com.rye.catcher.common.bigimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rye.catcher.R;
import com.rye.catcher.base.photoview.PhotoView;
import com.rye.catcher.utils.ToastUtils;


import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    private ArrayList<String> imgList;
    private Context mContext;
    //图片加载组件
    public ImagePagerAdapter(Context context, ArrayList imgList){
        this.imgList=imgList;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return imgList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView photoView=new PhotoView(mContext);
        //从文件中获取bitmap
        Bitmap bitmap=BitmapFactory.decodeFile(imgList.get(position));
        photoView.setImageBitmap(bitmap);
        photoView.setOnLongClickListener(v -> {
            ToastUtils.shortMsg("ddd");
            return false;
        });
        container.addView(photoView,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
