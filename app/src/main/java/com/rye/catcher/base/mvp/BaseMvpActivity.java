package com.rye.catcher.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.rye.catcher.BaseActivity;

/**
 * Created by 18041at 2019/5/26
 * Function:
 */
public abstract class BaseMvpActivity<V extends BaseView,T extends BasePresenter<V>> extends BaseActivity {
    public T presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    public abstract  T initPresenter();
}
