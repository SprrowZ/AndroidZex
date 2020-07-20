package com.rye.catcher.activity;

import com.rye.catcher.R;
import com.rye.catcher.base.mvp.BaseMvpActivity;
import com.rye.catcher.base.mvp.demos.AnimePresenter;
import com.rye.catcher.base.mvp.demos.AnimeView;

public class AnimeActivity extends BaseMvpActivity<AnimeView,AnimePresenter> implements AnimeView {



    @Override
    public AnimePresenter initPresenter() {
        return new AnimePresenter();
    }

    @Override
    public void showRealmData() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_anime2;
    }

    @Override
    public void initEvent() {

    }
}
