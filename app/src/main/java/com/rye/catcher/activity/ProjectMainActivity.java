package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.project.Ademos.MultiThreadDown;
import com.rye.catcher.project.catcher.DelayHandleUtil;
import com.rye.catcher.project.ctmviews.takephoto.CameraActivityEx;
import com.rye.catcher.project.ctmviews.takephoto.TestPhotoActivity;
import com.rye.catcher.project.dialog.TopDialog;
import com.rye.catcher.project.Ademos.mvp.MvpActivity;
import com.rye.catcher.project.services.ServiceMainActivity;
import com.rye.catcher.project.SQLiteZ.DBActivity;
import com.rye.catcher.utils.MeasureUtil;
import com.rye.catcher.utils.SDHelper;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2017/11/13.
 */

public class ProjectMainActivity extends BaseActivity {

    private static final String TAG = "ProjectMainActivity";
    private static final String TAG2="LifeCycle-A";


    @BindView(R.id.search_bar)
    TextView searchBar;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.service)
    Button popup;
    @BindView(R.id.intents)
    Button sliding;
    @BindView(R.id.dbtest)
    Button dbtest;
    @BindView(R.id.file)
    Button file;
    @BindView(R.id.aidl)
    Button aidl;
    @BindView(R.id.drawable)
    Button drawable;
    @BindView(R.id.shape)
    Button shape;
    @BindView(R.id.slidingDemo)
    Button slidingDemo;
    @BindView(R.id.recyclerView)
    Button recycleView;
    @BindView(R.id.dialogs)
    Button dialogs;
    @BindView(R.id.coor)
    Button coor;
    @BindView(R.id.viewDrag)
    Button viewDrag;
    @BindView(R.id.batchLoading)
    Button batchLoading;
    LinearLayout parent;
    @BindView(R.id.siteProtection)
    Button siteProtection;
    @BindView(R.id.blueTooth)
    Button blueTooth;
    @BindView(R.id.blueTooth2)
    Button blueTooth2;
    @BindView(R.id.mvpDemo)
    Button mvpDemo;
    @BindView(R.id.takePhoto)
    Button takePhoto;
    @BindView(R.id.takePhotoEx)
    Button takePhotoEx;
    @BindView(R.id.multiThread)
    Button multiThread;
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private Timer timer;
    private TimerTask timerTask;

    MultiThreadDown multiThreadDown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        ButterKnife.bind(this);
        setBarTitle("Mix");
        MeasureUtil.setLeftScale(this, search, searchBar, R.mipmap.icon_title_bar_edit_search);
        Log.i(TAG2, "onCreate: ...");
    }


    private void showDialog() {
        TopDialog topDialog =
                new TopDialog(ProjectMainActivity.this, R.layout.project_dialog_first);
        parent = (LinearLayout) topDialog.findViewById(R.id.parent);
        //设置drawleft的大小

        topDialog.getWindow().setBackgroundDrawable(null);
    }

    private void startService() {
        startActivityByAlpha(new Intent(this, ServiceMainActivity.class));
    }

    private void setClock() {
        timer=new Timer();
        timerTask=new TimerTask(){
            @Override
            public void run() {
                final Date date = new Date();
                runOnUiThread(() -> {
                    sliding.setText(sdf.format(date));
                });
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG2, "onResume: ....");
        setClock();
    }

    @OnClick({R.id.service, R.id.intents, R.id.dbtest, R.id.file,
            R.id.aidl, R.id.drawable, R.id.shape, R.id.slidingDemo,
            R.id.search_bar, R.id.recyclerView, R.id.dialogs, R.id.coor,
            R.id.viewDrag, R.id.batchLoading, R.id.siteProtection,
            R.id.blueTooth,R.id.blueTooth2,R.id.mvpDemo,R.id.takePhoto,
            R.id.takePhotoEx,R.id.multiThread,R.id.kotlin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                showDialog();
                break;
            case R.id.service:
                startService();
                break;
            case R.id.intents:
                startActivity(new Intent(ProjectMainActivity.this,
                        IntentsActivity.class));
                break;
            case R.id.dbtest:
                startActivity(new Intent(ProjectMainActivity.this,
                        DBActivity.class));
                break;
            case R.id.file:
                startActivity(new Intent(ProjectMainActivity.this,
                        FilesDemoActivity.class));
                break;
            case R.id.aidl:
                startActivity(new Intent(ProjectMainActivity.this,
                        AIDLActivity.class));
                break;
            case R.id.drawable:
                Intent intent8 = new Intent(ProjectMainActivity.this,
                        DrawableMainActivity.class);
                startActivity(intent8);
                break;
            case R.id.shape:
                testHandler();
                break;
            case R.id.slidingDemo:
                Intent intent11 = new Intent(ProjectMainActivity.this,
                        TestCoorActivity.class);
                startActivity(intent11);
                break;
            case R.id.recyclerView:
                Intent intent12 = new Intent(ProjectMainActivity.this,
                        RecycleDemoActivity.class);
                startActivity(intent12);
                break;
            case R.id.dialogs:
                startActivity(new Intent(this, CommonDialogActivity.class));
                break;
            case R.id.coor:
                startActivity(new Intent(this, CoordinatorActivity.class));
                break;
            case R.id.viewDrag:
                startActivity(new Intent(this, SlideActivity.class));
                break;
            case R.id.batchLoading:
                startActivity(new Intent(this, BatchLoadingActivity.class));
                break;
            case R.id.siteProtection:
                startActivity(new Intent(this, SiteProtectionActivity.class));
                break;
            case R.id.blueTooth:
                startActivity(new Intent(this, BlueToothActivity.class));
                break;
            case R.id.blueTooth2:
                startActivity(new Intent(this,BLEActivity.class));
                break;
            case R.id.mvpDemo:
                startActivity(new Intent(this, MvpActivity.class));
                break;
            case R.id.takePhoto:
                PermissionUtils.requestPermission(this,"需要相机权限！",false,
                        data -> {
                            startActivity(new Intent(this, TestPhotoActivity.class));
                        },1, Permission.CAMERA);
                break;
            case R.id.takePhotoEx:
                startActivity(new Intent(this, CameraActivityEx.class));
                break;
            case R.id.multiThread:
             multiThreadDown=new MultiThreadDown(MultiThreadDown.path, SDHelper.getAppExternal()+"Zzx.mp4",3);
             multiThreadDown.setOnDownLoadListener(new MultiThreadDown.DownLoadListener() {
                 @Override
                 public void getProgress(int progress) {

                 }

                 @Override
                 public void onComplete() {
                     Toast.makeText(ProjectMainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure() {

                 }
             });
             multiThreadDown.downLoad();
                break;
            case R.id.kotlin:
                startActivity(new Intent(this,KotlinActivity.class));
                break;
        }
    }

    //
    private void testHandler() {
        Log.i(TAG, "testHandler: ...");
        DelayHandleUtil.setDelay("zzg", 0L, 2000, new DelayHandleUtil.HandleListener() {
            @Override
            public void ReachTheTime() {
                Log.i(TAG, "testHandler222: ...");
            }
        });
    }

    @Override
    protected void onRestart() {
        Log.i(TAG2, "onRestart: ...");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG2, "onPause: ...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG2, "onStop: ...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG2, "onDestroy: ...");
        if (timer!=null){
            timer.cancel();
        }
        if (timerTask!=null){
            timerTask.cancel();
        }
    }
}
