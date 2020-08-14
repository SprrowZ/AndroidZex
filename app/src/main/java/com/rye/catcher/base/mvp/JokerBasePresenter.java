package com.rye.catcher.base.mvp;

/**
 * Created by 18041at 2019/5/26
 * Function:
 */
public abstract class JokerBasePresenter<T extends JokerBaseView> {
   public T mView;
   public void attach(T mView){
       this.mView=mView;
   }

   public void  detach(){
       mView=null;
   }

    /**
     * 在Presenter中调用View的接口时,一定要加以判断;如果View为空,就不要调用V层
     * @return
     */
   public boolean isViewAttached(){
       return mView!=null;
   }

   public T getView(){
       return mView;
   }
}
