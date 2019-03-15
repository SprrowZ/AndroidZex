package com.rye.catcher.activity.fragment;

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
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rye.catcher.R;

import com.rye.catcher.utils.ExtraUtil.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ZZG on 2017/10/18
 */

public class CameraFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_camera2)
    Button btnCamera2;
    @BindView(R.id.btn_camera3)
    Button btnCamera3;
    @BindView(R.id.imageView)
    ImageView imageView;
    Unbinder unbinder;
    private static int REQ_1 = 1;
    //whole picture
    public static final int TAKE_PHOTO = 2;
    private Uri imageUri;
    private View view;
    private String mFilepath;

    Bitmap bitmap1;
    private int REQUEST_TAKE_PHOTO_PERMISSION = 100;
    private int Ok;

    @Override
    protected int getLayoutResId() {
        return R.layout.camera_fragment;
    }

    @Override
    protected void initData() {
        imageView = view.findViewById(R.id.imageView);
        //获取文件位置
        mFilepath = Environment.getExternalStorageDirectory().getPath();
        mFilepath = mFilepath + "/" + "temp.png";
    }

    private void getThumbnail(){//获取缩略图
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ_1);
    }
    private void getFullImage(){
        String photoName;
        //给图片起个名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        photoName = "Photo" + sdf.format(date) + ".jpg";
        //保存图片
        File outputImage = new File(getActivity().getExternalCacheDir(), photoName.toString());
//                if (outputImage.exists()){
//                    outputImage.delete();
//                }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getActivity(), Constant.FILE_PROVIDER, outputImage);

        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }
    private void callAlbum(){
        Intent intent5 = new Intent();
        intent5.setType("image/*");
        intent5.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent5, 5);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQ_1) {
                Toast.makeText(getActivity(), "fuck..........", Toast.LENGTH_LONG).show();
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
        if (requestCode == 5) {
            Bundle bundle = data.getExtras();
            Uri uri = data.getData();
            Glide.with(getActivity()).load(uri).into(imageView);
        }
        //完整图的data是空，所以要放在外边
        if (requestCode == TAKE_PHOTO) {
            Toast.makeText(getActivity(), "fuck..........", Toast.LENGTH_LONG).show();
            bitmap1 = null;
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
                Ok = 111;
            } else {
                Toast.makeText(getActivity(), "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_camera, R.id.btn_camera2, R.id.btn_camera3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                getThumbnail();
                break;
            case R.id.btn_camera2:
                getFullImage();
                break;
            case R.id.btn_camera3:
                callAlbum();//private ，不知道会不会出问题
                break;
        }
    }
}
