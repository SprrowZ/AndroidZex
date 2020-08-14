package com.rye.catcher.activity.splash

import android.content.Context
import com.rye.base.mvp.BaseContract
import com.rye.base.mvp.BaseView
import com.yanzhenjie.permission.Action


/**
 * Create by rye
 * at 2020-08-06
 * @description:
 */
interface SplashContract {
    interface ISplashView : BaseView {
        fun jumpPage(): Action<MutableList<String>>?
    }

    interface ISplashPresenter : BaseContract.IPresenter<ISplashView> {
        fun applyAuthority(context: Context)
    }
}