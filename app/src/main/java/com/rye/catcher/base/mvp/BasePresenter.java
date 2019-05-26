package com.rye.catcher.base.mvp;

/**
 * Created by 18041at 2019/5/26
 * Function:
 */
public abstract class BasePresenter<T extends BaseView> {
   public T mView;
   public void attach(T mView){
       this.mView=mView;
   }
   public void  detach(){
       mView=null;
   }
}
