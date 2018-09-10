package com.example.myappsecond.activity;

import android.content.Intent;
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
import com.example.myappsecond.project.dialog.CommonDialogActivity;
import com.example.myappsecond.project.sqlLiteDatabase.DBActivity;
import com.example.myappsecond.utils.MeasureUtil;

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
        CommonDialogActivity topDialog =
                new CommonDialogActivity(ProjectMainActivity.this, R.layout.project_dialog_first);


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
            R.id.menu, R.id.drawable, R.id.shape, R.id.service,R.id.search_bar})
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
        }
    }
}
