package com.rye.catcher.activity.ctmactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.CtmViewFragment;
import com.rye.catcher.project.catcher.eventbus.MessageEvent;
import com.rye.catcher.project.ctmviews.Mypractice.RotateFirst;
import com.rye.catcher.project.ctmviews.ProgressFragment;
import com.rye.catcher.project.ctmviews.Shadows;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmMain2Activity extends BaseActivity {
    private RelativeLayout bottomContainers;
    private Context mContext;
    private CtmViewFragment fragment = new CtmViewFragment();
    private broadcastReceiver receiver = new broadcastReceiver();
    private ProgressFragment progressFragment=new ProgressFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_main2);
        init();
    }

    private void init() {
        //注册广播，用来加载Progress
        EventBus.getDefault().register(this);
        bottomContainers = findViewById(R.id.bottomContainers);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.custom.fragment.activity");
        registerReceiver(receiver, intentFilter);
    }
  @Subscribe (threadMode = ThreadMode.MAIN,sticky = true)
  public void ProgressEvent(MessageEvent event){
        //进行处理
      if (("insertProgress").equals(event.getMessage())){
          bottomContainers.removeAllViews();
          if (progressFragment.isAdded()){
              getSupportFragmentManager().beginTransaction().show(progressFragment).commit();
          }else{
              //   getSupportFragmentManager().beginTransaction().remove(progressFragment).commit();
              getSupportFragmentManager().beginTransaction()
                      .add(R.id.bottomContainer,progressFragment).commit();
          }
      }
  }



    class broadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {

            mContext = context;
            runOnUiThread(()->{
                    bottomContainers.removeAllViews();
                    View view1 = View.inflate(mContext, R.layout.bcustom_setshadowlayer, null);
                    Shadows shadow = new Shadows(context);
                    RotateFirst rotateFirst = new RotateFirst(context);
                    shadow = view1.findViewById(R.id.shadow);
                    rotateFirst = view1.findViewById(R.id.rotatefirst);

                    if (intent.getStringExtra("shadow") != null && intent.getStringExtra("shadow").equals("true")) {
                        shadow.setVisibility(View.VISIBLE);
                    } else if (intent.getStringExtra("ROTATEFIRST") != null && intent.getStringExtra("ROTATEFIRST").equals("true")) {
                        shadow.setVisibility(View.GONE);
                        rotateFirst.setVisibility(View.VISIBLE);
                    }
                    bottomContainers.addView(view1);
            });

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);//不要忘了注銷掉廣播
    }
}
