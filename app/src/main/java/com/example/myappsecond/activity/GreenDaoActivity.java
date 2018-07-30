package com.example.myappsecond.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.R;
import com.example.myappsecond.adapter.BaseArrayAdapter;
import com.example.myappsecond.adapter.GreenAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/7/5.
 */
public class GreenDaoActivity extends BaseActivity {
    public PullToRefreshListView listView;
    private  EditText query;
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
        setBarTitle("漫");
        listView=findViewById(R.id.listView);
        query=findViewById(R.id.search_bar);
//        inflater=LayoutInflater.from(this);
//        View view=inflater.inflate();
//        TextView endView=new TextView(this);
//        endView.setText("已经到底了呢~");
//        endView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        daoSession= EChatApp.getInstance().getDaoSession();
        cartoonsDao=daoSession.getCartoonsDao();
        //查询数据进行适配
        datalist=cartoonsDao.queryBuilder().
                where(
                CartoonsDao.Properties.NAME.notEq("七龙珠")).list();

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        if (adapter==null){
            adapter=new GreenAdapter(this,datalist);
            adapter.setService(new BaseArrayAdapter.IService<Cartoons>() {
                @Override
                public boolean add(Cartoons item, String query) {
                    return item.getNAME().contains(query)
                            ||item.getHERO().contains(query)
                            ||item.getHEROINE().contains(query);
                }
            });
            listView.setAdapter(adapter);
        }else{
          adapter.notifyDataSetChanged();
          if (query!=null){
            adapter.getFilter().filter(query.getText());
          }
        }

        listView.setAdapter(adapter);
    }

}
