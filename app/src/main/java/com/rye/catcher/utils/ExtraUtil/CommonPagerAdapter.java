package com.rye.catcher.utils.ExtraUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ZZG on 2017/11/17.用处还是蛮大的
 */

public class CommonPagerAdapter extends PagerAdapter {
    //图片数量
    private static int Num;
    private List<ImageView> viewList;
    //图片数组
    int [] ResId=new int[Num];

    public CommonPagerAdapter(List<ImageView> viewList, int [] ResId){
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

        ImageView view=viewList.get(position);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;
        //options.inPreferredConfig=Bitmap.Config.ARGB_4444;
        Bitmap   bitmap=BitmapFactory.decodeResource(container.getResources(),ResId[position],options);

      //设置尺寸
      // LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) view.getLayoutParams();
     // layoutParams.width= 500;
      // layoutParams.height= 500;
       // view.setLayoutParams(layoutParams);
        //加载bitmap

        view.setImageBitmap(bitmap);
        //Toast.makeText(container.getContext(),String.valueOf(bitmap.getHeight()),Toast.LENGTH_SHORT).show();
        Log.i("height========>", String.valueOf(bitmap.getHeight()));
        container.addView(viewList.get(position));

        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(viewList.get(position));
    }


}
