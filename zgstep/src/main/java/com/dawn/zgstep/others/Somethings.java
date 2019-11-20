package com.dawn.zgstep.others;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created By RyeCatcher
 * at 2019/11/8
 */
public class Somethings {
    public static void main(String[] args){
//        String testStr="setContentView(R.layout.activity_kotlin   )";
//        String resultStr= testStr.split("\\.")[2];
//        Pattern pattern=Pattern.compile("[[a-z]_]+");
//        Matcher matcher=pattern.matcher(resultStr);
//        while (matcher.find()){
//            System.out.println(matcher.group(0));
//        }
//         test2();

        test3();
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
}
