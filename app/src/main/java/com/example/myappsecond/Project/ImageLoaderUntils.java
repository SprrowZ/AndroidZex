package com.example.myappsecond.Project;

import android.app.Activity;
import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myappsecond.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by ZZG on 2017/10/17.
 */

public class ImageLoaderUntils extends Activity {
    private ImageView imageView;
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageloader);
        imageView=findViewById(R.id.imageView);
        imageLoader=ImageLoader.getInstance();
        //file:///storage/emulated/0/文件路径
        //"drawable://"+R.drawable.test系统文件路径
        //"file:///"+getImgPathList().get(0)
        //http://img3.imgtn.bdimg.com/it/u=357715224,1477815525&fm=27&gp=0.jpg网络图片路径

//        imageLoader.displayImage("http://img3.imgtn.bdimg.com/it/u=357715224,1477815525&fm=27&gp=0.jpg", imageView, new ImageLoadingListener() {
//             @Override
//             public void onLoadingStarted(String s, View view) {
//                 Toast.makeText(getApplicationContext(),"downloading..",Toast.LENGTH_SHORT).show();
//             }
//
//             @Override
//             public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//             }
//
//             @Override
//             public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//
//             }
//
//             @Override
//             public void onLoadingCancelled(String s, View view) {
//
//             }
//         });
        final ImageSize imageSize=new ImageSize(400,400);
        imageLoader.loadImage("http://img3.imgtn.bdimg.com/it/u=357715224,1477815525&fm=27&gp=0.jpg",
                imageSize, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
           imageView.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
   }


}
