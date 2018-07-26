package com.example.myappsecond.project.AsyncLoading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ZZG on 2017/11/1.
 */

public class ImageLoader  {
    private ImageView imageView;
    private String url;
    //使用Lru缓存策略，省的图片来回加载浪费流量
    //key是url故用string，value是图片，故用bitmap
    LruCache<String,Bitmap> mCache;
public ImageLoader(){
    int maxMemory=(int)Runtime.getRuntime().maxMemory();
    int cacheSize=maxMemory/4;
    mCache=new LruCache<String,Bitmap>(cacheSize){
        //sizeOf方法每次存入内存的时候调用，我们需要把bitmap大小给他
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };
}
//增加到缓存
public void addBitmapToCache(String url ,Bitmap bitmap){
if (getBitmapFromCache(url)==null){
    mCache.put(url,bitmap);
}
}
//从缓存中获取数据
public Bitmap getBitmapFromCache(String url){
    return mCache.get(url);
}


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (imageView.getTag().equals(url)) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }

        }
    };
    public void showImageByThread(ImageView imageView, final String url){
        this.imageView=imageView;
         this.url=url;
 new Thread(){
     @Override
     public void run() {
         super.run();
         Bitmap bitmap=getBitmapFromURL(url);
         Message message=Message.obtain();
         message.obj=bitmap;
         handler.sendMessage(message);
     }
 }.start();
    }

 public Bitmap getBitmapFromURL(String urlString){
     Bitmap bitmap;
     InputStream is = null;
     try {
         URL url=new URL(urlString);
         HttpURLConnection connection= (HttpURLConnection) url.openConnection();
         is=new BufferedInputStream(connection.getInputStream());
         bitmap= BitmapFactory.decodeStream(is);
         connection.disconnect();
         return  bitmap;
     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }finally
      {
          try {
              is.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
     return null;
 }

public void showImageByAsyncTask(ImageView imageView,String url){
    //加载的时候使用Lru策略
    Bitmap bitmap=getBitmapFromCache(url);
    if (bitmap==null){//如果缓存中没有就使用网络加载
        new NewsAsyncTask(imageView,url).execute(url);
    }else{//如果有那就直接设置吧
        imageView.setImageBitmap(bitmap);
    }

}
private class NewsAsyncTask extends AsyncTask<String,Void,Bitmap> {
    private ImageView mImageView;
    private String murl;

    public NewsAsyncTask(ImageView imageView,String  url) {
        mImageView = imageView;
        this.murl=url;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        //这个方法中是把图片从网上下载下来，使用了Lru策略之后，下载完图片需要将图片放入缓存之中，故修改如下
        //从缓存中取出对应的图片
        Bitmap bitmap=getBitmapFromURL(params[0]);
          String iurl=params[0];
        if (bitmap!=null){
            //j将不在缓存中的图片加入到缓存
            addBitmapToCache(iurl,bitmap);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(mImageView.getTag()==null){//初始化时并未设置Tag

            mImageView.setTag(murl);

        }else if(mImageView.getTag().equals(murl)){//已经设置过Tag

            mImageView.setImageBitmap(bitmap);

        }
    }

}

}











































