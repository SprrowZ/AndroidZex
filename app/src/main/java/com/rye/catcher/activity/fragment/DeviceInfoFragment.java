package com.rye.catcher.activity.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
        //设置布局
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (i%2==0){
                    return 2;
                }
                return 1;
            }
        });
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.recyclerView.setAdapter(adapter);

    }

    /**
     * 构造数据源
     * @return
     */
    private List<DeviceBean> getDataList(){
       dataList=new ArrayList<>();
       DeviceBean bean=new DeviceBean() ;
              bean.setTitle("厂商");
              bean.setContent(DeviceUtils.getDeviceManufacturer());

       DeviceBean bean1=new DeviceBean();
              bean1.setTitle("手机品牌");
              bean1.setContent(DeviceUtils.getDeviceBrand());

       DeviceBean bean2= new DeviceBean();
             bean2.setTitle("手机型号");
             bean2.setContent(DeviceUtils.getDeviceModel());
       DeviceBean bean3=new DeviceBean();
             bean3.setTitle("手机主板名");
             bean3.setContent(DeviceUtils.getDeviceBoard());
       DeviceBean bean4=new DeviceBean();
             bean4.setTitle("手机ID");
             bean4.setContent(DeviceUtils.getDeviceID());
       DeviceBean bean5=new DeviceBean();
             bean5.setTitle("手机分辨率");
             String resolvingPower=DeviceUtils.getScreenHeight(getActivity())
                     +"x"+DeviceUtils.getScreenWidth(getActivity());
             bean5.setContent(resolvingPower);
       DeviceBean bean6=new DeviceBean();
             bean6.setTitle("DPI");
             bean6.setContent(String.valueOf(DeviceUtils.getDpi(getActivity())));
       DeviceBean bean7=new DeviceBean();
             bean7.setTitle("最小宽度");
             bean7.setContent(String.valueOf(DeviceUtils.getScreenSw(getActivity())));
       dataList.add(bean);
       dataList.add(bean1);
       dataList.add(bean2);
       dataList.add(bean3);
       dataList.add(bean4);
       dataList.add(bean5);
       dataList.add(bean6);
       dataList.add(bean7);
       return dataList;
    }
}
