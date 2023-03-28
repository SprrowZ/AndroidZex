package com.dawn.zgstep.ui.fragment.assist;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
/**
 * Create by  [Rye]
 * <p>
 * at 2023/3/27 19:03
 */
public class CustomPageTransformer implements ViewPager.PageTransformer {
    private int mScaleOffsetX = 80;
    private int mScaleOffsetY = 30;
    private int mTranslationOffset = 30;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position <= 0) {
            page.setTranslationX(0f);
        } else {
            float pageWidth = page.getWidth();
            float tranX = -pageWidth*position + mTranslationOffset*position;
            page.setTranslationX(tranX);
            float scaleX = (pageWidth - mScaleOffsetX * position) / pageWidth;
            page.setScaleX(scaleX);

            float scaleY = (pageWidth - mScaleOffsetY * position) / pageWidth;
            page.setScaleY(scaleY);
        }
    }
}
