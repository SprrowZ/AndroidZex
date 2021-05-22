package com.dawn.zgstep.others.kotlins.apis

import com.dawn.zgstep.others.demos.reflex.Foo

/**
 * Create by rye
 * at 2021/5/15
 * @description:
 */
class DemoRouter {
    val DEFAULT = "default"

    //自定义操作符
    operator fun <T : Any> get(clazz: Class<T>, name: String = DEFAULT): T? {
        //拿到class进行具体的逻辑，比如添加缓存，key为clazz，先从缓存表中获取实例
        return clazz.newInstance()
    }

    //内联函数，以及在编译时获取泛型类型关键字：reified
    inline operator fun <reified T : Any> get(key: String = DEFAULT): T? = this[T::class.java, key]
}

class DemoData {
    fun out() {
        println("create success!")
    }
}

/**
 * 自定义操作符|运算符重载
 * 下面这些名字是固定的！
 * ---二元运算符---
 * times  :  a*b
 * div    :  a/b
 * rem    :  a%b
 * plus   :  a+b
 * minus  :  a-b
 *  ----复合赋值运算符---
 *  timesAssign  :  a+=b
 *  divAssign    :  a/=b
 *  remAssign    :  a%=b
 *  plusAssign   :  a+=b
 *  minusAssign  :  a-=b
 *  ---还有一元运算符---
 *  unaryPlus  :  +a
 *  unaryMinus :  -a
 *  not        :  !a
 *  inc        :  ++a ,a++
 *  dec        :  --a ,a--
 *  还有其他的，比如比较运算符、排序运算符、
 */
class Pointer(var x: Int, var y: Int) {
    operator fun plus(other: Pointer): Pointer = Pointer(x + other.x, y + other.y)
    operator fun times(other: Pointer): Pointer = Pointer(x * other.x, y * other.y)
}

class Test {
    fun testInlineDemo() {
        val demo2 = DemoRouter()
        demo2[DemoData::class.java, "default"]
        //单例用法
//        val demoData = DemoRouter[DemoData::class.java, DEFAULT]
//        demoData?.out()
    }

    fun testOperator() {
        val source = Pointer(1, 2)
        val target = Pointer(3, 4)
        val addResult = source + target
        val timesResult = source * target

    }


}