package com.dawn.zgstep.others.kotlins

/**
 * Create by rye
 * at 2021/2/23f
 * @description: 用来测试kotlin中的一些特性
 */

/**
 * 参数中使用T.()，闭包内的this可以指定为对象，否则this代表当前类
 */
fun <T : String> T.getViewTag(f: T.() -> Boolean) {
    if (f.invoke(this)) {
        println(this)
    }
}

infix fun String.appendEx(suffix: String): String {
    return this + suffix
}

object RKtExtensionsTest {

    fun whyFight(str: String) {
        str.getViewTag {
            println("why you fight?")
            true
        }
    }

    fun testInfix(prefix: String, suffix: String) {
        val result = prefix appendEx suffix
        println(result)
    }
}

fun main(args: Array<String>) {
    RKtExtensionsTest.whyFight("For Freedom!!")
    RKtExtensionsTest.testInfix("I am", " Chinese")
}