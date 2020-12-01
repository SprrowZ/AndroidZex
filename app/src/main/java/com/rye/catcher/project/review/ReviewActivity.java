package com.rye.catcher.project.review;


import android.content.Intent;

import androidx.annotation.RequiresApi;


import android.os.Build;

import android.view.View;

import android.widget.Button;


import com.rye.base.BaseFragmentActivity;

import com.rye.base.utils.ToastHelper;
import com.rye.catcher.R;
import com.rye.catcher.project.review.refresh.RefreshRecyActivity;
import com.rye.catcher.project.review.search.SearchFragment;
import com.rye.catcher.project.review.sqlite.TSqliteFragment;

/**
 * Created by ZZG on 2017/10/25.
 */

public class ReviewActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "ReviewActivity";
    private Button btnRefresh;
    private Button btnSpan;
    private Button btnSearch;
    private Button btnSqlite;


    @Override
    public int getLayoutId() {
        return R.layout.activity_review;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initEvent() {
        btnRefresh = findViewById(R.id.btn_refresh);
        btnSpan = findViewById(R.id.btn_span);
        btnSearch = findViewById(R.id.btn_search);
        btnSqlite = findViewById(R.id.btn_sqlite);


        btnRefresh.setOnClickListener(this);
        btnSpan.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnSqlite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh:
                ToastHelper.showToastShort(this,"暂不可点击！");
//                Intent intent1 = new Intent(this, RefreshRecyActivity.class);
//                startActivity(intent1);
                break;

            case R.id.btn_span:
                startActivity(new Intent(this, SpannableActivity.class));
                break;

            case R.id.btn_search:
                replaceFragment(new SearchFragment());
                break;


            case R.id.btn_sqlite:
                replaceFragment(R.id.container_review, new TSqliteFragment());
                break;


        }
    }


}
