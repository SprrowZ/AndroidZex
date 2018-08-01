package com.example.myappsecond.project.customViews;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.project.customViews.Mypractice.RotateFirst;
import com.example.myappsecond.project.fragments.CustomView2_Fragment;
import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmMain2Activity extends BaseActivity {
//    private LinearLayout fragmentContainer;
    private RelativeLayout bottomContainers;
    private Context mContext;
    private CustomView2_Fragment fragment=new CustomView2_Fragment();
    private broadcastReceiver receiver=new broadcastReceiver();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_main2);
        init();
    }

    private void init() {
//        fragmentContainer=findViewById(R.id.fragmentContainer);
        bottomContainers=findViewById(R.id.bottomContainers);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
        //注册广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.custom.fragment.activity");
        registerReceiver(receiver,intentFilter);
    }
    class  broadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, final Intent intent) {

            mContext=context;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bottomContainers.removeAllViews();
                    View view1=View.inflate(mContext,R.layout.bcustom_setshadowlayer,null);


                    Shadows shadow=new Shadows(context);
                    RotateFirst rotateFirst=new RotateFirst(context);
                    shadow=view1.findViewById(R.id.shadow);
                    rotateFirst=view1.findViewById(R.id.rotatefirst);
//                    if (intent.getStringExtra("telescope")!=null&&intent.getStringExtra("telescope").equals("true")){
//                        shadow.setVisibility(View.GONE);
//                        telescope.setVisibility(View.VISIBLE);
//                        Log.i("zzz", "run: telescope.....");
//                    }else
                    if (intent.getStringExtra("shadow")!=null&&intent.getStringExtra("shadow").equals("true")){
//                    bottomContainers.removeAllViews();
//                    CustomView_Shadow shadow=new CustomView_Shadow(context);
//                    shadow=view1.findViewById(R.id.shadow);
//                    shadow.setVisibility(View.GONE);

                        shadow.setVisibility(View.VISIBLE);
                        Log.i("zzz", "run: shadow.....");
                    }else if (intent.getStringExtra("ROTATEFIRST")!=null&&intent.getStringExtra("ROTATEFIRST").equals("true")){
                        shadow.setVisibility(View.GONE);
                        rotateFirst.setVisibility(View.VISIBLE);
                    }
                    bottomContainers.addView(view1);


                }
            });

        }
    }
}
