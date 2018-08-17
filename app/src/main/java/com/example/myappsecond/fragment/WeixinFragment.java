package com.example.myappsecond.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myappsecond.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzg on 2017/10/10.
 */

public class WeixinFragment extends BaseFragment {
  private View view;
  private Unbinder unbinder;//butterknife
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab01,container,false);
        this.view=view;
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBarTitle("秦时明月");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//一定不要忘记！
    }
}
