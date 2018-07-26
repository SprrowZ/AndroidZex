package com.example.myappsecond.review;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myappsecond.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZZG on 2017/10/25.
 */

public class BaseAdapterTest extends Activity implements RefreshListView.IReflashListener {
    private RefreshListView listView;
    List<ItemBean> itemBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_baseadapter);
        listView=findViewById(R.id.listView);
        //设置完接口后，在下面设置监听器
        listView.setInterface(this);
     itemBeanList=new ArrayList<>();
        for (int i=0;i<20;i++){
             itemBeanList.add(new ItemBean(R.mipmap.ic_launcher,"Title...","Content..."));
         }
        listView.setAdapter(new MyAdapter(itemBeanList,this) );
    }

    @Override
    public void onReflash() {
        //设置个延迟
        android.os.Handler handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//获取最新数据
                setReflashData();
                //通知界面显示

              listView.setInterface(BaseAdapterTest.this);
            listView.setAdapter(new MyAdapter(itemBeanList,BaseAdapterTest.this));

                //通知listview刷新数据完毕
                listView.reflashComplete();
            }
        },3000);

    }
    public void setReflashData(){

        for (int i=0;i<5;i++){
            //添加到顶部，所以加了一个0为index
            itemBeanList.add(0,new ItemBean(R.mipmap.ic_launcher,"........","Content...new"));
        }
    }
}
