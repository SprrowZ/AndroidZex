package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.SlideActivity;
import com.rye.catcher.project.catcher.DelayHandleUtil;
import com.rye.catcher.project.dialog.TopDialog;
import com.rye.catcher.project.services.ServiceMainActivity;
import com.rye.catcher.project.SQLiteZ.DBActivity;
import com.rye.catcher.utils.MeasureUtil;
import com.rye.catcher.utils.ToastUtils;

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
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_topbtntitle)
    TextView tvTopbtntitle;
    @BindView(R.id.back_parent)
    LinearLayout backParent;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.btn_function_back)
    LinearLayout btnFunctionBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.down_list_icon)
    ImageView downListIcon;
    @BindView(R.id.box_place)
    LinearLayout boxPlace;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.rl_top_bar)
    RelativeLayout rlTopBar;
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    //测试Handler
    private Handler zHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    runOnUiThread(() -> {
                        ToastUtils.shortMsg("弹出框。。。");
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        ButterKnife.bind(this);
        setBarTitle("Mix");

        MeasureUtil.setLeftScale(this, search, searchBar, R.mipmap.icon_title_bar_edit_search);

        Log.i(TAG, "onCreate: ...");
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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final Date date = new Date();
                runOnUiThread(() -> {
                    sliding.setText(sdf.format(date));
                });
            }
        }, 0, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setClock();
    }

    @OnClick({R.id.service, R.id.intents, R.id.dbtest, R.id.file,
            R.id.aidl, R.id.drawable, R.id.shape, R.id.slidingDemo,
            R.id.search_bar, R.id.recyclerView, R.id.dialogs, R.id.coor,
            R.id.viewDrag, R.id.batchLoading, R.id.siteProtection,
            R.id.blueTooth,R.id.blueTooth2})
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
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ...");
    }
}
