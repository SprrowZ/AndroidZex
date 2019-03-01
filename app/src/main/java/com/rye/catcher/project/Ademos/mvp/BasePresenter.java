package com.rye.catcher.project.Ademos.mvp;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function: Presenter基类
 */
public class BasePresenter<V extends BaseView> {
    private V mvpView;
    /**
     * 绑定View，一般在初始化的时候调用该方法
     * @param mvpView
     */
    public void attachView(V mvpView){
        this.mvpView=mvpView;
    }
    /**
     * 断开View，一般在onDestroy中调用
     */
    public void detachView(){
        this.mvpView=null;
    }
    /**
     * 是否与View建立连接（防止Activity已经被销毁，而Presenter不知道还去调MvpView的情况）
     * @return
     */
    public boolean isViewAttached(){
        return mvpView!=null;
    }

    public V getView(){
        return mvpView;
    }
}
