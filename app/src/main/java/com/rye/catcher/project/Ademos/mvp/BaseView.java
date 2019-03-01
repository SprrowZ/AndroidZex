package com.rye.catcher.project.Ademos.mvp;

import android.content.Context;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function:
 */
public interface BaseView {
    /**
     * 显示正在加载View
     */
    void showLoading();

    /**
     * 关闭正在加载View
     */
    void hideLoading();

    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showError();

    /**
     * 获取上下文
     * @return
     */
    Context getContext();
}
