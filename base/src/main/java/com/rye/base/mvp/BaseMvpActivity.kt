package com.rye.base.mvp

import android.os.Bundle
import com.rye.base.BaseActivity

/**
 * Create by rye
 * at 2020-08-06
 * @description: 使用此Mvp框架
 */
abstract class BaseMvpActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity(), BaseView {
    var presenter: P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.attachView(this as V)
    }

    abstract fun createPresenter(): P
    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }
}