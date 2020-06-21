package com.rye.catcher.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.ProvinceListAdapter;
import com.rye.catcher.activity.adapter.ProvinceSecondAdapter;
import com.rye.catcher.activity.adapter.ProvinceTitleAdapter;

/**
 * Created by Zzg on 2018/8/20.
 *
 * 现在用于测试Adapter切换。相当于MainMyFragment
 */
public class ProvinceListActivity extends BaseOldActivity implements ProvinceTitleAdapter.TitleClickListener {
   public static  final  String TAG="ProvinceListActivity";
   private  RecyclerView recyclerView;

   private RecyclerView titles;
   private RecyclerView.Adapter mContentAdapter;
   private ProvinceTitleAdapter mTitleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_list);
        init();
    }

    private void init() {
        setBarTitle("省份");

        recyclerView=findViewById(R.id.recyclerView);
        titles=findViewById(R.id.titles);
        //标题部分
        titles.setLayoutManager(new GridLayoutManager(this,5));
        mTitleAdapter=new ProvinceTitleAdapter(this);
        titles.setAdapter(mTitleAdapter);


    }

    @Override
    public void changeAdapter(int position) {

        switch (position){
            case 0:
            case 1:
            case 2:
            case 3:
                //内容部分
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                mContentAdapter =new ProvinceListAdapter(this);
                recyclerView.setAdapter(mContentAdapter);
                break;
            case 4:
                //内容部分
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                mContentAdapter =new ProvinceSecondAdapter(this);
                recyclerView.setAdapter(mContentAdapter);

                break;
        }

    }
}
