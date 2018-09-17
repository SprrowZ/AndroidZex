package com.example.myappsecond.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myappsecond.R;
import com.example.myappsecond.activity.fragment.BaseFragment;

/**
 * Created by ZZG on 2018/8/10.
 */
public class DistortionFragment extends BaseFragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View views=inflater.inflate(R.layout.bcustom_distortionview,container,false);
      this.view=views;
      return views;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
