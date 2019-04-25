package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2017/9/27.
 */

public class JetPackActivity extends BaseActivity {
    @BindView(R.id.viewModel)
    Button viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jetpack);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.viewModel})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.viewModel:
                Intent intent=new Intent(this,ViewModelActivity.class);
                startActivity(intent);
                break;
        }
    }
}
