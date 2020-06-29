package com.dawn.zgstep.others;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        test9();
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
        String url = "yst://com.xiaodianshi.tv.yst?type=3&isBangumi=1" +
                "&seasonId=24583&epId=232280&progress=876&from=appName&resource=6";
        int start = url.lastIndexOf("&");
        int start2 = url.lastIndexOf("=");
        String sub = url.substring(start2+1);
        String sub2 = url.substring(0,url.lastIndexOf("&"));
        System.out.println(sub2);

    }

    public static void test9(){
        String url = "yst%3A%2F%2Fcom.xiaodianshi.tv.yst%3Ftype%3D3%26isBangumi%3D0%26avId%3D841034100%26cId%3D0%26progress%3D0%26from%3Dxxx%26resource%3Drec%26stay%3D1%26position%3Dshortvideo_HOMECARD%26source%3Dxiaodu";
        String result="";
        try {
            result =  URLDecoder.decode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
