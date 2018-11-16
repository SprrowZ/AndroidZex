package com.rye.catcher.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;


import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created at 2018/10/17.
 *
 * @author Zzg
 */
public class CoordinatorActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout_test);
        ButterKnife.bind(this);
      //   toolbar.setNavigationIcon(R.drawable.mulu);
        toolbar.inflateMenu(R.menu.topbar_right);
        collapsingToolbarLayout.setTitle("....RyeCatcher....");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.red));
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.soft12));
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        collapsingToolbarLayout.setScrimAnimationDuration(300);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        ToastUtils.shortMsg("search");
                        break;
                    case R.id.notification:
                        ToastUtils.shortMsg("notification");
                        break;
                    case R.id.settings_about:
                        ToastUtils.shortMsg("settings");
                        break;
                    case R.id.mulu:
                        ToastUtils.shortMsg("mulu");
                        break;
                     default:
                         break;
                }
                return true;
            }
        });

    }
}
