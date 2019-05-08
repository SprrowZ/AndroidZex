package com.rye.catcher.project.kotlins

import com.rye.catcher.project.kotlins.CoderSon.CO.doProgramming
import java.util.*

/**
 *Created by 18041at 2019/5/3
 *Function:
 */
  const val TEST_PROPERTY="test"

class KotlinDemo1 {
   val testProperty:String?=null
    companion object{
        val testProperty2:String?=null
    }
}

class User {
    //常量
    val height = 175
    //变量
    var name: String = ""

    lateinit var stringBuilder: StringBuilder
    var sex: Boolean = false
    var timeStamp: Long = System.currentTimeMillis();
    var speciality = Speciality("basketball", "no")
    //空指针
    val num:String?=null
    //区间
    val age:IntRange=18..26
    //数组
    var rangeOne:String=String(charArrayOf('W','O','W'))
    var rangeTwo:Array<String> = arrayOf("good","job!")
    
    //数据类型转换
//    var ageString:String=age.toString()
//    var string2Int:Int=ageString.toInt()
    //字符串
    var helloWorld:String="HelloWorld!"
    var helloWorld2:String= String(charArrayOf('H','e','l','l','o','W','o','r','l','d','!'))


    //复写方法
    override fun toString(): String {
        return "UserName is $name,Age is $age"
    }

}


class Speciality  {
    var what:String?=null
    var other:String?=null
    //构造函数
    constructor(){

    }
    constructor(what: String, other: String ){
        this.what=what
        this.other=other
    }

    fun printlnInfo():Unit{
        "Speciality:$what,Other:$other"
    }
    //lambda
    val printlnSpeciality={speciality:String->
        println("speciality:$speciality")
    }
    fun printlnInfo2()= println("Speciality:$what,Other:$other")

}
//接口
interface InputDevice {
    fun  input(event:Any)
}
interface UserInputDevice:InputDevice
interface  BLEInputDevice:InputDevice

class Computer{
    fun addUserInputDevice(inputDevice:InputDevice){
      println("add usb inputDevice:$inputDevice")
    }
}
abstract class Person{
    open fun doSomething(arg1:String){
        println("do:$arg1")
    }
}
//要想被继承，则必须加上open
open class Coder :Person(){
    override fun doSomething(arg1: String) {
        println("son do:$arg1")
        super.doSomething(arg1)
    }
    //要想被子类重写，必须加open
    open fun work(){
        println("Coder is working...")
    }
    open fun work(something:String){
        println("Coder is $something")
    }
}
class CoderSon:Coder(){
  override fun  work(){

  }
    //变量用这个
    lateinit var codeName:String
    //常量用
    val coderJob:String by lazy {
        "helloProgrammer"
    }
    //伴生对象
  companion object CO{
      val coderName:String="SprrowZ"
      fun doProgramming():String{
          return "Coding..."
      }
  }
 //内部类


}

/**
 * 数据类
 */
data class  Student(val name:String ){
   val sex:Boolean=true
}
class Outer{
    val attribute:String by lazy {
        "good"
    }
    class Inner{
      fun hello(){

      }
    }
    inner class Inner2{
      fun hello(){
          println(attribute)
      }
    }
}

    //主函数
    fun main(args:Array<String>) {
      //内部类??????为啥静态和非静态都一样呢

    val inner:Outer.Inner
    val  inner2:Outer.Inner2



    //
    val user=User()
    val speciality=Speciality("basketBall","none")
    //定义方法
    speciality.printlnInfo()
    //测试
    user.toString()
//    user.num!!.length
//    user.num?.length //为空的话，就返回null

    println("helloWorldL:${user.helloWorld}\n" +
            "helloWorld2:${user.helloWorld2}")

    //Coder
    val coder=Coder()
    coder.doSomething("sleep~")
     CoderSon.doProgramming()
   doProgramming()
    //when 代替java中的switch
    val dataList: CharArray = charArrayOf('d','f')
    val  index=0;
    for (i in dataList){
        when(dataList.get(index)){
            'd'-> println("item is ${dataList.get(index)}")
            'f'-> println("item is ${dataList.get(index)}")
        }
    }
}