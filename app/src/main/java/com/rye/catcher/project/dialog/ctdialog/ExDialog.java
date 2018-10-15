package com.rye.catcher.project.dialog.ctdialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.rye.catcher.R;

/**
 * Created at 2018/9/27.
 * 核心业务在controller里
 * @author Zzg
 */
public class ExDialog extends ExBaseDialog implements IDialog{

    private ExDialogController controller;
    private IDialog.OnBuilderListener builderListener;
    private static final  String FTag="dialogTag";
    public ExDialog(){
        controller=new ExDialogController(this);
    }

    @Override
    protected int getLayoutRes() {
        return controller.getLayoutRes();
    }

    @Override
    protected View getDialogView() {
        return controller.getDialogView();
    }
    @Override
    protected int getDialogWidth() {
        return controller.getDialogWidth();
    }

    @Override
    protected int getDialogHeight() {
        return controller.getDialogHeight();
    }

    @Override
    protected boolean isCancelableOutSide() {
        return controller.isCancelableOutside();
    }

    @Override
    public boolean isCancelable() {
        return controller.isCancelable();
    }

    @Override
    public float getDimAmount() {
        return controller.getDimAmount();
    }

    @Override
    protected int getGravity() {
        return controller.getGravity();
    }

    @Override
    protected int getAnimRes() {
        return controller.getAnimRes();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //设置默认子View布局
        controller.setChildView(view);
        if (builderListener!=null&&getLayoutRes()!=0&&getBaseView()!=null){
            builderListener.onBuilderChildView(this,getBaseView(),getLayoutRes());
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (controller!=null){
            controller=null;
        }
    }

    public static class  Builder{
        private ExDialogController.SYParams params;
        private IDialog.OnBuilderListener builderListener;
        public Builder(Context context){
            if (!(context instanceof Activity)){
                throw  new IllegalArgumentException("Context must be Activity");
            }
            params=new ExDialogController.SYParams();
            params.fragmentManager=((Activity) context).getFragmentManager();
            params.context=context;
        }

        /**
         * 设置DialogView
         * @param layoutRes
         * @return
         */
        public Builder setDialogView(@LayoutRes int layoutRes){
            params.layoutRes=layoutRes;
            return this;
        }

        /**
         * 设置DialogView
         * @param dialogView
         * @return
         */
        public Builder setDialogView(View dialogView){
            params.dialogView=dialogView;
            return this;
        }

        /**
         * 设置屏幕宽度百分比
         * @param percentage 0.0f~1.0f
         * @return
         */
        public Builder setScreenWidthP(float percentage){
            params.dialogWidth= (int) (getScreenWidth((Activity) params.context)*percentage);
            return this;
        }

        public Builder setScreenHeightP(float percentage){
            params.dialogHeight= (int) (getScreenHeight((Activity) params.context)*percentage);
        return this;
        }

        /**
         * 设置Dialog的宽度
         * @param width 宽度
         * @return Builder
         */
        public Builder setWidth(int width){
            params.dialogWidth=width;
            return this;
        }

        /**
         * 设置Dialog的高度
         * @param height
         * @return
         */
        public Builder setHeight(int height){
            params.dialogHeight=height;
            return this;
        }

        /**
         * 设置背景色色值
         * @param percentage 0.0f~1.0f 1.0f为完全不透明
         * @return
         */
        public Builder setWindowBackgroundP(float percentage){
            params.dimAmount=percentage;
            return this;
        }

        /**
         * 设置Gravity
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity){
            params.gravity=gravity;
            return this;
        }

        /**
         * 设置dialog外点击是否可以让dialog消失
         * @param cancelableOutSide
         * @return
         */
        public Builder setCancelableOutSide(boolean cancelableOutSide){
            params.isCancelableOutside=cancelableOutSide;
            return this;
        }

        /**
         * 设置是否屏蔽物理返回键
         * @param cancelable true 点击物理返回键可以让dialog消失
         * @return
         */
        public Builder setCancelable(boolean cancelable){
            params.cancelable=cancelable;
            return this;
        }

        /**
         * 构建子View的listener，自定义xml文件用
         * @param listener
         * @return
         */
        public Builder setBuildChildListener(IDialog.OnBuilderListener listener){
            this.builderListener=listener;
            return this;
        }

        /**
         * 设置dialog的动画效果
         * @param animStyle
         * @return
         */
        public Builder setAnimStyle(int animStyle){
            params.animRes=animStyle;
            return this;
        }

        /**
         * 设置默认右侧点击按钮
         * @param onClickListener
         * @return
         */
        public Builder setPositiveButton(IDialog.OnClickListener onClickListener){
            return  setPositiveButton("确定",onClickListener);
        }
        public Builder setPositiveButton(String btnStr,IDialog.OnClickListener onClickListener){
            params.positiveBtnListener=onClickListener;
            params.positiveStr=btnStr;
            params.showBtnRight=true;
            return this;
        }

        /**
         * 设置左侧按钮
         * @param onClickListener
         * @return
         */
        public Builder setNegativeButton(IDialog.OnClickListener onClickListener){
            return setNegativeButton("取消",onClickListener);
        }
        public Builder setNegativeButton(String btnStr,IDialog.OnClickListener onClickListener){
            params.negativeBtnListener=onClickListener;
            params.negativeStr=btnStr;
            params.showBtnLeft=true;
            return this;
        }

        /**
         * 设置默认dialog的title
         * @param title
         * @return
         */
        public Builder setTitle(String title){
            params.titleStr=title;
            return this;
        }

        /**
         * 设置默认dialog的content
         * @param content
         * @return
         */
        public  Builder setContent(String content){
            params.contentStr=content;
            return this;
        }

        private ExDialog create(){
            ExDialog dialog=new ExDialog();
            params.apply(dialog.controller);
            dialog.builderListener=builderListener;
            return dialog;
        }

        public ExDialog show(){
         if (params.layoutRes<=0&&params.dialogView==null){
             //如果没有设置布局，提供默认布局
             setDefaultOption();
         }
         ExDialog dialog=create();
         removePreDialog();
         dialog.show(params.fragmentManager,FTag);
         return dialog;
        }

        /**
         * 设置默认Dialog的配置
         */
        private void setDefaultOption(){
            params.cancelable=false;
            params.isCancelableOutside=false;
            params.gravity= Gravity.CENTER;
            params.layoutRes= R.layout.layout_dialog_new;
            params.dimAmount=0.5f;
            params.dialogWidth= (int) (getScreenWidth((Activity) params.context)*0.85f);
            params.dialogHeight= WindowManager.LayoutParams.WRAP_CONTENT;
        }
        /**
         * 移除之前的dialog
         */
      private void removePreDialog(){
          FragmentTransaction ft=params.fragmentManager.beginTransaction();
          Fragment prev=params.fragmentManager.findFragmentByTag(FTag);
          if (prev!=null){
              ft.remove(prev);
          }
          ft.commitAllowingStateLoss();
      }


    }

}
