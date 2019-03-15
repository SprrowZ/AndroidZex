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

import butterknife.BindView;

/**
 * Created by zzg on 2017/10/10.
 */

public class LMFragment extends BaseFragment {

    List<ItemBean> itemBeanList;
    @BindView(R.id.listView)
    ListView listView;
    @Override
    protected int getLayoutResId() {
        return R.layout.tab02;
    }

    @Override
    protected void initData() {
        setBarTitle("折戟沉沙");
        itemBeanList=new ArrayList<>();
        for (int i=0;i<20;i++){
            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher,"Title...","Content..."));
        }
        listView.setAdapter(new MyAdapter(itemBeanList,getActivity()) );
    }


}
