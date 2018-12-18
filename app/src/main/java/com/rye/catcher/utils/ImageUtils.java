package com.rye.catcher.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rye.catcher.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by ZZG on 2018/7/31.
 */
public class ImageUtils {
    private static final String TAG="ImageUtils";
    /**
     * 静态内部类
     */
    private static class ImageUtil {
        public static ImageUtils INSTANCE=new ImageUtils();
    }
    public static ImageUtils getIntance(){
        return ImageUtil.INSTANCE;
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
    public static Bitmap ratio(String path,int pixelHeight,int pixelWidth){
        BitmapFactory.Options options=new BitmapFactory.Options();
        //只加载图片宽高，不加载内容
//        options.inJustDecodeBounds=true;
        //位深度最低
        options.inPreferredConfig=Bitmap.Config.RGB_565;
        //预加载，之后就可以获取到图片的宽和高
        BitmapFactory.decodeFile(path,options);
        //输出原图的字节大小
        Log.i(TAG, "ratio:Original "+getBytesByBitmap(BitmapFactory.decodeFile(path,options)).length);
        int originalH=options.outHeight;
        int originalW=options.outWidth;
        options.inSampleSize=getSampleSize(originalH,originalW,pixelHeight,
                pixelWidth);
        //一定要记得返回内容，不然加个鸡儿啊
        options.inJustDecodeBounds=false;
        Log.i(TAG, "ratio:after "+getBytesByBitmap(BitmapFactory.decodeFile(path,options)).length);
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


}
