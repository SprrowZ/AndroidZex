package com.rye.catcher.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.base.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiteProtectionActivity extends Activity {
    private static final String TAG = "SiteProtectionActivity";
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.container)
    LinearLayout container;
    private String currentTime;
    private SiteProtectFragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_protection);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }
    private void init(Bundle savedInstanceState){
        Log.i(TAG, "init: onCreate ");
        currentTime=DateUtils.getCurrentTime(DateUtils.FORMAT_DATETIME);
        if (savedInstanceState!=null) {//现场保护，迪斯卡
            currentTime = savedInstanceState.getString("currentTime");
        }
        textView.setText(currentTime);
        //加载Fragment
        FragmentManager fm=getFragmentManager();

        currentFragment= (SiteProtectFragment) fm.findFragmentByTag("areYouOk");
        if (currentFragment==null){//为空就加进来
            fm.beginTransaction()
                    .replace(R.id.container,new SiteProtectFragment(),"areYouOk")
                    .commit();
        }


    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ...");

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: ...");
        outState.putString("currentTime",currentTime);
        super.onSaveInstanceState(outState);
    }
 
}
