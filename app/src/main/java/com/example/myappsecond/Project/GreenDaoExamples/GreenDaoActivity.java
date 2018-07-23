package com.example.myappsecond.Project.GreenDaoExamples;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.R;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_exone);
        listView=findViewById(R.id.listView);
        daoSession= EChatApp.getInstance().getDaoSession();
        cartoonsDao=daoSession.getCartoonsDao();
        //查询数据进行适配
        datalist=cartoonsDao.queryBuilder().where(
                CartoonsDao.Properties.NAME.notEq("七龙珠")).list();
        adapter=new GreenAdapter(this,datalist);
        listView.setAdapter(adapter);
    }

}
