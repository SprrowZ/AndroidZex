package com.example.myappsecond.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.project.ctmviews.DistortionViews;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2018/8/19.
 */
public class CartoonsDetailActivity extends BaseActivity {
    @BindView(R.id.header_img)
    DistortionViews headerImg;
    @BindView(R.id.top_name)
    TextView topName;
    @BindView(R.id.cartoon_name)
    TextView cartoonName;
    @BindView(R.id.cartoon_hero)
    TextView cartoonHero;
    @BindView(R.id.cartoon_director)
    TextView cartoonDirector;
    @BindView(R.id.cartoon_is_end)
    TextView cartoonIsEnd;
    @BindView(R.id.cartoon_actors)
    TextView cartoonActors;
    @BindView(R.id.details)
    TextView details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartoon_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }

    @OnClick(R.id.header_img)
    public void onViewClicked() {
    }
}
