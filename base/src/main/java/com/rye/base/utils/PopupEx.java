package com.rye.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created By user
 * at 2019/7/17
 * 建造者模式封装PopupWindow
 */
public class PopupEx {
    private static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    private Context mContext;
    //需配置的PopupWindow
    private PopupWindow mPopupWindow;
    @LayoutRes
    private int mLayout = -1;
    @AnimRes
    private int anim = -1;
    //背景色
    @ColorInt
    private int mBackgroundColor = Color.TRANSPARENT;
    private int mWidth;
    private int mHeight;
    private int mOffsetX=0;
    private int mOffsetY=0;

    //
    private boolean focusable = true;
    //位置
    private int mGravity=Gravity.BOTTOM;
    //根布局
    private View mView;
    //与ExitText混用
    private int mInputMethodMode = PopupWindow.INPUT_METHOD_FROM_FOCUSABLE;
    private int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
   //
    private View mAnchorView;
    private View mParentView;




    public PopupEx(Context context, @LayoutRes int layout, int mBackgroundColor,
                   boolean focusable, int gravity, int anim, int mWidth, int mHeight) {
        this.mContext = context;
        this.mLayout = layout;
        init();
    }

    private void init() {
        initView();
        unitedSettings();
        if (anim != -1) {
            mPopupWindow.setAnimationStyle(anim);
        }
        //设置背景色
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(mBackgroundColor));

        setLocation();
    }
    private void setLocation(){
        if (mParentView!=null){
            mPopupWindow.showAtLocation(mParentView, mGravity, mOffsetX, mOffsetY);
        }
        if (mAnchorView!=null&&mParentView==null){
            mPopupWindow.showAsDropDown(mAnchorView,mOffsetX,mOffsetY,mGravity);
        }
    }
    private void showAtLocation(){

    }

    private void initView() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow();
        }
        if (mLayout != -1 && mContext != null) {//如果需要设置布局的话
            mView = LayoutInflater.from(mContext).inflate(mLayout, null, false);
            mPopupWindow.setContentView(mView);
        } else {
            throw new IllegalArgumentException("Context or Layout must be not null!");
        }
        if (mWidth > 0 || mWidth == MATCH_PARENT
                || mWidth == WRAP_CONTENT) {
            mPopupWindow.setWidth(mWidth);
        } else {
            mPopupWindow.setWidth(WRAP_CONTENT);
        }
        if (mHeight > 0 || mHeight == MATCH_PARENT
                || mHeight == WRAP_CONTENT) {
            mPopupWindow.setHeight(mHeight);
        } else {
            mPopupWindow.setHeight(WRAP_CONTENT);
        }
        // TODO: 2019/7/19 测量真实宽高再设置
        mPopupWindow.setInputMethodMode(mInputMethodMode);
        mPopupWindow.setSoftInputMode(mSoftInputMode);
    }

    private void unitedSettings() {
        mPopupWindow.setFocusable(focusable);
        //点击事件处理
        mPopupWindow.setTouchable(true);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public View getView() {
        if (mView != null) {
            return mView;
        } else {
            throw new IllegalStateException("mView should be null ");
        }
    }

    //
    public static final class Builder {
        private Context mContext;
        private @LayoutRes
        int mLayout;
        private @ColorInt
        int mBackgroundColor;
        private @AnimRes
        int mAnim;
        private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        private boolean focusable = true;
        private int mGravity = Gravity.BOTTOM;

        public Builder() {

        }

        public Builder setContextView(Context context, @LayoutRes int layout) {
            this.mContext = context;
            this.mLayout = layout;
            return this;
        }


        /**
         * 设置背景色
         *
         * @param color
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.mBackgroundColor = color;
            return this;
        }

        /**
         * 设置动画
         *
         * @param anim
         */
        public Builder setAnim(@AnimRes int anim) {
            this.mAnim = anim;
            return this;
        }

        /**
         * 设置位置
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

//        public Builder showAsDrowDown(View ){
//
//        }

        public PopupEx create() {
            return new PopupEx(mContext, mLayout, mBackgroundColor,
                    focusable, mGravity, mAnim, mWidth, mHeight);
        }
    }
}
