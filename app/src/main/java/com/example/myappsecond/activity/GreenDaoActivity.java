package com.example.myappsecond.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.R;
import com.example.myappsecond.adapter.GreenAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/7/5.
 */
public class GreenDaoActivity extends BaseActivity {
    public PullToRefreshListView listView;
    private DaoSession daoSession;
    private CartoonsDao cartoonsDao;
    List datalist=new ArrayList<Cartoons>();
    private GreenAdapter adapter;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_exone);
        init();
    }

    private void init() {
        listView=findViewById(R.id.listView);

//        inflater=LayoutInflater.from(this);
//        View view=inflater.inflate();
//        TextView endView=new TextView(this);
//        endView.setText("已经到底了呢~");
//        endView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        daoSession= EChatApp.getInstance().getDaoSession();
        cartoonsDao=daoSession.getCartoonsDao();
        //查询数据进行适配
        datalist=cartoonsDao.queryBuilder().where(
                CartoonsDao.Properties.NAME.notEq("七龙珠")).list();
        adapter=new GreenAdapter(this,datalist);
        listView.setAdapter(adapter);
    }

}
