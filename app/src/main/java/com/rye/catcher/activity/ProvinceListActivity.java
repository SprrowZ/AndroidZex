package com.rye.catcher.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.ProvinceListAdapter;
import com.rye.catcher.activity.testdata.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/8/20.
 */
public class ProvinceListActivity extends BaseActivity {
   public static  final  String TAG="ProvinceListActivity";
   private  RecyclerView recyclerView;
   private ProvinceListAdapter adapter;
   private List<DataModel> dataList;
   int colors[] ={R.color.soft1,R.color.soft2,R.color.soft3};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_list);
        init();
    }

    private void init() {
        setBarTitle("省份");
        dataList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        //        LinearLayoutManager manager=new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
        Log.i(TAG, "init: "+(int)2.7);
        for (int i=0;i<20;i++){
            int type=(int)((Math.random()*3)+1);
            DataModel data=new DataModel();
            data.avatarColor=colors[type-1];
            data.type=type;
            data.name="name:"+i;
            data.content="content:"+i;
            data.contentColor=colors[(type+1)%3];
        }
        adapter=new ProvinceListAdapter(this,dataList);




    }
}
