package com.rye.catcher.activity.splash

import android.content.Context
import com.rye.base.mvp.BasePresenter
import com.rye.catcher.R
import com.rye.catcher.utils.permission.PermissionUtils
import com.yanzhenjie.permission.Permission


/**
 * Create by rye
 * at 2020-08-06
 * @description:
 */
class SplashPresenter : BasePresenter<SplashContract.ISplashView>(), SplashContract.ISplashPresenter {
    override fun applyAuthority(context: Context) {
        PermissionUtils.requestPermission(context, context.getString(R.string.need_authority),
                false, mView?.get()?.jumpPage(), 0,
                Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION)//CALL_PHONE先去掉

    }
}