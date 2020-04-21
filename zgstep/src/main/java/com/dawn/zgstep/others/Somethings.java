package com.dawn.zgstep.others;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dawn.zgstep.algorithms.ZLinkedList;

/**
 * Created By RyeCatcher
 * at 2019/11/8
 */
public class Somethings {
    private static int count=5;


    public static void main(String[] args){
//        String testStr="setContentView(R.layout.activity_kotlin   )";
//        String resultStr= testStr.split("\\.")[2];
//        Pattern pattern=Pattern.compile("[[a-z]_]+");
//        Matcher matcher=pattern.matcher(resultStr);
//        while (matcher.find()){
//            System.out.println(matcher.group(0));
//        }
//         test2();

    //    test3();
    //    test4();

  //      test5();
//        test6();
  //      test7();
        test8();
    }





    public static void test2(){
        String target=".asyncloading_item,null);";
        Pattern pattern=Pattern.compile("[a-z0-9_]+");
        Matcher matcher=pattern.matcher(target);
        while (matcher.find()){
            System.out.println(matcher.group(0)+"###");
            break;
        }
    }


    public static void test3(){
        String target="aaaaaaaa.bbbbbbb";
       String result= target.substring(0,target.indexOf("."))+target.substring(target.indexOf("."));
       System.out.println(result);
    }

    public static void test4(){
        String target="AAABBB";
        String target2=" AAA ";
        target= target.replaceAll(" AAA","FFF");
        target2=target2.replaceAll(" AAA"," C CC");
        System.out.println(target);
        System.out.println(target2);
    }

    /**
     * 数字字符串输出数字-----没毛病
     */
    public static void test5(){
       String numStr="33345";
       int result=0;
      char[] array= numStr.toCharArray();
       for (int i=0;i<array.length;i++){
           if (Character.isDigit(array[i])){
               result+=  (array[i]-'0')*Math.pow(10,(numStr.length()-1-i));
           }else{
               return ;
           }
       }
       System.out.println(result);


       HashMap map=new HashMap();
       map.put("A","C");
    }
    public static void test6(){
        ZLinkedList list=new ZLinkedList();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.addNode(4);
        list.addNode(5);
//        list.traversal(list);
//
//        list.traversal(list.reverse(list));
    }


    public static void test7(){

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello...");
                if (count<=0){
                    cancel();
                }
                count--;
            }
        },5000,1000);
    }

    public static void test8(){


    }
}
