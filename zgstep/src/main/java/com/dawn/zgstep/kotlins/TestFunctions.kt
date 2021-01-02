package com.dawn.zgstep.kotlins

import com.dawn.zgstep.others.tests.TestStatic

/**
 * Create by rye
 * at 2020/12/30
 * @description:
 */

//class Run<out T> {
//    fun fun1(param: T): T {
//        return param
//    }
//}
//class Runno<in T>{
//    fun fun2(param:T):T{
//        return param
//    }
//}

class TestInit{
    init {
        println("-----init---")
    }
}

fun main() {
    for (i in 0..10){
        val bean = TestInit()
        val bean2 = TestStatic()
    }
}