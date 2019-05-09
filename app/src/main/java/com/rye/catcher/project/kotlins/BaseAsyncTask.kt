package com.rye.catcher.project.kotlins

abstract  class BaseAsyncTask {
    lateinit var method:String

    //更新值
    fun method(methodName: String){
        this.method=methodName
    }
}