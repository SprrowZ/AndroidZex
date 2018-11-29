package com.rye.catcher.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.rye.catcher.project.animations.AnimShapeActivity;
import com.rye.catcher.project.dialog.TopDialog;
import com.rye.catcher.project.services.ServiceMainActivity;
import com.rye.catcher.project.sqlDemo.DBActivity;
import com.rye.catcher.utils.MeasureUtil;
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
    private static boolean flag=true;
    private static  final  String TAG="ProjectMainActivity";
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
    @BindView(R.id.sliding)
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
    @BindView(R.id.callPhone)
    Button callPhone;
    @BindView(R.id.sendSMS)
    Button sendSMS;
    @BindView(R.id.sendEmail)
    Button sendEmail;
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

    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        ButterKnife.bind(this);
        setBarTitle("Mix");

        MeasureUtil.setLeftScale(this, search, searchBar, R.mipmap.icon_title_bar_edit_search);

        Log.i(TAG, "onCreate: ...");
    }


    private void showDialog(){
        TopDialog topDialog =
                new TopDialog(ProjectMainActivity.this, R.layout.project_dialog_first);


        parent = (LinearLayout) topDialog.findViewById(R.id.parent);
        //设置drawleft的大小

        topDialog.getWindow().setBackgroundDrawable(null);
        //editText绑定键盘
//                son.setFocusable(true);
//                son.setFocusableInTouchMode(true);
//                son.requestFocus();
//                InputMethodManager inputManager = (InputMethodManager)son.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(son, 0);

    }
    private void startService(){
        startActivityByAlpha(new Intent(this, ServiceMainActivity.class));
    }

    private void setClock() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final Date date = new Date();
                runOnUiThread(()->{
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

    @OnClick({R.id.service, R.id.sliding, R.id.dbtest, R.id.file,
            R.id.aidl, R.id.drawable, R.id.shape, R.id.slidingDemo,
            R.id.search_bar,R.id.recyclerView,R.id.callPhone,
            R.id.sendSMS,R.id.sendEmail,R.id.dialogs,R.id.coor,
            R.id.viewDrag,R.id.batchLoading,R.id.siteProtection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                showDialog();
                break;
            case R.id.service:
                startService();
                break;
            case R.id.sliding:
                startActivity(new Intent(ProjectMainActivity.this,
                        LivePreservationActivity.class));
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
                Intent intent6 = new Intent(ProjectMainActivity.this,
                        AnimShapeActivity.class);
                startActivity(intent6);
                break;
            case R.id.slidingDemo:
                Intent intent11 = new Intent(ProjectMainActivity.this,
                        TestCoorActivity.class);
                startActivity(intent11);
                break;
            case R.id.recyclerView:
                Intent intent12= new Intent(ProjectMainActivity.this,
                        RecycleDemoActivity.class);
                startActivity(intent12);
                break;
            case R.id.callPhone:
                PermissionUtils.requestPermission(this,"权限申请失败，请到手机设置中设置"
                        ,false,
                        data -> {
                        //打电话
                        callPhone();
                        },0, Permission.CALL_PHONE);
                break;
            case R.id.sendSMS:
                PermissionUtils.requestPermission(this,"权限申请失败，请到手机设置中设置",
                        false,
                        data -> {
                        sendSMS();
                        },0, Permission.CALL_PHONE);
                break;
            case R.id.sendEmail:
                PermissionUtils.requestPermission(this,"权限申请失败，请到手机设置中设置",
                        false,
                        data -> {
                        sendEmail();
                        },0, Permission.CALL_PHONE);
                break;
            case R.id.dialogs:
                startActivity(new Intent(this,CommonDialogActivity.class));
                break;
            case R.id.coor:
                startActivity(new Intent(this,CoordinatorActivity.class));
                break;
            case R.id.viewDrag:
                startActivity(new Intent(this, SlideActivity.class));
                break;
            case R.id.batchLoading:
                startActivity(new Intent(this,BatchLoadingActivity.class));
            case R.id.siteProtection:
                startActivity(new Intent(this,SiteProtectionActivity.class));
        }
    }

    private void callPhone(){
        Intent intent2=new Intent(Intent.ACTION_DIAL);
        Uri datas;
        if (flag){
           datas=Uri.parse("tel:");
        }else {
            datas=Uri.parse("tel:"+"13201383679");
        }
        flag=false;
        intent2.setData(datas);
        startActivity(intent2);
    }
    private void sendSMS(){
        Uri smsToUri;
     if (flag){
         smsToUri=Uri.parse("smsto:"+"13201383679");
     }else {
         smsToUri=Uri.parse("smsto:");
     }
     flag=true;
     Intent intent=new Intent(Intent.ACTION_SENDTO,smsToUri);
     intent.putExtra("sms_body","");
     startActivity(intent);
    }
    private void sendEmail(){
      Intent data=new Intent(Intent.ACTION_SENDTO);
      data.setData(Uri.parse("mailto:1804124963@qq.com"));
      data.putExtra(Intent.EXTRA_SUBJECT," ");
      data.putExtra(Intent.EXTRA_TEXT,"内容");
      startActivity(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ...");
    }
}
