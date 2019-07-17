package com.rye.base.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created By user
 * at 2019/7/17
 * 建造者模式封装PopupWindow
 */
public class PopupEx {
    private Context mContext;
    @LayoutRes
    private int layout;
    @AnimRes
    private int anim;
    @ColorInt
    private int backgroundColor;

    PopupEx(Context context){
      this.mContext=context;
      init();
    }
    private void init(){
        View view= LayoutInflater.from(mContext).inflate(layout,null,false);
        final PopupWindow popupWindow=new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(anim);
        //点击事件处理
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //设置背景色
        popupWindow.setBackgroundDrawable(new ColorDrawable(backgroundColor));
        popupWindow.showAtLocation(mContext, Gravity.BOTTOM,0,0);
    }

    /**
     * 设置布局
     * @param layout
     */
    public void setLayout(@LayoutRes int layout){
         this.layout=layout;
    }

    /**
     * 设置背景色
     * @param color
     */
    public void setBackgroundColor(@ColorInt int color){
        this.backgroundColor=color;
    }

    /**
     * 设置动画
     * @param anim
     */
    public void setAnim(@AnimRes int anim){
        this.anim=anim;
    }


    public static final class Builder{
        private Context mContext;
        public Builder(Context context){
         this.mContext=context;
        }
        private PopupEx popup=new PopupEx(mContext);
        /**
         * 设置布局
         * @param layout
         */
        public void setLayout(@LayoutRes int layout){
            popup.layout=layout;
        }

        /**
         * 设置背景色
         * @param color
         */
        public void setBackgroundColor(@ColorInt int color){
            popup.backgroundColor=color;
        }

        /**
         * 设置动画
         * @param anim
         */
        public void setAnim(@AnimRes int anim){
            popup.anim=anim;
        }

        public PopupEx create(){
            return popup;
        }
    }
}
