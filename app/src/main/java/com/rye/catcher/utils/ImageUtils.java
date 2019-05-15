package com.rye.catcher.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rye.catcher.R;
import com.rye.catcher.RyeCatcherApp;
import com.rye.catcher.utils.ExtraUtil.MD5Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ZZG on 2018/7/31.
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";

    private LruCache<String, Bitmap> mLrucache;

    /**
     * 静态内部类
     */
    private static class ImageUtil {
        public static final ImageUtils INSTANCE = new ImageUtils();
    }

    public static ImageUtils getIntance() {
        return ImageUtil.INSTANCE;
    }
    //私有构造器
    private ImageUtils() {
        //获取最大缓存空间
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);//前面的应该就是运存了
        Log.i(TAG, "ImageUtils: "+maxSize/8/1024/1024);
        mLrucache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * 加载网络图片，通过Glide，因为底下有自己封装的LruCache，所以需要分清Orz
     *
     * @param context
     * @param url
     * @param view
     */
    public static void loadImages(Context context, String url, ImageView view) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .override(500, 400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(view);
    }

    /**
     * 保存Bitmap到本地
     * @param bitmap
     * @param name
     */
    public void saveBitmap(final  Bitmap bitmap,String name){
        File file=new File(SDHelper.getImageFolder(),name);
        if (!file.exists()){
        }else {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到图片的字节流，传输图片用
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBytesByBitmap(Bitmap bitmap) {
        //只有JPEG格式的可以压缩
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 压缩图片
     *
     * @param path
     * @param pixelHeight
     * @param pixelWidth
     */
    public static Bitmap ratio(String path, int pixelHeight, int pixelWidth, InputStream inputStream) {
        Log.i(TAG, "ratio: 压缩图片...");
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只加载图片宽高，不加载内容
        options.inJustDecodeBounds = true;
        //位深度最低
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                //预加载，之后就可以获取到图片的宽和高
                BitmapFactory.decodeFile(path, options);
            }
        } else if (inputStream != null) {
            Log.i(TAG, "ratio: ");
            BitmapFactory.decodeStream(inputStream, null, options);
        } else {
            Log.i(TAG, "ratio: Failed");
            return null;
        }
        int originalH = options.outHeight;
        int originalW = options.outWidth;
        options.inSampleSize = getSampleSize(originalH, originalW, pixelHeight,
                pixelWidth);
        //一定要记得返回内容，不然加个鸡儿啊
        options.inJustDecodeBounds = false;
        if (path!=null){
            return BitmapFactory.decodeFile(path, options);
        }else{
            Log.i(TAG, "ratio: decodeStream...");
            return BitmapFactory.decodeStream(inputStream,null,options);
        }
    }

    /**
     * 计算压缩率
     *
     * @param originalH
     * @param originalW
     * @param pixelHeight
     * @param pixelWidth
     * @return
     */
    private static int getSampleSize(int originalH, int originalW, int pixelHeight, int pixelWidth) {
        int inSampleSize = 1;
        if (originalH > originalW && originalH > pixelHeight) {
            inSampleSize = originalH / pixelHeight;
        } else if (originalW > originalH && originalW > pixelWidth) {
            inSampleSize = originalW / pixelWidth;
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        return inSampleSize;
    }

    /**
     * 本地有图片则取本地，没有则下载后存本地
     *
     * @param imgName
     * @param height
     * @param width
     * @param url
     * @return
     */
    public Bitmap getBitmap(String imgName, int height, int width, String url) {
        Bitmap bitmap = ratio(SDHelper.getImageFolder() + imgName, height, width, null);
        if (bitmap != null) {
            return bitmap;
        } else if (url != null) {
            FileUtils.saveImage(url, imgName);//
            return ratio(SDHelper.getImageFolder() + imgName, height, width, null);
        }
        return BitmapFactory.decodeResource(EchatAppUtil.getAppContext().getResources(), R.drawable.default_img);
    }

/********************************************LruCache--图片三级缓存**********************************************/


    /**
     * 用户加载网络图片
     *
     * @param view
     * @param url
     */
    public void displayImage(ImageView view, String url) {
        Bitmap bitmap;
        bitmap = getBitmapFromCache(url);
        Log.i(TAG, "displayImage: bitmap:"+(bitmap==null));
        if (bitmap != null) {
            Log.i(TAG, "displayImage: getBitmap From Cache");
            view.setImageBitmap(bitmap);
            return;
        }
        bitmap=getBitmapFromLocal(url);
        if (bitmap!=null){
            Log.i(TAG, "displayImage: getBitmap From Local");
            view.setImageBitmap(bitmap);
            //从本地取得也要放到缓存中
            putBitmapToCache(url,bitmap);
        return ;
        }
        getBitmapFromNet(view,url);
    }

    /**
     * 从缓存中读取图片
     *
     * @param url
     * @return
     */
    private Bitmap getBitmapFromCache(String url) {
        return mLrucache.get(url);
    }

    /**
     * 将下载下来的图片加在缓存中
     *
     * @param bitmap
     * @param url
     */
    private void putBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            mLrucache.put(url, bitmap);
        }
    }

    private Bitmap getBitmapFromLocal(String url) {
      String fileName=null;
      Bitmap bitmap=null;
        try {
            fileName=MD5Encoder.encode(url);
            File file=new File(SDHelper.getImageFolder(),fileName);
            bitmap=BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveBitmapToLocal(String url, Bitmap bitmap) {
         String fileName=null;
        try {
            fileName= MD5Encoder.encode(url);
            File file=new File(SDHelper.getImageFolder(),fileName);
            File parentFile=file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载网络图片，下载完成后置给ImageView，并保存到内存、本地
     *
     * @param view
     * @param url
     */
    private void getBitmapFromNet(ImageView view, String url) {
        Log.i(TAG, "getBitmapFromNet: ...");
        new DownloadTask().execute(view, url);
    }

    /**
     * 下载图片并保存到缓存中，用原生的HttpUrlConnection下载吧
     *
     * @param view
     * @param url
     */
    private Bitmap downloadImage(ImageView view, String url) {
        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {//连接成功
           //      Bitmap bitmap = ratio(null, 800, 500, inputStream);
              Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                Log.i(TAG, "downloadImage: "+bitmap);
                return bitmap;
            } else {
                Log.i(TAG, "downloadImage: download Failed...");
                Bitmap bitmap = BitmapFactory.decodeResource(RyeCatcherApp.getContext().getResources(),
                        R.drawable.default_portrait);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            Log.i(TAG, "downloadImage: "+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(TAG, "downloadImage: "+e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载图片异步线程池，AsyncTask是Handler和线程池的结合
     */
    class DownloadTask extends AsyncTask<Object, Void, Bitmap> {
        private ImageView imageView;
        private String url;

        @Override
        protected Bitmap doInBackground(Object[] objects) {
            Log.i(TAG, "doInBackground: downloadImage...");
            imageView = (ImageView) objects[0];
            url = (String) objects[1];
            return downloadImage(imageView, url);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 运行在主线程中
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            //保存到缓存中
            putBitmapToCache(url, bitmap);
            saveBitmapToLocal(url, bitmap);
            super.onPostExecute(bitmap);
        }
    }


}
