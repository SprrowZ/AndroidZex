package com.rye.catcher.project.helpers;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rye.catcher.R;

import android.widget.ImageView;

/**
 * Created at 2019/3/19.
 *
 * @author Zzg
 * @function: 设置bottomNavgationView子项图片大小
 */
public class BottomNavigationViewHelper {

    public static void setImageSize(BottomNavigationView view, int width, int height) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(R.id.icon);
                imageView.getLayoutParams().width = width;
                imageView.getLayoutParams().height = height;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**********************如果想改变底部栏高度***********************/
             //在dimen中添加
//<dimen name="design_bottom_navigation_height">60dp</dimen>

}
