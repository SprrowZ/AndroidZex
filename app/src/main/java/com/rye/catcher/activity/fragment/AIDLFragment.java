package com.rye.catcher.activity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.beans.AIDLBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class AIDLFragment extends BaseFragment {
    private static final String TAG="AIDLFragment";
    private static String fromClientParam;
    private View view;
    Unbinder unbinder;
    @BindView(R.id.baseType)
    TextView  baseType;
    @BindView(R.id.bean)
    TextView customType;
    public AIDLFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_aidl, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void dealAIDLEvent(AIDLBean bean){
        Log.i(TAG, "dealAIDLEvent: "+bean.toString());
        String addParam1= String.valueOf(bean.getIntParam1());
        String addParam2= String.valueOf(bean.getIntParam2());
        String personName=bean.getPersonName();
        String sex= String.valueOf(bean.isSex());
        String baseTypeParams=addParam1+","+addParam2;
        baseType.setText(baseTypeParams);
        String customParams=personName+","+sex;
        customType.setText(customParams);
    }
}
