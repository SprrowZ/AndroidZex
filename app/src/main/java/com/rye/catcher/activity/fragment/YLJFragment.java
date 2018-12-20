package com.rye.catcher.activity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.rye.catcher.R;
import com.rye.catcher.project.ctmviews.DistortionViews;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.FileUtils;
import com.rye.catcher.utils.ImageUtils;
import com.rye.catcher.utils.SDHelper;

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
  //头像
  private DistortionViews portrait;

  private ImageView iv1;
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
        portrait=getView().findViewById(R.id.portrait);
        iv1=getView().findViewById(R.id.iv1);
        savePortraitToLocal();
    }

    /**
     * 头像还是有问题，后续优化
     */
    private void savePortraitToLocal() {
        new Thread(()->{
            //先保存到本地
            FileUtils.saveImage(Constant.PORTRAIT_URL,"portrait.png");
            //取小图
            Bitmap portraitUrl= ImageUtils.ratio(SDHelper.getImageFolder()+"portrait.png",
                    200,200);
            getActivity().runOnUiThread(()->{
//                portrait.setBitmap(portraitUrl);
                iv1.setImageBitmap(portraitUrl);
            });
        }).start();

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
