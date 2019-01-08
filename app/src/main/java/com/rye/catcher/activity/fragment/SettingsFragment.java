package com.rye.catcher.activity.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.rye.catcher.activity.CameraActivity;
import com.rye.catcher.FirstActivity;
import com.rye.catcher.R;
import com.rye.catcher.SecondActivity;
import com.rye.catcher.activity.ORRActivity;
import com.rye.catcher.activity.ProjectMainActivity;
import com.rye.catcher.activity.ctmactivity.CtmMainActivity;
import com.rye.catcher.project.ReviewTest;
import com.rye.catcher.project.animations.AnimMainActivity;
import com.rye.catcher.project.review.KeepJava;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.ToastUtils;

/**
 * Created by zzg on 2017/10/10.
 */

public class SettingsFragment extends BaseFragment {
    private ProgressBar progressBar;
    private Button btn1;
    private View view;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button cameraOne;

    private Button review;
    private Button someting_new;
    private Button Animation;

    private Button custom;
    private Button project;

    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.tab04,container,false);
        this.view=view;

      return  view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//dialog逻辑
        setBarTitle("苍海");
        btn1=view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View view) {
           startActivity(new Intent(getActivity(), ORRActivity.class));
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

//Review
        review=view.findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(getActivity(), ReviewTest.class);
                startActivity(intent5);
            }
        });


Animation=view.findViewById(R.id.animation);
        Animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7=new Intent(getActivity(), AnimMainActivity.class);
                startActivity(intent7);
               getActivity().overridePendingTransition(R.anim.rotate,R.anim.alpha);
        }
        });
custom=view.findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int height= MeasureUtil.getHeight(getActivity());
               // Toast.makeText(getActivity(),String.valueOf(height),Toast.LENGTH_LONG).show();
                Intent intent9=new Intent(getActivity(), CtmMainActivity.class);
                startActivity(intent9);
            }
        });
project=view.findViewById(R.id.project);
        project.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent10=new Intent(getActivity(), ProjectMainActivity.class);
                        startActivity(intent10);
                    }
                }
        );
           editText=view.findViewById(R.id.editText);



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
