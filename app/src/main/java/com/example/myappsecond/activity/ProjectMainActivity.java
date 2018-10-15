package com.example.myappsecond.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.project.MenuActivity;
import com.example.myappsecond.project.SlidingConflict;
import com.example.myappsecond.project.animations.AnimShapeActivity;
import com.example.myappsecond.project.dialog.TopDialog;
import com.example.myappsecond.project.sqlDemo.DBActivity;
import com.example.myappsecond.utils.MeasureUtil;
import com.example.myappsecond.utils.permission.PermissionUtils;
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
    @BindView(R.id.popup)
    Button popup;
    @BindView(R.id.sliding)
    Button sliding;
    @BindView(R.id.dbtest)
    Button dbtest;
    @BindView(R.id.file)
    Button file;
    @BindView(R.id.menu)
    Button menu;
    @BindView(R.id.drawable)
    Button drawable;
    @BindView(R.id.shape)
    Button shape;
    @BindView(R.id.service)
    Button service;
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
    LinearLayout parent;
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);
        ButterKnife.bind(this);
        setBarTitle("Mix");

        MeasureUtil.setLeftScale(this, search, searchBar, R.mipmap.icon_title_bar_edit_search);
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
    private void showPop(){
        View view1 = View.inflate(ProjectMainActivity.this, R.layout.popupwindow, null);
        PopupWindow pop = new PopupWindow(ProjectMainActivity.this);
        pop.setContentView(view1);
        pop.setHeight(ViewPager.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        // pop.setOutsideTouchable(false);
        // ObjectAnimator  animator=ObjectAnimator.ofFloat(view1,"translationY",0F,100F);
        // animator.setDuration(100).start();
        pop.showAsDropDown(popup);
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

    @OnClick({R.id.popup, R.id.sliding, R.id.dbtest, R.id.file,
            R.id.menu, R.id.drawable, R.id.shape, R.id.service,
            R.id.search_bar,R.id.recyclerView,R.id.callPhone,
            R.id.sendSMS,R.id.sendEmail,R.id.dialogs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                showDialog();
                break;
            case R.id.popup:
                showPop();
                break;
            case R.id.sliding:
                startActivity(new Intent(ProjectMainActivity.this, SlidingConflict.class));
                break;
            case R.id.dbtest:
                startActivity(new Intent(ProjectMainActivity.this, DBActivity.class));
                break;
            case R.id.file:
                startActivity(new Intent(ProjectMainActivity.this, FileStorageTest.class));
                break;
            case R.id.menu:
                startActivity(new Intent(ProjectMainActivity.this, MenuActivity.class));
                break;
            case R.id.drawable:
                Intent intent8 = new Intent(ProjectMainActivity.this, DrawableMainActivity.class);
                startActivity(intent8);
                break;
            case R.id.shape:
                Intent intent6 = new Intent(ProjectMainActivity.this, AnimShapeActivity.class);
                startActivity(intent6);
                break;
            case R.id.service:
                Intent intent11 = new Intent(ProjectMainActivity.this, TestCoorActivity.class);
                startActivity(intent11);
                break;
            case R.id.recyclerView:
                Intent intent12= new Intent(ProjectMainActivity.this,RecycleDemoActivity.class);
                startActivity(intent12);
                break;
            case R.id.callPhone:
                PermissionUtils.requestPermission(this,"权限申请失败，请到手机设置中设置",false,
                        data -> {
                        //打电话
                        callPhone();
                        },0, Permission.CALL_PHONE);
                break;
            case R.id.sendSMS:
                PermissionUtils.requestPermission(this,"权限申请失败，请到手机设置中设置",false,
                        data -> {
                        sendSMS();
                        },0, Permission.CALL_PHONE);
                break;
            case R.id.sendEmail:
                PermissionUtils.requestPermission(this,"权限申请失败，请到手机设置中设置",false,
                        data -> {
                        sendEmail();
                        },0, Permission.CALL_PHONE);
                break;
            case R.id.dialogs:
                startActivity(new Intent(this,CommonDialogActivity.class));
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
}
