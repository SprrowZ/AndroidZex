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

//【中缀函数】
infix fun String.appendEx(suffix: String): String {
    return this + suffix
}

//【接口扩展函数+泛型实化】
interface IInline {

}

inline fun <reified T> IInline.appendObject() { //接口扩展加泛型实化
    if (T::class.java == String.javaClass) {

    }
}

//【闭包测试】
class FunctionRecord {
    var token: String? = null
}

class TestFunctionRecord {
    private val functionList = mutableListOf<FunctionRecord>()

    private fun visitFunctionWidget(action: (record: FunctionRecord) -> Unit) {
        print("nothing...")
        functionList.forEach {
            action.invoke(it)
        }
    }

    fun test() {
        visitFunctionWidget {
            print(it.token)
        }
    }
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