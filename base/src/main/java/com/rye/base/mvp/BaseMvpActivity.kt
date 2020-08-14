package com.rye.base.mvp

import android.os.Bundle
import com.rye.base.BaseActivity

/**
 * Create by rye
 * at 2020-08-06
 * @description:
 */
abstract class BaseMvpActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity(), BaseView {
    var mPresenter: P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    abstract fun createPresenter(): P
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}