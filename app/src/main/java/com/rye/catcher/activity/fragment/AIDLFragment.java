package com.rye.catcher.activity.fragment;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.rye.catcher.BaseFragment;
import com.rye.catcher.R;
import com.rye.catcher.beans.AIDLBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AIDLFragment extends BaseFragment {
    private static final String TAG="AIDLFragment";
    private static String fromClientParam;

    @BindView(R.id.baseType)
    TextView  baseType;
    @BindView(R.id.bean)
    TextView customType;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_aidl;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
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
