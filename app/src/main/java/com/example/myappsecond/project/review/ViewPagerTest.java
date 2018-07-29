package com.example.myappsecond.project.review;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.example.myappsecond.R;
import com.example.myappsecond.project.Viewpager.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by ZZG on 2017/10/26.
 */

public class ViewPagerTest extends Activity {
    private List<View> dataList;
    private ViewPager view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        initView();
    }

    private void initView() {
        view=findViewById(R.id.view);
        View view1= View.inflate(ViewPagerTest.this, R.layout.viewpager_first,null);
        View view2= View.inflate(ViewPagerTest.this, R.layout.viewpager_second,null);
        View view3= View.inflate(ViewPagerTest.this, R.layout.viewpager_third,null);
        View view4= View.inflate(ViewPagerTest.this, R.layout.viewpager_fourth,null);
        //不要忘了实例化datalist
        dataList=new ArrayList<View>();
        dataList.add(view1);
        dataList.add(view2);
        dataList.add(view3);
        dataList.add(view4);
        MyPagerAdapter myadapter=new MyPagerAdapter(dataList);
        view.setAdapter(myadapter);
        Toast.makeText(this,"ddd",Toast.LENGTH_SHORT).show();
    }
}
