package com.rye.catcher.activity.ctmactivity;


import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.rye.base.BaseActivity;

import com.rye.catcher.R;
import com.rye.catcher.project.ctmviews.WaterFallLayout;

import java.util.Random;

/**
 * Created by ZZG on 2018/4/26.
 */

public class CtmWaterFallActivity extends BaseActivity {
    private static int IMG_COUNT = 5;
    private WaterFallLayout waterFallLayout;


    public static void start(Context context){
        Intent intent = new Intent();
        intent.setClass(context, CtmWaterFallActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.bcustom_waterfalllayout;
    }

    @Override
    public void initEvent() {
        waterFallLayout = findViewById(R.id.waterfallLayout);
        findViewById(R.id.add_btn).setOnClickListener(v -> addView(waterFallLayout));
    }

    private void addView(WaterFallLayout waterFallLayout) {
        Random random = new Random();
        Integer num = Math.abs(random.nextInt());
        WaterFallLayout.LayoutParams layoutParams = new WaterFallLayout.LayoutParams(WaterFallLayout.LayoutParams.WRAP_CONTENT,
                WaterFallLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(this);
        if (num % IMG_COUNT == 0) {
            imageView.setImageResource(R.mipmap.my3);
        } else if (num % IMG_COUNT == 1) {
            imageView.setImageResource(R.mipmap.my2);
        } else if (num % IMG_COUNT == 2) {
            imageView.setImageResource(R.mipmap.my3);
        } else if (num % IMG_COUNT == 3) {
            imageView.setImageResource(R.mipmap.my4);
        } else if (num % IMG_COUNT == 4) {
            imageView.setImageResource(R.mipmap.my5);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        waterFallLayout.addView(imageView, layoutParams);

        waterFallLayout.setOnItemClickListener(new com.rye.catcher.project.ctmviews.WaterFallLayout.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(CtmWaterFallActivity.this, "item=", Toast.LENGTH_SHORT).show();
            }

        });
    }


}
