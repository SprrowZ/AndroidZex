package com.rye.catcher.utils.ExtraUtil;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZZG on 2017/11/17.用处还是蛮大的
 */

public class CommonPagerAdapter_d extends PagerAdapter {
    //图片数量
    private static int Num;
    private List<View> viewList;
    //图片数组
    int [] ResId=new int[Num];


    public CommonPagerAdapter_d(List<View> viewList, int [] ResId){
        this.viewList=viewList;
        this.ResId=ResId;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
                View view=viewList.get(position);
                view.setBackgroundResource(ResId[position]);
                container.addView(viewList.get(position));
//        Bitmap bitmap=BitmapFactory.decodeResource(container.getResources(),2);
//        bitmap.recycle();
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(viewList.get(position));
    }


}
