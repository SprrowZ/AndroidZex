package com.dawn.zgstep.kotlins

object TestKotlins {

  fun testMethod(){
      val dataList=ArrayList<Boolean>()
      for ( index in 1..100) {
          dataList.add(false)
          println(dataList.size)
      }
  }
}


fun main(args:Array<String>){
  TestKotlins.testMethod()
    runOnMainThread()
}

