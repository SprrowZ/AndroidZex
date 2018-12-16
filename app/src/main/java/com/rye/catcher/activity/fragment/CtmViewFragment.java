package com.rye.catcher.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rye.catcher.R;
import com.rye.catcher.activity.ctmactivity.CtmDDActivity;
import com.rye.catcher.project.catcher.eventbus.MessageEvent;
import com.rye.catcher.activity.ctmactivity.CtmEighthActivity;
import com.rye.catcher.activity.ctmactivity.CtmElevenActivity;
import com.rye.catcher.activity.ctmactivity.CtmNinethActivity;
import com.rye.catcher.activity.ctmactivity.CtmTwelfthsActivity;
import com.rye.catcher.project.ctmviews.Mypractice.Custom_Tenth;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmViewFragment extends BaseFragment implements View.OnClickListener {
    public static final String TELESCOPE="TELESCOPE";
    public static final String DISTORTIONVIEW="DISTORTIONVIEW";
    public static final String ORIGIN="ORIGIN";
    private View view;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.bcustom_main2_fragment,container,false);
    this.view=view;
    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();
        super.onActivityCreated(savedInstanceState);
    }

    private void init() {
        btn1=view.findViewById(R.id.btn1);
        btn2=view.findViewById(R.id.btn2);
        btn3=view.findViewById(R.id.btn3);
        btn4=view.findViewById(R.id.btn4);
        btn5=view.findViewById(R.id.btn5);
        btn6=view.findViewById(R.id.btn6);
        btn7=view.findViewById(R.id.btn7);
        btn8=view.findViewById(R.id.btn8);
        btn9=view.findViewById(R.id.btn9);
        btn10=view.findViewById(R.id.btn10);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
      case R.id.btn1:
          //通过广播控制Activity点击事件
          View view1=View.inflate(getActivity(),R.layout.bcustom_setshadowlayer,null);
          Intent intent=new Intent();
          intent.setAction("com.custom.fragment.activity");
          Bundle bundle=new Bundle();
          bundle.putString("shadow","true");
          intent.putExtras(bundle);
          getActivity().sendBroadcast(intent);
      break;
      case R.id.btn2:
          Intent intent2=new Intent(getActivity(),CtmEighthActivity.class);
          Bundle bundle2=new Bundle();
          bundle2.putString(CtmViewFragment.ORIGIN,CtmViewFragment.TELESCOPE);
          intent2.putExtras(bundle2);
          startActivity(intent2);
          break;
      case R.id.btn3:
          Intent intent1=new Intent(getActivity(),CtmEighthActivity.class);
          Bundle bundle1=new Bundle();
          bundle1.putString(CtmViewFragment.ORIGIN,CtmViewFragment.DISTORTIONVIEW);
          intent1.putExtras(bundle1);
          startActivity(intent1);
          break;
      case R.id.btn4:
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
            default:
                break;
  }
    }


}
