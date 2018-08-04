package com.example.myappsecond.project.ctmviews.ListView_Delete;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.myappsecond.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ZZG on 2017/11/29.
 */
public class List_Main extends Activity implements View.OnClickListener, List_LinearLayout.OnScrollListener {
    //使用自定义ListView
    private List_ListView listView;
   // private TextView del;
    List_MergeListAdapter adapter;
    //
    private List_LinearLayout mLastScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_listview);
        listView =findViewById(R.id.listview);
        //del=findViewById(R.id.del);
        final List<List_MergeListAdapter.DataHolder> items = new ArrayList<List_MergeListAdapter.DataHolder>();
        for(int i=0;i<20;i++){
           List_MergeListAdapter.DataHolder item = new List_MergeListAdapter.DataHolder();
            item.title = "第"+i+"项";
            items.add(item);
        }
        adapter = new List_MergeListAdapter(this,items,this,this);
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
    public void OnScroll(List_LinearLayout var1) {
        if(this.mLastScrollView != null) {
            this.mLastScrollView.smoothScrollTo(0, 0);
         }

       this.mLastScrollView =var1;
    }
    }

//    public void OnScroll(MyLinearLayout view) {
//
