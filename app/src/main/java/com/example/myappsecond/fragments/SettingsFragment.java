package com.example.myappsecond.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.myappsecond.CameraActivity;
import com.example.myappsecond.FirstActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.SecondActivity;
import com.example.myappsecond.animations.AnimationMain;
import com.example.myappsecond.animations.ShapeTest;
import com.example.myappsecond.customViews.CustomMain;
import com.example.myappsecond.okHttps.OkHttpMain;
import com.example.myappsecond.project.DrawableTest.Drawable_Main;
import com.example.myappsecond.project.MenuActivity;
import com.example.myappsecond.project.ProjectMain;
import com.example.myappsecond.project.ReviewTest;
import com.example.myappsecond.project.Services.ServiceMainActivity;
import com.example.myappsecond.review.KeepJava;
import com.example.myappsecond.utils.PermissionUtil;
import com.example.myappsecond.utils.PermissionsUtil;
import com.example.myappsecond.utils.ToastUtils;

/**
 * Created by zzg on 2017/10/10.
 */

public class SettingsFragment extends Fragment {
    private ProgressBar progressBar;
    private Button btn1;
    private View view;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button cameraOne;
    private Button cameraTwo;
    private Button review;
    private Button someting_new;
    private Button Animation;
    private Button drawable;
    private Button custom;
    private Button project;
    private Button service;
    private EditText editText;
    PermissionUtil permissionUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.tab04,container,false);
       // progressBar= view.findViewById(R.id.progressBar);
        //btn1=view.findViewById(R.id.btn1);

        this.view=view;
        permissionUtil= new PermissionUtil(getActivity());
      return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//dialog逻辑
        btn1=view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View view) {
//        MyDialog dialog=new MyDialog(getActivity());
//        dialog.setCancelable(false);
//        dialog.show();
//        Window dialogWindow = dialog.getWindow();
//        WindowManager m = getActivity().getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight()* 0.8); // 高度设置为屏幕的0.6，根据实际情况调整
//        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
//        dialogWindow.setAttributes(p);
//        WindowManager manager = getActivity().getWindowManager();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        manager.getDefaultDisplay().getMetrics(outMetrics);
//        int width = outMetrics.widthPixels;
//        int height = outMetrics.heightPixels;
//        WindowManager.LayoutParams p = dialogWindow.getAttributes();
           startActivity(new Intent(getActivity(), OkHttpMain.class));

    }
});
        //测试finish方法
        btn2=view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent4=new Intent(getActivity(),KeepJava.class);
                startActivity(intent4);
            }
        });






        /**
         * Timer
         */
//        Timer timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.GONE);
//            }
//        },3000);


        progressBar= view.findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if (getActivity()==null){
                        return ;
                    }else{

                     getActivity().runOnUiThread(new Runnable() {
                         @Override
                       public void run() {
                             progressBar.setVisibility(View.GONE);
                         }
                    });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //跳转到子Activity中
        btn3=view.findViewById(R.id.btn3);
btn3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1=new Intent(getActivity(),FirstActivity.class);
        startActivity(intent1);
    }
});
        //
        btn4=view.findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getActivity(),SecondActivity.class);
                startActivity(intent2);
            }
        });
//camera Test
        cameraOne=view.findViewById(R.id.cameraOne);
        cameraOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authority();
                Intent intent3=new Intent(getActivity(),CameraActivity.class);
                intent3.putExtra("info","fuck...");
                startActivity(intent3);
            }
        });
//Menu Test
        cameraTwo=view.findViewById(R.id.cameraTwo);
        cameraTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(getActivity(),MenuActivity.class);
                startActivity(intent4);
            }
        });
//Review
        review=view.findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(getActivity(), ReviewTest.class);
                startActivity(intent5);
            }
        });

     someting_new=view.findViewById(R.id.someting_new);
        someting_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent6=new Intent(getActivity(), ShapeTest.class);
                startActivity(intent6);
            }
        });
Animation=view.findViewById(R.id.animation);
        Animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7=new Intent(getActivity(), AnimationMain.class);
                startActivity(intent7);
               getActivity().overridePendingTransition(R.anim.rotate,R.anim.alpha);
        }
        });
 drawable=view.findViewById(R.id.drawable);
        drawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8=new Intent(getActivity(), Drawable_Main.class);
                startActivity(intent8);
            }
        });

custom=view.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int height= MeasureUtil.getHeight(getActivity());
               // Toast.makeText(getActivity(),String.valueOf(height),Toast.LENGTH_LONG).show();
                Intent intent9=new Intent(getActivity(), CustomMain.class);
                startActivity(intent9);
            }
        });
project=view.findViewById(R.id.project);
        project.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent10=new Intent(getActivity(), ProjectMain.class);
                        startActivity(intent10);
                    }
                }
        );
editText=view.findViewById(R.id.editText);
service=view.findViewById(R.id.service);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11=new Intent(getActivity(), ServiceMainActivity.class);
                startActivity(intent11);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void authority() {
        //申请权限
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsUtil.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        PermissionsUtil.IPermissionsResult permissionsResult=new PermissionsUtil.IPermissionsResult() {
            @Override
            public void passPermissons() {
                ToastUtils.shortMsg("权限申请成功");
            }

            @Override
            public void forbitPermissons() {
                ToastUtils.shortMsg("权限申请失败");
            }
        };
    }


    @Override
    public void onDestroy() {
       // Toast.makeText(getActivity(),"destroy",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


}
