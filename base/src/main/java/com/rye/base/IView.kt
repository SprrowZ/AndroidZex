package com.rye.base

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * 继承自RxLifecycle里的接口--解决内存泄漏
 */
interface IView:LifecycleProvider<ActivityEvent>{
    /**
     * 请求开始
      */
    fun onHttpStart(action:String,isShowLoading:Boolean)
    /**
     * 响应成功
     */
    fun onHttpSuccess(action:String,result:Any)
    /**
     * 请求出错
     */
    fun onHttpError(action:String,errorMsg:String)
    /**
     * 请求结束
     */
    fun onHttpFinish(action:String)
}