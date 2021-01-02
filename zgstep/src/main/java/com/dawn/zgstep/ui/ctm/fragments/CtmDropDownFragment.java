package com.dawn.zgstep.ui.ctm.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawn.zgstep.R;
import com.dawn.zgstep.ui.ctm.simple.dropdown.DataSource;
import com.dawn.zgstep.ui.ctm.simple.dropdown.DropDownAdapter;
import com.dawn.zgstep.ui.ctm.views.DropDownMenu;
import com.rye.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by rye
 * at 2020-09-07
 *
 * @description:
 */
public class CtmDropDownFragment extends BaseFragment {
    private Context mContext;
    @Override
    protected int getLayoutId() {
        return R.layout.drop_down_menu;
    }

    DropDownMenu dropDownMenu;
    private String headers[] = {"城市", "年龄", "性别", "星座"};
    private List<View> popViews = new ArrayList<>();


    DropDownAdapter cityAdapter;
    DropDownAdapter sexAdapter;
    DropDownAdapter ageAdapter;
    DropDownAdapter constellationAdapter;

    @Override
    public void initEvent() {
        mContext =getContext();
        dropDownMenu = mRoot.findViewById(R.id.dropDownMenu);
        RecyclerView lvCity = new RecyclerView(mContext);
        lvCity.setLayoutManager(new LinearLayoutManager(mContext));
        cityAdapter = new DropDownAdapter(DataSource.getInstance().getCityList(), mContext);
        lvCity.setAdapter(cityAdapter);

        //
        RecyclerView lvSex = new RecyclerView(mContext);
        lvSex.setLayoutManager(new LinearLayoutManager(mContext));
        lvSex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        sexAdapter = new DropDownAdapter(DataSource.getInstance().getSexList(), mContext);
        lvSex.setAdapter(sexAdapter);
        //
        RecyclerView lvage = new RecyclerView(mContext);
        lvage.setLayoutManager(new LinearLayoutManager(mContext));
        lvage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ageAdapter = new DropDownAdapter(DataSource.getInstance().getAgeList(), mContext);
        lvage.setAdapter(ageAdapter);
        //
        RecyclerView constellation = new RecyclerView(mContext);
        constellation.setLayoutManager(new GridLayoutManager(mContext, 4));
        constellation.setLayoutParams(new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        constellation.setBackgroundColor(getResources().getColor(R.color.white));
        constellationAdapter = new DropDownAdapter(DataSource.getInstance().getConstellationList(),
                mContext);
        constellation.setAdapter(constellationAdapter);
        //将四个View添加进来,顺序别搞反了
        popViews.add(lvCity);
        popViews.add(lvage);
        popViews.add(lvSex);
        popViews.add(constellation);

        //
        ImageView contentView = new ImageView(mContext);
        contentView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popViews, contentView);
    }
}
