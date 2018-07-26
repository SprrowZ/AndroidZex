package com.example.myappsecond.customViews;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myappsecond.R;

import java.util.Random;

/**
 * Created by ZZG on 2018/4/26.
 */

public class Custom_Twelfths extends Activity {
    private static int IMG_COUNT = 5;
    private WaterFallLayout waterFallLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_waterfalllayout);
        waterFallLayout=findViewById(R.id.waterfallLayout);
//        waterFallLayout.setOnItemClickListener(new Custom_WaterFallLayout.OnItemClickListener() {
//            @Override
//            public void onItemClick() {
//                Toast.makeText(Custom_Twelfths.this,"full...",Toast.LENGTH_SHORT).show();
//            }
//        });
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           addView(waterFallLayout);
            }
        });


        
    }

    private void addView(WaterFallLayout waterFallLayout) {
        Random random = new Random();
        Integer num = Math.abs(random.nextInt());
        WaterFallLayout.LayoutParams layoutParams = new WaterFallLayout.LayoutParams(WaterFallLayout.LayoutParams.WRAP_CONTENT,
                WaterFallLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(this);
        if (num % IMG_COUNT == 0) {
            imageView.setImageResource(R.drawable.my1);
        } else if (num % IMG_COUNT == 1) {
            imageView.setImageResource(R.drawable.my2);
        } else if (num % IMG_COUNT == 2) {
            imageView.setImageResource(R.drawable.my3);
        } else if (num % IMG_COUNT == 3) {
            imageView.setImageResource(R.drawable.my4);
        } else if (num % IMG_COUNT == 4) {
            imageView.setImageResource(R.drawable.my5);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        waterFallLayout.addView(imageView, layoutParams);

        waterFallLayout.setOnItemClickListener(new com.example.myappsecond.customViews.WaterFallLayout.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(Custom_Twelfths.this, "item=" , Toast.LENGTH_SHORT).show();
            }

        });
    }


}
