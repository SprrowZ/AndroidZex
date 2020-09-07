package com.rye.catcher.activity.splash

import com.rye.base.mvp.BaseMvpActivity
import com.rye.catcher.R
import com.rye.catcher.activity.MainActivity
import com.yanzhenjie.permission.Action
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Create by rye
 * at 2020-08-06
 * @description:
 */
class SplashActivityEx : BaseMvpActivity<SplashContract.ISplashView, SplashPresenter>(), SplashContract.ISplashView {
    override fun createPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.app_splash
    }

    override fun initEvent() {
        presenter?.applyAuthority(this)
    }

    override fun jumpPage(): Action<MutableList<String>>? { //View回调
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe { aLong -> MainActivity.start(this) }
        return null
    }


}