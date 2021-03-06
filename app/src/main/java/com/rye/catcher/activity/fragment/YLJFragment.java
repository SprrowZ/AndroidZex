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
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.common.bigimg.ImageActivity;
import com.rye.catcher.project.ctmviews.DistortionViews;
import com.rye.catcher.sdks.beans.TangBean;
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
//    private static final String pLocal = SDHelper.getImageFolder() + "portrait.png";
//    @BindView(R.id.portrait)
//    DistortionViews portrait;
//    @BindView(R.id.iv1)
//    ImageView iv1;
@BindView(R.id.poetry_title)
    TextView poetryTitle;
@BindView(R.id.poetry_content)
   TextView poepryContent;
@BindView(R.id.poetry_author)
   TextView poepryAuthor;
    @Override
    protected int getLayoutResId() {
        return R.layout.tab01;
    }

    @Override
    protected void initData() {
        setBarTitle("秦时明月");
        //     initEvent();
        EventBus.getDefault().register(this);
    }


//    private void initEvent() {
//
//        ImageUtils.getIntance().displayImage(iv1, Constant.PORTRAIT_URL);
//        //点击头像进大图
//        portrait.setOnClickListener(data -> {
//            Intent intent = new Intent(getActivity(), ImageActivity.class);
//            ArrayList<String> imgList = new ArrayList<>();
//            imgList.add(pLocal);
//            intent.putStringArrayListExtra(ImageActivity.IMAGE_LIST, imgList);
//            startActivity(intent);
//        });
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setPoetry(TangBean bean) {
         TangBean.ResultBean result=bean.getResult();
         String title=result.getTitle();
         String author=result.getAuthors();
         String content=result.getContent();
         poetryTitle.setText(title);
         poepryAuthor.setText(author);
         //内容
         String[] contentSplit= content.split("\\|");
         StringBuilder sb=new StringBuilder();
         for (int i=0;i<contentSplit.length;i++){
             Log.i(TAG, "setPoetry: "+contentSplit[i]);
             if (i!=contentSplit.length-1){
                 sb.append(contentSplit[i]+"\n\n");
             }else {
                 sb.append(contentSplit[i]+"\n");
             }

         }
         poepryContent.setText(sb);
    }

}
