package com.rye.catcher.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.project.review.ItemBean;
import com.rye.catcher.project.review.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzg on 2017/10/10.
 */

public class LMFragment extends BaseFragment {
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
        setBarTitle("折戟沉沙");
    }
}
