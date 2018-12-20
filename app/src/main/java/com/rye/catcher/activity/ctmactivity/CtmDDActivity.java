package com.rye.catcher.activity.ctmactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import com.rye.catcher.activity.adapter.ddmenu.DataSource;
import com.rye.catcher.activity.adapter.ddmenu.DropDownAdapter;


import com.rye.catcher.project.ctmviews.DropDownMenu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CtmDDActivity extends BaseActivity {
    DropDownMenu dropDownMenu;
    private String headers[]={"城市","年龄","性别","星座"};
    private List<View> popViews=new ArrayList<>();

    private  int[] imageIds={R.drawable.my1,R.drawable.my2,R.drawable.my3,R.drawable.my4};
    DropDownAdapter cityAdapter;
    DropDownAdapter sexAdapter;
    DropDownAdapter ageAdapter;
    DropDownAdapter constellationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drop_down_menu);
        dropDownMenu=findViewById(R.id.dropDownMenu);
        initViews();
    }
    private void initViews(){
        //
        RecyclerView  lvCity= new RecyclerView(this);
        lvCity.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter=new DropDownAdapter(DataSource.getInstance().getCityList(),this);
        lvCity.setAdapter(cityAdapter);

        //
        RecyclerView  lvSex= new RecyclerView(this);
        lvSex.setLayoutManager(new LinearLayoutManager(this));
        lvSex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        sexAdapter =new DropDownAdapter(DataSource.getInstance().getSexList(),this);
        lvSex.setAdapter(sexAdapter);
        //
        RecyclerView  lvage= new RecyclerView(this);
        lvage.setLayoutManager(new LinearLayoutManager(this));
        lvage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ageAdapter =new DropDownAdapter(DataSource.getInstance().getAgeList(),this);
        lvage.setAdapter(ageAdapter);
        //
        RecyclerView  constellation= new RecyclerView(this);
        constellation.setLayoutManager(new GridLayoutManager(this,4));
        constellation.setLayoutParams(new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        constellation.setBackgroundColor(getResources().getColor(R.color.white));
        constellationAdapter =new DropDownAdapter(DataSource.getInstance().getConstellationList(),
                this);
        constellation.setAdapter(constellationAdapter);
        //将四个View添加进来,顺序别搞反了
        popViews.add(lvCity);
        popViews.add(lvage);
        popViews.add(lvSex);
        popViews.add(constellation);

        //
        ImageView contentView=new ImageView(this);
        contentView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers),popViews,contentView);

    }




























}
