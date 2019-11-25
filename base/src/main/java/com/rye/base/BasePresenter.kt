package com.rye.base


import android.annotation.SuppressLint
import com.rye.base.impl.HttpObserver

import com.rye.base.network.ServiceGenerator
import io.reactivex.Observable
import io.reactivex.Observer

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



/**
 *Created By RyeCatcher
 * at 2019/8/13
 */
open class BasePresenter<T> {
    protected var TAG: String? = null
    protected var view: IView? = null
    protected var apiService: T? = null

    constructor(service: Class<T>) {
        TAG = javaClass.simpleName
        apiService = ServiceGenerator.getService(service)
    }
    constructor(){

    }
    /**
     * Presenter绑定IView
     */
    fun bindView(view: IView) {
        this.view = view
    }

    /**
     * 处理请求
     */
    protected fun handleRequest(observable: Observable<T>, observer: Observer<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view?.bindToLifecycle())
                .subscribe(observer)
    }

    /**
     * 处理请求
     * 通过调用IView的接口，使得Presenter处理结果返回给
     * Activity/Fragment
     */
    protected fun handleRequest(action: String, observable: Observable<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view?.bindToLifecycle())
                .subscribe(object : HttpObserver<T>() {
                    override fun onStart() {
                        view?.onHttpStart(action, true)
                    }

                    override fun onSuccess(data: T?) {
                        view?.onHttpSuccess(action, data.toString())
                    }

                    override fun onError(code: Int, msg: String) {
                        view?.onHttpError(action, msg)
                    }

                    override fun onFinish() {
                        view?.onHttpFinish(action)
                    }
                })

    }

    fun <T> handleRequest(call: Call<T>,observer: Observer<T>){
//              observer.onSubscribe(null)
        call.enqueue(object :Callback<T>{
            override fun onResponse(call: retrofit2.Call<T>, response: Response<T>) {
               bindLifecycle(object :Consumer<Boolean>{
                   override fun accept(t: Boolean?) {
                       observer.onNext(response.body()!!)
                   }

               })
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                bindLifecycle(object :Consumer<Boolean>{
                    override fun accept(t: Boolean?) {
                        observer.onError(throwable)
                    }
                })
            }
        })
    }

    /**
     * 允许返回null，而不走onError
     * 这个是什么鬼操作？
     */
    protected fun <T> clearError(observable:Observable<T>,clz:Class<T>):Observable<T>{
         return observable.onErrorReturn(object :Function<Throwable,T>{
             override fun apply(t: Throwable): T {
                 return clz.newInstance()
             }
         })
         }







    private fun  bindLifecycle(consumer: Consumer<Boolean>){
        Observable.just(true)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view?.bindToLifecycle())
                .subscribe(consumer)
    }

    /**
     * 泛型方法，只有加了 <T> 的方法才叫泛型方法
     */
    fun <T> getService(tClass:Class<T>): T? {
            return ServiceGenerator.getService(tClass)
    }

    fun  onDestroy(){
        view=null
        apiService=null
    }

}