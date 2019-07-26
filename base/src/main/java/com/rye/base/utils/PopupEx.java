package com.rye.base.utils;

import android.app.Activity;
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
    private int mOffsetX = 0;
    private int mOffsetY = 0;

    //位置
    private int mGravity = Gravity.BOTTOM;
    //根布局
    private View mView;
    //与ExitText混用
    private int mInputMethodMode = PopupWindow.INPUT_METHOD_FROM_FOCUSABLE;
    private int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
    //
    private View mAnchorView;
    private View mParentView;
    //
    private float alphaDim;
    private boolean cancelOutside=false;
    public PopupEx(Context context, @LayoutRes int layout, int backgroundColor,
                    int gravity, int anim, int width, int height,
                   View archorView,View parentView,float alpha,boolean cancelOutside) {
        this.mContext = context;
        this.mLayout = layout;
        this.mBackgroundColor=backgroundColor;

        this.mGravity=gravity;
        this.anim=anim;
        this.mWidth=width;
        this.mHeight=height;
        this.mAnchorView=archorView;
        this.mParentView=parentView;
        this.alphaDim=alpha;
        this.cancelOutside=cancelOutside;
        init();
    }

    private void init() {
        initView();
        unitedSettings();
        if (anim != -1) {
            mPopupWindow.setAnimationStyle(anim);
        }
        //设置背景色，设置了可点击外部不消失，不设置点击外部不消失
      // mPopupWindow.setBackgroundDrawable(new ColorDrawable(mBackgroundColor));

        setLocation();
        handleDim();

    }

    /**
     *设置位置
     */
    private void setLocation() {
        if (mParentView != null) {
            mPopupWindow.showAtLocation(mParentView, mGravity, mOffsetX, mOffsetY);
            return;
        }
        if (mAnchorView != null) {
            mPopupWindow.showAsDropDown(mAnchorView, mOffsetX, mOffsetY, mGravity);
        }

    }

    /**
     * 设置背景不透明度
     */
    private void handleDim(){
        if (alphaDim!=0&&mContext!=null){
            if (alphaDim>1) alphaDim=1;
            WindowManager.LayoutParams lp=((Activity)mContext).getWindow().getAttributes();
            lp.alpha=alphaDim;
            ((Activity)mContext).getWindow().setAttributes(lp);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                  handleDismiss();
                }
            });
        }

    }
   private void handleDismiss(){
       WindowManager.LayoutParams lp=((Activity)mContext).getWindow().getAttributes();
       lp.alpha=1.0f;
       ((Activity)mContext).getWindow().setAttributes(lp);
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

    }

    private void unitedSettings() {
        //焦点给popup，防止点击穿透
        mPopupWindow.setFocusable(true);
        //todo 待处理与输入框之间的关系
        mPopupWindow.setInputMethodMode(mInputMethodMode);
        mPopupWindow.setSoftInputMode(mSoftInputMode);
        //
        mPopupWindow.setOutsideTouchable(true);
//        //点击事件处理
        mPopupWindow.setTouchable(true);

    }

    public View getView() {
        if (mView != null) {
            return mView;
        } else {
            throw new IllegalStateException("mView should be null ");
        }
    }
    public boolean isShowing(){
        if (mPopupWindow!=null && mPopupWindow.isShowing()){
            return true;
        }
        return false;
    }
    public void dismiss(){
        if (mPopupWindow==null) return;
          mPopupWindow.dismiss();
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
        private int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        private int mGravity = Gravity.BOTTOM;

        private View parentView;
        private View archorView;

        private float alphaDim;

        private boolean outCancel;
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



        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setParentView(View parentView,int gravity) {
            this.parentView = parentView;
            this.mGravity=gravity;
            return this;
        }
        public Builder setParentView(View parentView ) {
            this.parentView = parentView;
            return this;
        }
        public Builder setArchorView(View archorView,int gravity) {
            this.archorView = archorView;
            this.mGravity=gravity;
            return this;
        }
        public Builder setDim(float alpha){
            this.alphaDim=alpha;
            return this;
        }
        public Builder outCancel(boolean cancel){
            this.outCancel=cancel;
            return this;
        }
//        public Builder showAsDrowDown(View archorView ){
//             if (parentView!=null||archorView==null) return this;
//
//        }

        public PopupEx create() {
            return new PopupEx(mContext, mLayout, mBackgroundColor,
                     mGravity, mAnim, mWidth, mHeight,
                    archorView,parentView,alphaDim,outCancel);
        }
    }
}
