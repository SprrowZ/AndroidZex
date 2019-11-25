package com.rye.catcher.project.mvp;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function: Presenter和Activity的中间层，
 * 接口里的方法要根据需求来
 */
public interface MvpView extends BaseView {
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data
     */
    void showData(String data);

}
