package com.rye.catcher.project.Ademos.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.utils.ToastUtils;

public class MvpBaseActivity extends BaseActivity implements  BaseView{
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showLoading() {
     if (!mProgressDialog.isShowing()){
         mProgressDialog.show();
     }
    }

    @Override
    public void hideLoading() {
       if (mProgressDialog.isShowing()){
           mProgressDialog.dismiss();
       }
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.shortMsg(msg);
    }

    @Override
    public void showError() {
     showToast("出错了！");
    }

    @Override
    public Context getContext() {
        return this;
    }
}
