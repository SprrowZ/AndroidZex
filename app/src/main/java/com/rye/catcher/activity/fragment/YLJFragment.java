package com.rye.catcher.activity.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.rye.catcher.R;
import com.rye.catcher.common.bigimg.ImageActivity;
import com.rye.catcher.project.ctmviews.DistortionViews;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.ImageUtils;
import com.rye.catcher.utils.SDHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzg on 2017/10/10.
 */

public class YLJFragment extends BaseFragment {
    private static final String TAG = "YLJFragment";
  private View view;
  private Unbinder unbinder;//butterknife
  private ScrollView scrollView;
  //头像
  private DistortionViews portrait;
  //头像本地地址
  private static  final String pLocal=SDHelper.getImageFolder()+"portrait.png";
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
        initEvent();
    }


    private void initEvent() {
        /**
         * 下载头像
         */
            iv1.getViewTreeObserver().addOnGlobalLayoutListener(()->{
                new Thread(()->{
                    Log.i(TAG, "savePortraitToLocal: "+iv1.getMeasuredHeight()+"xxx"+iv1.getHeight());
                    Bitmap bitmap=ImageUtils.getIntance().getBitmap(SDHelper.getImageFolder()+"portrait.png",
                            iv1.getMeasuredHeight(), iv1.getMeasuredWidth(),Constant.PORTRAIT_URL);
                    getActivity().runOnUiThread(()->{
                        iv1.setImageBitmap(bitmap);
                    });
                }).start();
            });

       //点击头像进大图
        portrait.setOnClickListener(data->{
            Intent intent=new Intent(getActivity(), ImageActivity.class);
            ArrayList<String> imgList=new ArrayList<>();
            imgList.add(pLocal);
            intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST,imgList);
            startActivity(intent);
        });

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
