package com.rye.catcher.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.utils.ExtraUtil.Bean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzg on 2017/10/10.
 */

public class YLJFragment extends BaseFragment {
  private View view;
  private Unbinder unbinder;//butterknife
  private ScrollView scrollView;
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
        EventBus.getDefault().register(this);
        scrollView=getView().findViewById(R.id.scrollView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//一定不要忘记！
    }
  @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
  public void dealWeather(Bean bean){
        if (bean!=null){

        }
  }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
