package com.rye.catcher.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dawn.zgstep.player.ui.codec.MediaCodecActivity;
import com.dawn.zgstep.player.ui.PlayerDetailActivity;
import com.rye.appupdater.UpdateActivityRx;
import com.rye.base.rxmvp.RxBaseActivity;
import com.rye.base.common.LanguageConstants;
import com.rye.base.utils.PopupEx;
import com.rye.base.utils.ToastHelper;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.ProjectListAdapter;
import com.rye.catcher.activity.presenter.ProjectPresenterRx;
import com.rye.catcher.agocode.beans.ProjectBean;

import com.rye.catcher.project.media.CameraMainActivity;


import com.rye.catcher.project.helpers.MultiThreadDown;
import com.rye.catcher.project.mvp.MvpActivity;
import com.rye.catcher.project.netdiagnosis.NetDiagnosisActivity;
import com.rye.catcher.project.services.ServiceMainActivity;
import com.rye.catcher.project.sqlite.DBActivity;
import com.rye.base.utils.SDHelper;
import com.rye.catcher.utils.SharedPreManager;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.rye.opengl.course_y.other.TestCustomSurfaceViewActivity;
import com.rye.opengl.course_y.other.TestTextureViewActivity;
import com.rye.router.runtime.Router;
//import com.rye.router_annotation.Route;
import com.yanzhenjie.permission.Permission;

import java.util.List;
import java.util.Set;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZZG on 2017/11/13.
 */
//@Route(
//        value = "router://page/project",
//        description = "各种Demo示例"
//)
public class ProjectMainActivityRx extends RxBaseActivity implements
        ProjectListAdapter.OnItemClickListener, FilesDemoActivity.DataListener {

    private static final String TAG2 = "LifeCycle-A";
    private static final String PROJECT_MAIN_URI = "rye://com.rye.catcher?type=6&from=Xsite";

    //改为recycleView
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private List<ProjectBean> dataList;
    MultiThreadDown multiThreadDown;

    @Override
    public int bindLayout() {
        return R.layout.project_main;
    }

    public static void startFromOutside(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setData(Uri.parse(PROJECT_MAIN_URI));
        context.startActivity(intent);
    }


    @Override
    public void initEvent() {
        ButterKnife.bind(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        //Mvp的目前操作，这个地方可以修改
        dataList = getPresenter(ProjectPresenterRx.class).getDataList(this);
        ProjectListAdapter adapter = new ProjectListAdapter(this, dataList);
        //子Item点击事件
        adapter.setOnItemClickListener(this);
        recycleView.setAdapter(adapter);
        FilesDemoActivity.setListener(this);
        printOutsideParams();
    }

    private void startService() {
        startActivity(new Intent(this, ServiceMainActivity.class));
    }

    private void printOutsideParams() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        Set<String> params = uri.getQueryParameterNames();
        String query = uri.getQuery();
        for (String par : params) {
            Log.i("Giao", "paramName:" + par);
        }
        Log.i("Giao", "query:" + query);
    }


    /**
     * 切语言
     *
     * @param view
     */
    private void changeLanguage(View view) {

        PopupEx popupEx = new PopupEx.Builder()
                .setContextView(this, R.layout.popup_change_language)
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

    private void change(String language) {
        if (SharedPreManager.getValue(LanguageConstants.VALUE)
                .equals(language)) {
            return;
        }
        SharedPreManager.saveValue(LanguageConstants.VALUE, language);
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
                ToastHelper.showToastShort(this, "已经被干掉了！");
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
                PermissionUtils.requestPermission(this, "需要相机权限！", false,
                        data -> {
                            startActivity(new Intent(this, CameraMainActivity.class));
                        }, 1, Permission.CAMERA);
                break;
            case "changeLanguage":
                // startActivity(new Intent(this, CameraActivityEx.class));
                changeLanguage(getContentView());
                break;
            case "testMulti":
                multiThreadDown = new MultiThreadDown(MultiThreadDown.path, SDHelper.getAppExternal() + "Zzx.mp4", 3);
                multiThreadDown.setOnDownLoadListener(new MultiThreadDown.DownLoadListener() {
                    @Override
                    public void getProgress(int progress) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(ProjectMainActivityRx.this, "下载完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                multiThreadDown.downLoad();
                break;
            case "testKotlin":
                ToastHelper.showToastShort(this, "已删除");
                break;
            case "ktCoroutine":
                startActivity(new Intent(this, KtCoroutineActivity.class));
                break;
            case "testUpdate":
                startActivity(new Intent(this, UpdateActivityRx.class));
                break;
            case "zgStep":
                Router.routeTo(this, "router://page/step?stepTitle=Hi~");
                break;
            case "diagnosis":
                NetDiagnosisActivity.start(this);
                break;
            case "player_demo":
                PlayerDetailActivity.start(this);
                break;
            case "opengl_main":
                //FirstOpenglActivity.start(this);
               // ShapeActivity.start(this); //OpenGL绘制形状
                //TestEGLHelperActivity.start(this); //测试EGLHelper
                //TestCustomSurfaceViewActivity.start(this);//测试自定义GLSurfaceView
                TestTextureViewActivity.start(this);//测试纹理
                break;
            case "mediaCodec":
                MediaCodecActivity.jump(this,MediaCodecActivity.TYPE_LIVE);
                break;
        }
    }


    @Override
    public void dataLoad(String content) {
        Log.i("RxZZG", "------DATA:" + content);
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
