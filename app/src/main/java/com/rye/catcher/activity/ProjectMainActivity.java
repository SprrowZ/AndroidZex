package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rye.base.common.LanguageConstants;
import com.rye.base.utils.PopupEx;
import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.project.Ademos.MultiThreadDown;
import com.rye.catcher.project.catcher.DelayHandleUtil;
import com.rye.catcher.project.ctmviews.takephoto.CameraActivityEx;
import com.rye.catcher.project.ctmviews.takephoto.TestCameraActivity;

import com.rye.catcher.project.dialog.TopDialog;
import com.rye.catcher.project.Ademos.mvp.MvpActivity;
import com.rye.catcher.project.services.ServiceMainActivity;
import com.rye.catcher.project.SQLiteZ.DBActivity;
import com.rye.catcher.utils.MeasureUtil;
import com.rye.catcher.utils.SDHelper;
import com.rye.catcher.utils.SharedPreManager;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by ZZG on 2017/11/13.
 */

public class ProjectMainActivity extends BaseActivity {

    private static final String TAG2="LifeCycle-A";

    LinearLayout parent;
    @BindView(R.id.search_bar)
    TextView searchBar;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.intents)
    Button sliding;
    @BindView(R.id.service)
    Button popup;
    @BindView(R.id.dbtest)
    Button dbtest;
    @BindView(R.id.file)
    Button file;
    @BindView(R.id.aidl)
    Button aidl;
    @BindView(R.id.language)
    Button language;
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
    @BindView(R.id.kotlin)
    Button kotlin;
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private Timer timer;
    private TimerTask timerTask;

    MultiThreadDown multiThreadDown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        ButterKnife.bind(this);
        setBarTitle("-雨夜思书-");
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
    @Optional
    @OnClick({R.id.service, R.id.intents, R.id.dbtest, R.id.file,
            R.id.aidl, R.id.language, R.id.slidingDemo,
            R.id.search_bar, R.id.recyclerView, R.id.dialogs, R.id.coor,
            R.id.viewDrag, R.id.batchLoading, R.id.siteProtection,
            R.id.blueTooth,R.id.blueTooth2,R.id.mvpDemo,R.id.takePhoto,
            R.id.takePhotoEx,R.id.multiThread,R.id.kotlin,R.id.kotlin_coroutine})
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

            case R.id.language://切换语言

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
                            startActivity(new Intent(this, TestCameraActivity.class));
                        },1, Permission.CAMERA);
                break;
            case R.id.takePhotoEx:
               // startActivity(new Intent(this, CameraActivityEx.class));
               changeLanguage(view);
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
            case R.id.kotlin_coroutine:
                startActivity(new Intent(this,KtCoroutineActivity.class));
                break;
        }
    }
    /**
     * 切语言
     * @param view
     */
    private void changeLanguage(View view){

        PopupEx popupEx= new PopupEx.Builder()
                .setContextView(this,R.layout.popup_change_language)
                .setDim(0.7F)
                .setWidth(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setParentView(view, Gravity.CENTER)
                .create();
        popupEx.getView().findViewById(R.id.chinese).setOnClickListener(v -> {
            popupEx.dismiss();
            change(LanguageConstants.SIMPLIFIED_CHINESE);
        });
        popupEx.getView().findViewById(R.id.english).setOnClickListener(v -> {
            popupEx.dismiss();
            change(LanguageConstants.ENGLISH);

        });

    }
    private void change(String language){
        if (SharedPreManager.getValue(LanguageConstants.VALUE)
                .equals(language)){
            return;
        }
        SharedPreManager.saveValue(LanguageConstants.VALUE,language);
        setResult(RESULT_OK);
        finish();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG2, "onDestroy");
        if (timer!=null){
            timer.cancel();
        }
        if (timerTask!=null){
            timerTask.cancel();
        }
    }
}
