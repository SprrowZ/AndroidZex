package com.example.myappsecond.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myappsecond.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZZG on 2017/10/18
 */

public class Camera_Fragment extends Fragment {
    private static final int EXTERNAL_STORAGE_REQ_CODE =12 ;
    private ImageView imageView;
    private static int REQ_1=1;
    //whole picture
    public static  final  int TAKE_PHOTO=2;
    private Uri imageUri;
    //
    private View view;
    private String mFilepath;
    private Button btnCamera;
    private Button btnCamera2;
    private Button btnCamera3;
    Bitmap bitmap1;
    private int  REQUEST_TAKE_PHOTO_PERMISSION=100;
    private int Ok;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

   View view=inflater.inflate(R.layout.camera_fragment,container, false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView=view.findViewById(R.id.imageView);
        //获取文件位置
        mFilepath= Environment.getExternalStorageDirectory().getPath();
        mFilepath=mFilepath+"/"+"temp.png";
         //获取缩略图
       btnCamera =view.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQ_1);
            }
        });
        //获取完整图
        btnCamera2 =view.findViewById(R.id.btn_camera2);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
        btnCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photoName;
                //给图片起个名字
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                photoName="Photo"+sdf.format(date)+".jpg";
                //保存图片
                File outputImage=new File(getActivity().getExternalCacheDir(),photoName.toString());
//                if (outputImage.exists()){
//                    outputImage.delete();
//                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(getActivity(),"com.example.myappsecond.fileprovider",outputImage);

                }
                else {
                    imageUri=Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        //调用系统相册
        btnCamera3=view.findViewById(R.id.btn_camera3);
        btnCamera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Intent.ACTION_PICK, null);
                intent5.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent5.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent5, 5);
            }
        });


    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null) {
            if (requestCode == REQ_1) {
                    Toast.makeText(getActivity(), "fuck..........", Toast.LENGTH_LONG).show();
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
            }
        }
        if (requestCode==5){
            Bundle bundle=data.getExtras();
           Uri uri=data.getData();
         //使用ImageLoader加载图片
            ImageLoader myload=ImageLoader.getInstance();
            myload.displayImage(uri.toString(), imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

          //Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_LONG).show();
            //Bitmap bitmap = (Bitmap) bundle.get("data");
            //imageView.setImageBitmap(bitmap);
        }
        //完整图的data是空，所以要放在外边
        if (requestCode==TAKE_PHOTO){
            Toast.makeText(getActivity(),"fuck..........",Toast.LENGTH_LONG).show();
             bitmap1= null;
            try {
                bitmap1 = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap1);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                Ok=111;
            } else {
                Toast.makeText(getActivity(), "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
