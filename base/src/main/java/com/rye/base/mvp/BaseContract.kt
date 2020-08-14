package com.rye.base.mvp

/**
 * Create by rye
 * at 2020-08-06
 * @description:
 */
interface BaseContract{
    interface  IView
    interface  IPresenter<V>{
        fun attachView(v:V)
        fun detachView()
    }
}