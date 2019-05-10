package com.rye.catcher.project.kotlins;

public class TestKotlin {
   //静态方法不能在顶部调用
   public static void main(String[] args){

   }
   private void testFun(){
      KotlinDemo1.Companion.test1();
   }
}
