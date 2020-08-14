package com.rye.catcher.base.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.rye.base.BaseActivity;


/**
 * Created by 18041at 2019/5/26
 * Function:
 */
public abstract class JokerMvpActivity<V extends JokerBaseView,T extends JokerBasePresenter<V>>
        extends BaseActivity implements JokerBaseView {
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
