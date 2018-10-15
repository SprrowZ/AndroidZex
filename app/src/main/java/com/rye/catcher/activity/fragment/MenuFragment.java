package com.rye.catcher.activity.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rye.catcher.project.MyReceiver;
import com.rye.catcher.R;

/**
 * Created by angel on 2017/10/23.
 */

public class MenuFragment extends Fragment {

private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view=inflater.inflate(R.layout.menu_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     btn1=view.findViewById(R.id.btn1);
        btn2=view.findViewById(R.id.btn2);
        btn3=view.findViewById(R.id.btn3);
        btn4=view.findViewById(R.id.btn4);
        //长按
        btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(getActivity(),"demn",Toast.LENGTH_LONG).show();
                return false;
            }
        });
        //send Broadcast
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   //注册广播接收者
                IntentFilter intentFilter=new IntentFilter();
                intentFilter.addAction("com.zzg.myselfReceiver.LAUNCH");
                getActivity().registerReceiver(new MyReceiver(),intentFilter);
                //发送广播
                Intent intent1=new Intent();
                intent1.setAction("com.zzg.myselfReceiver.LAUNCH");
                getActivity().sendBroadcast(intent1);
            }
        });
        //MenuTest



    }
}
