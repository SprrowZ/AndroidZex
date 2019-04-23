package com.rye.catcher.activity.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.DeviceAdapter;
import com.rye.catcher.beans.binding.DeviceBean;
import com.rye.catcher.databinding.DeviceInfoBinding;
import com.rye.catcher.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Fragment用Butterknife，Adapter用DataBinding
 */
public class DeviceInfoFragment extends Fragment {

    private DeviceInfoBinding binding;
    private DeviceAdapter adapter;
    private List<DeviceBean> dataList;
    public DeviceInfoFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_device_info,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new DeviceAdapter(getContext(),getDataList());

    }

    private List<DeviceBean> getDataList(){
       dataList=new ArrayList<>();
       DeviceBean bean=new DeviceBean() ;
              bean.setTitle("厂商");
              bean.setContent(DeviceUtils.getDeviceManufacturer());

       DeviceBean bean1=new DeviceBean();
              bean.setTitle("手机品牌");
              bean.setContent(DeviceUtils.getDeviceBrand());

       DeviceBean bean2= new DeviceBean();
             bean.setTitle("手机型号");
             bean.setTitle(DeviceUtils.getDeviceModel());
       DeviceBean bean3=new DeviceBean();
             bean.setTitle("手机主板名");
             bean.setContent(DeviceUtils.getDeviceBoard());
       DeviceBean bean4=new DeviceBean();
             bean.setTitle("手机ID");
             bean.setContent(DeviceUtils.getDeviceID());

       dataList.add(bean);
       dataList.add(bean1);
       dataList.add(bean2);
       dataList.add(bean3);
       dataList.add(bean4);
       return dataList;
    }
}
