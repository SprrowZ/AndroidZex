package com.rye.catcher.agocode.bigimg;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.rye.catcher.R;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
private ViewPager viewPager;
//传进来的图片路径
private ArrayList<String> imgList;
public static  final  String  IMAGE_LIST="IMAGE";
//图片个数
private int mLength;

private ImagePagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        init();

    }
    private void init(){
        viewPager=findViewById(R.id.viewPager);
        imgList=getIntent().getStringArrayListExtra(IMAGE_LIST);
        mLength=imgList.size();
        mAdapter=new ImagePagerAdapter(this,imgList);
        viewPager.setAdapter(mAdapter);
    }
}
