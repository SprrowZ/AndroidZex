package com.dawn.zgstep.kotlins

/**
 * Create by rye
 * at 2020-06-09
 * @description: Kotlin扩展函数
 */

fun MutableList<Int>.exchange(fromIndex:Int,toIndex:Int){
    val tmp = this[fromIndex]
    this[fromIndex] = this[toIndex]
    this[toIndex] = tmp
}

fun TestKotlins.println(str:String){
    println(str)
}
val TestKotlins.nickName :String
get() = this.javaClass.name +".ORZ"


class Extendss{
    fun testExchange(){
        val list = mutableListOf<Int>(1,2)
        list.exchange(0,1)

        TestKotlins.println("zg")
    }
}