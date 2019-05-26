package com.rye.catcher.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.base.mvp.BaseMvpActivity;
import com.rye.catcher.base.mvp.BasePresenter;
import com.rye.catcher.base.mvp.demos.AnimePresenter;
import com.rye.catcher.base.mvp.demos.AnimeView;

public class AnimeActivity extends BaseMvpActivity<AnimeView,AnimePresenter> implements AnimeView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime2);
    }

    @Override
    public AnimePresenter initPresenter() {
        return new AnimePresenter();
    }

    @Override
    public void showRealmData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void refreshView() {

    }
}
