package com.rye.base.mvp

import java.lang.ref.WeakReference

/**
 * Create by rye
 * at 2020-08-06
 * @description:
 */
open class BasePresenter<V : BaseView> : BaseContract.IPresenter<V> {
    var mView: WeakReference<V>? = null
    override fun attachView(v: V) {
        mView = WeakReference(v)
    }

    override fun detachView() {
        mView = null
    }
}