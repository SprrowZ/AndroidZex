package com.rye.catcher.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.MultiPagerAdapter;
import com.rye.catcher.activity.fragment.orr.OkhttpFragment;
import com.rye.catcher.activity.fragment.orr.RetrofitFragment;
import com.rye.catcher.activity.fragment.orr.RxjavaFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiActivity extends AppCompatActivity {
@BindView(R.id.tabLayout)
TabLayout tableLayout;
@BindView(R.id.viewPager)
ViewPager viewPager;
private String[]  titles=new String[]{"MultiHttp","MultiRe","ThreadPool"};
private List<String> titleList;
private List<Fragment> fragmentList;
private MultiPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        ButterKnife.bind(this);
        init();
    }
    private void init(){
        fragmentList=new ArrayList<>();
        titleList= Arrays.asList(titles);
        fragmentList.add(new OkhttpFragment());
        fragmentList.add(new RxjavaFragment());
        fragmentList.add(new RetrofitFragment());
        adapter=new MultiPagerAdapter(getSupportFragmentManager(),titleList,fragmentList);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

    }
}
