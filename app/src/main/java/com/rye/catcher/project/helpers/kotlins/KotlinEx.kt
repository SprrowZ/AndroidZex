package com.rye.catcher.project.helpers.kotlins
/**
 *Created by 18041at 2019/5/25
 *Function:练习kotlin高阶函数
 */

fun main(args:Array<String>){

    val kt= KotlinEx()
    kt.say("zzg",kt::hello)
    val sourceList= listOf(1,2,3,4)


    //forEach(sourceList)
    //  map(sourceList)
    //闭包测试
    var index=0
    val test= closure()
    while (index<20){
        test()

        index++
    }

}
 fun  forEach(sourceList:List<String>){
    //forEach,遍历集合
    val dataList= ArrayList<Int>()
    sourceList.forEach {
        val item=it.toInt()*2
        dataList.add(item)
    }
    //遍历输出
    dataList.forEach(::println)
}

 fun  map(sourceList: List<Int>){
     val newList= sourceList.map {
           it*2 +3
       }

     newList.map(Int::toString)
     newList.forEach(::println)
 }

/**
 * 闭包测试
 */
fun closure(): () -> Unit {
  var property=1

   fun test2(){
       property+=100
   }
    test2()
   return fun(){
       println(property++)
   }
 }

class testFirst{



}
//lambda,看似属性，实际上是个方法
val test={p1:Int,p2:Int->{
    println("property:$p1,$p2")
}}
class KotlinEx {
    fun hello(name: String):String{
        return "$name say:hello.everyOne!"
    }
    fun say(name:String,method:(na:String)->String){
        println(method(name))
    }

}