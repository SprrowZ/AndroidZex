package com.rye.catcher.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rye.catcher.BaseOldFragment;
import com.rye.catcher.R;
import com.rye.catcher.activity.ctmactivity.CtmDDActivity;
import com.rye.catcher.activity.ctmactivity.CtmPTRActivity;
import com.rye.catcher.project.catcher.eventbus.MessageEvent;
import com.rye.catcher.activity.ctmactivity.CtmEighthActivity;
import com.rye.catcher.activity.ctmactivity.CtmElevenActivity;
import com.rye.catcher.activity.ctmactivity.CtmNinethActivity;
import com.rye.catcher.activity.ctmactivity.CtmTwelfthsActivity;
import com.rye.catcher.project.ctmviews.Mypractice.Custom_Tenth;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmViewFragment extends BaseOldFragment {
    public static final String TELESCOPE="TELESCOPE";
    public static final String DISTORTIONVIEW="DISTORTIONVIEW";
    public static final String ORIGIN="ORIGIN";


    @Override
    protected int getLayoutResId() {
        return R.layout.bcustom_main2_fragment;
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.orr,R.id.javaMore,R.id.translate,
    R.id.rotate,R.id.btn5,R.id.btn6, R.id.btn7, R.id.btn8,R.id.btn9,R.id.btn10,
    R.id.btn11,R.id.btn12})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.orr:
                //通过广播控制Activity点击事件
                View view1=View.inflate(getActivity(),R.layout.bcustom_setshadowlayer,null);
                Intent intent=new Intent();
                intent.setAction("com.custom.fragment.activity");
                Bundle bundle=new Bundle();
                bundle.putString("shadow","true");
                intent.putExtras(bundle);
                getActivity().sendBroadcast(intent);
                break;
            case R.id.javaMore:
                Intent intent2=new Intent(getActivity(),CtmEighthActivity.class);
                Bundle bundle2=new Bundle();
                bundle2.putString(CtmViewFragment.ORIGIN,CtmViewFragment.TELESCOPE);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.translate:
                Intent intent1=new Intent(getActivity(),CtmEighthActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString(CtmViewFragment.ORIGIN,CtmViewFragment.DISTORTIONVIEW);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.rotate:
                startActivity(new Intent(getActivity(), CtmNinethActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(getActivity(), Custom_Tenth.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(getActivity(), CtmElevenActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(getActivity(), CtmTwelfthsActivity.class));
                break;
            case R.id.btn8:
                Intent intent8=new Intent();
                intent8.setAction("com.custom.fragment.activity");
                Bundle bundle3=new Bundle();
                bundle3.putString("ROTATEFIRST","true");
                intent8.putExtras(bundle3);
                getActivity().sendBroadcast(intent8);

                break;

            case R.id.btn9:
                //加载一个fragment,发个广播吧。。额，自己坑自己玩
                EventBus.getDefault().postSticky(new MessageEvent("insertProgress"));
                break;
            case R.id.btn10:
                Intent intent3=new Intent(getActivity(),CtmDDActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn11:
                startActivity(new Intent(getActivity(), CtmPTRActivity.class));
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
