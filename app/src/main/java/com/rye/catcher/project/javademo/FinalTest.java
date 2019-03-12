package com.rye.catcher.project.javademo;

/**
 * Created at 2019/3/9.
 *
 * @author Zzg
 * @function:
 */
public class FinalTest {
//    public static void main(String[] args)  {
//        String a = "hello2";
//        final String b = "hello";
//        String d = "hello";
//        String c = b + 2;
//        String e = d + 2;
//        System.out.println((a == c));
//        System.out.println((a == e));
//        System.out.println(a);
//        System.out.println(e);
//    }
//

     public static void main(String[] args){
         String testStr=new String("SprrowZ");
         String testStr2="sss";
         change(testStr);
         change(testStr2);
         System.out.println(testStr);
         System.out.println(testStr2);
         testStr2="3333";
         System.out.println(testStr2);
     }
     public static void change(String str){
         str="zzg";
     }
}
