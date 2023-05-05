package com.dawn.zgstep.ui.fragment.assist.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dawn.zgstep.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/4/10 14:51
 */
public class BannerContainer extends FrameLayout {
    private View mRoot;
    private BannerViewPager mViewPager2;
    private BannerAdapter mBannerAdapter;


    public BannerContainer(@NonNull Context context) {
        this(context,null);
    }

    public BannerContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BannerContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mRoot = LayoutInflater.from(context).inflate(R.layout.container_banner,null);
        mViewPager2 = mRoot.findViewById(R.id.view_pager);
        mBannerAdapter = new BannerAdapter();
        mViewPager2.setAdapter(mBannerAdapter);
        mViewPager2.setCanShowIndicator(true);
        //mViewPager2.setCanLoop(true);
        mViewPager2.create(mockData());
        removeAllViews();
        addView(mRoot);
    }

    public List<StaticBannerBean> mockData() {
        List<StaticBannerBean> dataList = new ArrayList<>();
        for (int i=0;i<7;i++) {
            StaticBannerBean bean = new StaticBannerBean();
            bean.imgUrl = R.drawable.my_new1;
            bean.linkUrl = "https://www.baidu.com";
            dataList.add(bean);
        }
        return dataList;
    }



}
