package com.example.myappsecond.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myappsecond.R;
import com.example.myappsecond.Review.BaseAdapterTest;
import com.example.myappsecond.Review.ItemBean;
import com.example.myappsecond.Review.MyAdapter;
import com.example.myappsecond.Review.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by changshuchao on 2017/10/10.
 */

public class Frd_Fragment extends Fragment {
    private ListView listView;
    List<ItemBean> itemBeanList;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.tab02,container,false);
        this.view=view;
        listView=view.findViewById(R.id.listView);
        //设置完接口后，在下面设置监听器

        itemBeanList=new ArrayList<>();
        for (int i=0;i<20;i++){
            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher,"Title...","Content..."));
        }
        listView.setAdapter(new MyAdapter(itemBeanList,getActivity()) );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
    }
}
