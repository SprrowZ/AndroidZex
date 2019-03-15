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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzg on 2017/10/10.
 */

public class YLJFragment extends BaseFragment {
    private static final String TAG = "YLJFragment";


    //头像本地地址
    private static final String pLocal = SDHelper.getImageFolder() + "portrait.png";
    @BindView(R.id.portrait)
    DistortionViews portrait;
    @BindView(R.id.iv1)
    ImageView iv1;

    @Override
    protected int getLayoutResId() {
        return R.layout.tab01;
    }

    @Override
    protected void initData() {
        setBarTitle("秦时明月");
        initEvent();
    }


    private void initEvent() {

        ImageUtils.getIntance().displayImage(iv1, Constant.PORTRAIT_URL);
        //点击头像进大图
        portrait.setOnClickListener(data -> {
            Intent intent = new Intent(getActivity(), ImageActivity.class);
            ArrayList<String> imgList = new ArrayList<>();
            imgList.add(pLocal);
            intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST, imgList);
            startActivity(intent);
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
