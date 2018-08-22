package com.example.myappsecond.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by Zzg on 2018/8/20.
 */
public class ProvinceListActivity extends BaseActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_list);
        init();
    }

    private void init() {
        setBarTitle("树");
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }
}
