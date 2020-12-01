package com.rye.base.rxmvp

import kotlin.collections.HashMap

/**
 *Created By RyeCatcher
 * at 2019/8/13
 * 创建和缓存Presenter实例
 */
class PresenterHoler {
    private var presenterMapRx: HashMap<String, RxBasePresenter<*>>
    private var viewRx: RxIView

    constructor(viewRx: RxIView) {
        this.viewRx = viewRx
        presenterMapRx = HashMap()
    }

    /**
     * 获取一个指定类型的Presenter实例，没有则创建一个
     */
    fun <P : RxBasePresenter<*>> getPresenter(clz: Class<P>): P? {
        var presenter: P? = null
        if (presenterMapRx.containsKey(clz.name)) {
            presenter = presenterMapRx.get(clz.name) as P
        } else {
            try {
                presenter = clz.newInstance()
                //presenter绑定view， 是上面的不需要存了
                presenter.bindView(viewRx)
                presenterMapRx.put(clz.name, presenter)
            } catch (e: InstantiationError) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        return presenter
    }

    /**
     * 清空presenter实例
     */
    fun onDestroy() {
        for (key in presenterMapRx.keys) {
            presenterMapRx.get(key)?.onDestroy()
        }
    }
}