package com.example.myappsecond.Project.Viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.myappsecond.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 2017/10/25.
 */

public class helloActivity extends Activity {
    private ImageView close;
    private ViewPager view;
    private List<View> dataList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        view=findViewById(R.id.view);
        View view1= View.inflate( this, R.layout.viewpager_first,null);
        View view2= View.inflate(this, R.layout.viewpager_second,null);
        View view3= View.inflate(this, R.layout.viewpager_third,null);
        View view4= View.inflate(this, R.layout.viewpager_fourth,null);
        //不要忘了实例化datalist
        dataList=new ArrayList<View>();
        dataList.add(view1);
        dataList.add(view2);
        dataList.add(view3);
        dataList.add(view4);
        MyPagerAdapter myadapter=new MyPagerAdapter(dataList);
        view.setAdapter(myadapter);

    }
}
