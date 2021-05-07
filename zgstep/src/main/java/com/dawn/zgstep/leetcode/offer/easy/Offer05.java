package com.dawn.zgstep.leetcode.offer.easy;

/**
 * Create by rye
 * at 2021/2/27
 *
 * @description: 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 */
public class Offer05 {
    private static String replaceSpace(String s) {
       StringBuilder sb = new StringBuilder();
       for (char c:s.toCharArray()){
           if (c == ' '){
               sb.append("%20");
           }else{
               sb.append(c);
           }
       }

       return String.valueOf(sb);
    }

    public static void output(){
        System.out.println(replaceSpace("we are happy."));
    }
}
