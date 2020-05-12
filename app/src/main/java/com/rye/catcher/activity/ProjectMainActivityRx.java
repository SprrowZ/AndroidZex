package com.rye.catcher.activity;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.rye.appupdater.UpdateActivityRx;
import com.rye.base.rxmvp.RxBaseActivity;
import com.rye.base.common.LanguageConstants;
import com.rye.base.utils.PopupEx;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.ProjectListAdapter;
import com.rye.catcher.activity.presenter.ProjectPresenterRx;
import com.rye.catcher.beans.ProjectBean;

import com.rye.catcher.project.ctmviews.takephoto.TestCameraActivity;


import com.rye.catcher.project.helpers.MultiThreadDown;
import com.rye.catcher.project.mvp.MvpActivity;
import com.rye.catcher.project.services.ServiceMainActivity;
import com.rye.catcher.project.SQLiteZ.DBActivity;
import com.rye.catcher.utils.SDHelper;
import com.rye.catcher.utils.SharedPreManager;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import java.util.List;


 import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZZG on 2017/11/13.
 */

public class ProjectMainActivityRx extends RxBaseActivity implements
        ProjectListAdapter.OnItemClickListener,ReflectActivity.DataListener{

    private static final String TAG2="LifeCycle-A";
    //改为recycleView
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private List<ProjectBean> dataList;
    MultiThreadDown multiThreadDown;

    @Override
    public int bindLayout() {
        return R.layout.project_main ;
    }

    @Override
    public void initEvent() {
        ButterKnife.bind(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        //Mvp的目前操作，这个地方可以修改
        dataList=getPresenter(ProjectPresenterRx.class).getDataList(this);
        ProjectListAdapter adapter=new ProjectListAdapter(this,dataList);
        //子Item点击事件
        adapter.setOnItemClickListener(this);
        recycleView.setAdapter(adapter);
    }

    private void startService() {
        startActivity(new Intent(this, ServiceMainActivity.class));
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
    public void onItemClick(String action) {
        switch (action) {
            case "testService":
                startService();
                break;
            case "testIntents":
                startActivity(new Intent(ProjectMainActivityRx.this,
                        IntentsActivity.class));
                break;
            case "testSQLite":
                startActivity(new Intent(ProjectMainActivityRx.this,
                        DBActivity.class));
                break;
            case "testFile":
                startActivity(new Intent(ProjectMainActivityRx.this,
                        FilesDemoActivity.class));
                break;
            case "testAIDL":
                startActivity(new Intent(ProjectMainActivityRx.this,
                        AIDLActivity.class));
                break;
            case "testSlide":
                Intent intent11 = new Intent(ProjectMainActivityRx.this,
                        ReflectActivity.class);
                startActivity(intent11);
                break;
            case "testRecycle":
                Intent intent12 = new Intent(ProjectMainActivityRx.this,
                        RecycleDemoActivity.class);
                startActivity(intent12);
                break;
            case "testDialogEx":
                startActivity(new Intent(this, CommonDialogActivity.class));
                break;
            case "testCoor":
                startActivity(new Intent(this, CoordinatorActivity.class));
                break;
            case "batchLoading":
                startActivity(new Intent(this, BatchLoadingActivity.class));
                break;
            case "testPreserve":
                startActivity(new Intent(this, SiteProtectionActivity.class));
                break;
            case "testBluetooth":
                startActivity(new Intent(this, BlueToothActivity.class));
                break;
            case "testMvp":
                startActivity(new Intent(this, MvpActivity.class));
                break;
            case "takePhoto":
                PermissionUtils.requestPermission(this,"需要相机权限！",false,
                        data -> {
                            startActivity(new Intent(this, TestCameraActivity.class));
                        },1, Permission.CAMERA);
                break;
            case "changeLanguage":
                // startActivity(new Intent(this, CameraActivityEx.class));
                changeLanguage(getContentView());
                break;
            case "testMulti":
                multiThreadDown=new MultiThreadDown(MultiThreadDown.path, SDHelper.getAppExternal()+"Zzx.mp4",3);
                multiThreadDown.setOnDownLoadListener(new MultiThreadDown.DownLoadListener() {
                    @Override
                    public void getProgress(int progress) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(ProjectMainActivityRx.this,"下载完成",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                multiThreadDown.downLoad();
                break;
            case "testKotlin":
                startActivity(new Intent(this,KotlinActivity.class));
                break;
            case "ktCoroutine":
                startActivity(new Intent(this,KtCoroutineActivity.class));
                break;
            case "testUpdate":
                startActivity(new Intent(this, UpdateActivityRx.class));
                break;
        }
    }


    @Override
    public void dataLoad(String content) {
        Log.i("ZZG","------DATA:"+content);
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
