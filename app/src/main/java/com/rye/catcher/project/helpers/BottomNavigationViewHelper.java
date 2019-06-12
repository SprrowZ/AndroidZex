package com.rye.catcher.project.helpers;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
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
                ImageView imageView = item.findViewById(android.support.design.R.id.icon);
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
