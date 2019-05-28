package com.rye.catcher.project.kotlins

/**
 *Created by 18041at 2019/5/25
 *Function:练习kotlin高阶函数
 */

fun main(args:Array<String>){

    val kt=KotlinEx()
    kt.say("zzg",kt::hello)
}
class KotlinEx {
    fun hello(name: String):String{
        return "$name say:hello.everyOne!"
    }
    fun say(name:String,method:(na:String)->String){
        println(method(name))
    }
}