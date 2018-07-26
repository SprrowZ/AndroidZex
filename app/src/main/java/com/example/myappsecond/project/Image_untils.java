package com.example.myappsecond.project;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.myappsecond.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by ZZG on 2017/10/17.
 */

public class Image_untils extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(700,1000)
                //max width,max height,即保存的每个缓存文件的最大长度
                .discCacheExtraOptions(700,1000,null)
                //线程池内加载的数量，最大为5
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2*1024*1024)
                .discCacheSize(50*1024*1024)
                //将保存的资源的URI名称用MD5加密
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                //缓存的文件数量
                .discCacheFileCount(100)
                .discCache(new UnlimitedDiscCache(new File(Environment.getExternalStorageDirectory()
                + "/myApp/imgCache")))
                 .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(new BaseImageDownloader(this,5*1000,30*1000))
                .writeDebugLogs()
                .build();//开始构建
        ImageLoader.getInstance().init(config);


    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.test1)//设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.test2)//设置图片Uri为空或是错误时候显示的图片
                .showImageOnFail(R.drawable.test3)//设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片的编码方式
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(60))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(3000))//图片加载后渐入的动画时间
                .build();//构建完成
        return options;
    }
}
