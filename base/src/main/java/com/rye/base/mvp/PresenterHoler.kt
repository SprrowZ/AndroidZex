package com.rye.base.mvp

import com.rye.base.BasePresenter
import com.rye.base.IView
import java.util.*
import kotlin.collections.HashMap

/**
 *Created By RyeCatcher
 * at 2019/8/13
 * 创建和缓存Presenter实例
 */
class PresenterHoler {
   private lateinit var presenterMap: HashMap<String,BasePresenter<*>>
    private lateinit var view:IView
    constructor(view:IView){
        this.view=view
        presenterMap= HashMap()
    }

    /**
     * 获取一个指定类型的Presenter实例，没有则创建一个
     */
    fun <P:BasePresenter<*>> getPresenter(clz:Class<P>): P? {
        var presenter: P? =null
        if (presenterMap.containsKey(clz.name)){
            presenter=presenterMap.get(clz.name) as P
        }else{
            try {
                presenter=clz.newInstance()
                //presenter绑定view， 是上面的不需要存了
                presenter.bindView(view)
                presenterMap.put(clz.name,presenter)
            }catch (e:InstantiationError){
                e.printStackTrace()
            }catch (e:IllegalAccessException){
                e.printStackTrace()
            }
        }
        return presenter
    }

    /**
     * 清空presenter实例
     */
    fun onDestroy(){
        for (key in presenterMap.keys){
            presenterMap.get(key)?.onDestroy()
        }
    }
}