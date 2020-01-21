package com.rye.catcher.base.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.rye.catcher.BaseActivity;

/**
 * Created by 18041at 2019/5/26
 * Function:
 */
public abstract class BaseMvpActivity<V extends BaseView,T extends BasePresenter<V>>
        extends BaseActivity  implements BaseView{
    public T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter =initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    public abstract  T initPresenter();

    @Override
    public void showLoading() {
        if (mPresenter.isViewAttached()){

        }
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void refreshView() {
        if (mPresenter.isViewAttached()){

        }
    }

}
