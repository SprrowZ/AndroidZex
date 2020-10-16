package com.rye.catcher.activity.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rye.catcher.BaseOldFragment;
import com.rye.catcher.R;
import com.rye.catcher.project.review.refresh.ItemBean;
import com.rye.catcher.project.review.refresh.RefreshAdapter;
import com.rye.catcher.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zzg on 2017/10/10.
 */

public class LMFragment extends BaseOldFragment {

    List<ItemBean> itemBeanList;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.adaption)
    TextView adaption;
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
        listView.setAdapter(new RefreshAdapter(itemBeanList,getActivity()) );
    if (adaption!=null&&adaption.getVisibility()== View.VISIBLE){//360dp设备

    }else{
        ToastUtils.shortMsg("非Small-Width:360dp");

        getResources().getBoolean(R.bool.has_two_panes);
    }

    }


}
