package com.dawn.zgstep.design_patterns.other

/**
 * Create by rye
 * at 2020-11-27
 * @description: kotlin 委托模式 by
 * 小孩买东西，委托老爹付账
 */

interface Action {
    fun pay()
}
//没有啥作用的测试接口
interface ExtraActions {
    fun play()
}

class Father : Action {
    override fun pay() {
        println("老爹付账")
    }

}

class Child(father: Father) : ExtraActions, Action by father {
    override fun play() {
        println(".....")
    }

}

fun main() {
    val father = Father()
    val child = Child(father)
    child.pay()
}
