package com.rye.catcher.activity.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.beans.binding.ClickHandler;
import com.rye.catcher.beans.binding.SettingBean;
import com.rye.catcher.databinding.Tab04Binding;

/**
 * Created by zzg on 2017/10/10.
 */

public class SettingsFragment  extends Fragment {

   private Tab04Binding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.tab04,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValue();
    }

    private void setValue() {
        SettingBean bean=new SettingBean();
        bean.setOrr(getString(R.string.orr));
        bean.setAnimation(getString(R.string.animation));
        bean.setSomeDemo(getString(R.string.somedome));
        bean.setCamera(getString(R.string.camera));
        bean.setCustomView(getString(R.string.customView));
        bean.setJavaMore(getString(R.string.javaMore));
        bean.setRetrofit(getString(R.string.retrofit));
        bean.setReview(getString(R.string.review));
        bean.setProject(getString(R.string.project));
        binding.setSettingName(bean);
         binding.setClickHandler(new ClickHandler());
//        binding.camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                   bean.setCamera("22222!");
//            }
//        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
