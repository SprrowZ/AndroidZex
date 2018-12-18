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
import com.rye.catcher.activity.adapter.ddmenu.ConstellationAdapter;
import com.rye.catcher.activity.adapter.ddmenu.CityAdapter;
import com.rye.catcher.activity.adapter.ddmenu.ListDropDownAdapter;
import com.rye.catcher.project.ctmviews.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CtmDDActivity extends BaseActivity {
    DropDownMenu dropDownMenu;
    private String headers[]={"城市","年龄","性别","星座"};
    private List<View> popViews=new ArrayList<>();
    private String citys[]={"不限","北京","上海","武汉","成都","广州","深圳","天津","西安","南京","杭州","郑州"};
    private String ages[]={"不限","18岁以下","18-22岁","23-26岁","27-35岁","35岁以上"};
    private String sexs[]={"不限","男","女"};
    private String constellations[]={"不限","白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座"};
    private  int[] imageIds={R.drawable.my1,R.drawable.my2,R.drawable.my3,R.drawable.my4};
    CityAdapter cityAdapter;
    ListDropDownAdapter sexAdapter;
    ListDropDownAdapter ageAdapter;
    ConstellationAdapter constellationAdapter;

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
        cityAdapter=new CityAdapter(Arrays.asList(citys),this);
        lvCity.setAdapter(cityAdapter);

        //
        RecyclerView  lvSex= new RecyclerView(this);
        lvSex.setLayoutManager(new LinearLayoutManager(this));
        lvSex.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        sexAdapter =new ListDropDownAdapter(this,Arrays.asList(sexs));
        lvSex.setAdapter(sexAdapter);
        //
        RecyclerView  lvage= new RecyclerView(this);
        lvage.setLayoutManager(new LinearLayoutManager(this));
        lvage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ageAdapter =new ListDropDownAdapter(this,Arrays.asList(ages));
        lvage.setAdapter(ageAdapter);
        //
        RecyclerView  constellation= new RecyclerView(this);
        constellation.setLayoutManager(new GridLayoutManager(this,4));
        constellationAdapter =new ConstellationAdapter(this,Arrays.asList(constellations));
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
