package com.rye.catcher.project.ctmviews.dellist;

import android.os.Bundle;
import android.view.View;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ZZG on 2017/11/29.
 */
public class DelMainActivity extends BaseActivity implements View.OnClickListener, ItemDelLinear.OnScrollListener {
    //使用自定义ListView
    private DelListView listView;
   // private TextView del;
    DelAdapter adapter;
    //
    private ItemDelLinear mLastScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_listview);
        listView =findViewById(R.id.listview);
        //del=findViewById(R.id.del);
        final List<DelAdapter.DataHolder> items = new ArrayList<DelAdapter.DataHolder>();
        for(int i=0;i<20;i++){
           DelAdapter.DataHolder item = new DelAdapter.DataHolder();
            item.title = "第"+i+"项";
            items.add(item);
        }
        adapter = new DelAdapter(this,items,this,this);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.del){
            int position = listView.getPositionForView(v);
            adapter.removeItem(position);
        }
    }

    @Override
    public void OnScroll(ItemDelLinear var1) {
        if(this.mLastScrollView != null) {
            this.mLastScrollView.smoothScrollTo(0, 0);
         }

       this.mLastScrollView =var1;
    }
    }

//    public void OnScroll(MyLinearLayout view) {
//
