package com.rye.catcher.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rye.catcher.R;

/**
 * Created by ZZG on 2018/7/31.
 */
public class GlideUtils {
    public static GlideUtils glideUtils=new GlideUtils();
    public static GlideUtils getIntance(){
        return glideUtils;
    }
    public static void loadImages(Context context, String url, ImageView view){
        RequestOptions options=new RequestOptions()
                  .placeholder(R.drawable.loading)
                  .error(R.drawable.error)
                  .override(500,400)
                  .fitCenter()
                  .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
             .load(url)
             .apply(options)
             .into(view);
    }
}
