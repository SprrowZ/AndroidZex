package com.rye.catcher.project.mvp;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.TextView;

import com.rye.catcher.R;

public class MvpActivity extends MvpBaseActivity implements MvpView {
    //进度条
    ProgressDialog progressDialog;
    TextView text;
    MvpPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp_demo;
    }

    @Override
    public void initEvent() {
        init();
    }
    private void init() {
        text = findViewById(R.id.text);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载数据...");
        presenter = new MvpPresenter();
        presenter.attachView(this);
    }

    public void getData(View view) {
        presenter.getData("normal");
    }

    public void getDataForFailure(View view) {
        presenter.getData("failure");
    }

    public void getDataForError(View view) {
        presenter.getData("error");
    }


    @Override
    public void showData(String data) {
        text.setText(data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


}
