package com.rye.catcher.project.kotlins

/**
 *Created by 18041at 2019/5/25
 *Function:练习kotlin高阶函数
 */

fun main(args:Array<String>){

    val kt=KotlinEx()
    kt.say("zzg",kt::hello)
    forEach()
}
 fun  forEach(){
    //forEach,遍历集合
    val sourceList= listOf("1","2","3","4")
    val dataList= ArrayList<Int>()
    sourceList.forEach {
        val item=it.toInt()*2
        dataList.add(item)
    }
    //遍历输出
    dataList.forEach(::println)
}
 fun  map(){

 }



class testFirst{



}
class KotlinEx {
    fun hello(name: String):String{
        return "$name say:hello.everyOne!"
    }
    fun say(name:String,method:(na:String)->String){
        println(method(name))
    }
}