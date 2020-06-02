package com.dawn.zgstep.kotlins

/**
 * Create by rye
 * at 2020-05-22
 * @description: kotlin面向对象之接口测试
 */
interface IktListener {
    //看似属性，实则方法
    val result: String

    fun onSuccess(res: String)
    val name
        get() = "Jack"
}