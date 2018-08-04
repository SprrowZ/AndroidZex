package com.example.myappsecond.project.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myappsecond.project.ctmviews.CtmEighthActivity;
import com.example.myappsecond.project.ctmviews.CtmElevenActivity;
import com.example.myappsecond.project.ctmviews.CtmNinethActivity;
import com.example.myappsecond.project.ctmviews.CtmTwelfthsActivity;
import com.example.myappsecond.project.ctmviews.Mypractice.Custom_Tenth;
import com.example.myappsecond.R;

/**
 * Created by ZZG on 2018/3/6.
 */

public class CtmViewFragment extends  Fragment implements View.OnClickListener {
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
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
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
          startActivity(new Intent(getActivity(), CtmEighthActivity.class));
          Intent intent2=new Intent(getActivity(),CtmEighthActivity.class);
          intent2.putExtra("origin","Telescope");
          startActivity(intent2);
//          Intent intent1=new Intent();
//          intent1.setAction("com.custom.fragment.activity");
//          Bundle bundle1=new Bundle();
//          bundle1.putString("telescope","true");
//          getActivity().sendBroadcast(intent1);
          break;
      case R.id.btn3:
          Intent intent1=new Intent(getActivity(),CtmEighthActivity.class);
          intent1.putExtra("origin","DistortionView");
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
            Bundle bundle1=new Bundle();
            bundle1.putString("ROTATEFIRST","true");
            intent8.putExtras(bundle1);
         getActivity().sendBroadcast(intent8);

            break;

        case R.id.btn9:
            break;
  }
    }
}
