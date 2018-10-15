package com.rye.catcher.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;

/**
 * Created by ZZG on 2018/8/10.
 */
public class TelescopeFragment extends BaseFragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View views=inflater.inflate(R.layout.bcustom_telescope,container,false);
        this.view=views;
        return views;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
