package com.rye.catcher.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rye.catcher.R;
import com.rye.catcher.zApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ZZG on 2018/7/31.
 */
public class ImageUtils {
    private static final String TAG="ImageUtils";

    private LruCache<String,Bitmap> mLrucache;
    /**
     * 静态内部类
     */
    private static class ImageUtil {
        public static ImageUtils INSTANCE=new ImageUtils();
    }
    public static ImageUtils getIntance(){
        return ImageUtil.INSTANCE;
    }

    private ImageUtils(){
        //获取最大缓存空间
        int maxSize= (int) (Runtime.getRuntime().maxMemory()/8);//前面的应该就是运存了
        mLrucache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 加载网络图片，通过Glide，因为底下有自己封装的LruCache，所以需要分清Orz
     * @param context
     * @param url
     * @param view
     */
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

    /**
     * 得到图片的字节流，传输图片用
     * @param bitmap
     * @return
     */
    public static  byte[] getBytesByBitmap(Bitmap bitmap) {
        //只有JPEG格式的可以压缩
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 压缩图片
     * @param path
     * @param pixelHeight
     * @param pixelWidth
     */
    public static Bitmap ratio(String path,int pixelHeight,int pixelWidth,InputStream inputStream){
        BitmapFactory.Options options=new BitmapFactory.Options();
        //只加载图片宽高，不加载内容
        options.inJustDecodeBounds=true;
        //位深度最低
        options.inPreferredConfig=Bitmap.Config.RGB_565;
        if (path!=null){
            File file=new File(path);
            if (file.exists()){
                //预加载，之后就可以获取到图片的宽和高
                BitmapFactory.decodeFile(path,options);
            }
        }else if (inputStream!=null){
            BitmapFactory.decodeStream(inputStream,null,options);
        }else{
            Log.i(TAG, "ratio: Failed");
            return null;
        }
        int originalH=options.outHeight;
        int originalW=options.outWidth;
        options.inSampleSize=getSampleSize(originalH,originalW,pixelHeight,
                pixelWidth);
        //一定要记得返回内容，不然加个鸡儿啊
        options.inJustDecodeBounds=false;
       return BitmapFactory.decodeFile(path,options);
    }

    /**
     * 计算压缩率
     * @param originalH
     * @param originalW
     * @param pixelHeight
     * @param pixelWidth
     * @return
     */
    private static int getSampleSize(int originalH, int originalW, int pixelHeight, int pixelWidth) {
     int inSampleSize=1;
     if (originalH>originalW&&originalH>pixelHeight){
         inSampleSize=originalH/pixelHeight;
     }else if (originalW>originalH&&originalW>pixelWidth){
         inSampleSize=originalW/pixelWidth;
     }
     if (inSampleSize<=0){
         inSampleSize=1;
     }
     return inSampleSize;
    }

    /**
     * 本地有图片则取本地，没有则下载后存本地
     * @param imgName
     * @param height
     * @param width
     * @param url
     * @return
     */
    public Bitmap getBitmap(String imgName,int height,int width,String url){
        Bitmap bitmap=ratio(SDHelper.getImageFolder()+imgName,height,width,null);
        if (bitmap!=null){
            return  bitmap;
        }else if (url!=null){
          FileUtils.saveImage(url,imgName);//
          return ratio(SDHelper.getImageFolder()+imgName,height,width,null);
        }
        return BitmapFactory.decodeResource(EchatAppUtil.getAppContext().getResources(),R.drawable.default_img);
    }
/********************************************LruCache**********************************************/


    /**
     * 用户加载网络图片
     * @param view
     * @param url
     */
    public void displayImage(ImageView view,String url){
       Bitmap bitmap=getBitmapFromCache(url);
       if (bitmap!=null){
           view.setImageBitmap(bitmap);
       }else{
           downloadImage(view,url);
       }
    }



    /**
     * 从缓存中读取图片
     * @param url
     * @return
     */
    private Bitmap getBitmapFromCache(String url){
        return mLrucache.get(url);
    }

    /**
     * 将下载下来的图片加在缓存中
     * @param bitmap
     * @param url
     */
    private void putBitmapToCache(Bitmap bitmap,String url){
          if (bitmap!=null){
              mLrucache.put(url,bitmap);
          }
    }

    /**
     * 下载图片并保存到缓存中，用原生的HttpUrlConnection下载吧
     * @param view
     * @param url
     */
    private void downloadImage(ImageView view, String url) {
        try {
            URL mUrl=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) mUrl.openConnection();
            conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            InputStream inputStream=conn.getInputStream();
            if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){//连接成功
                Bitmap bitmap=ratio(null,view.getWidth(),view.getHeight(),inputStream);
                //加载数据
                putBitmapToCache(bitmap,url);
                view.setImageBitmap(bitmap);
            }else{
                Log.i(TAG, "downloadImage: download Failed...");
                Bitmap bitmap=BitmapFactory.decodeResource(zApplication.getContext().getResources(),
                        R.drawable.default_portrait);
                view.setImageBitmap(bitmap);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
