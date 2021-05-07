package com.dawn.zgstep.leetcode.offer.medium;

/**
 * Create by rye
 * at 2021/4/3
 *
 * @description: 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，
 * 25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 */
class Offer46 {
    public static void main(String[] args) {
        System.out.println(translateNum(12258));
    }

    public static int translateNum(int num) {
        String numStr = String.valueOf(num);
        int length = numStr.length();
        if (length == 1) return 1;
        int[] numArray = new int[length];
        for (int i = length-1; i >=0; i--) {
            numArray[i] = num%10;
            num = num/10;
        }
        int s1 = 1;
        int s2;
        int s2Count = numArray[1] + numArray[0]*10;
        if (s2Count > 25) {
            s2 = 1;
        } else {
            s2 = 2;
        }
        int s3 = 0;
        int filterCount = 0;
        for (int i = 2; i < length; i++) {
            int doubleNum = numArray[i - 1] * 10 + numArray[i];
            if (doubleNum > 25) {
                filterCount += s3;
            }
            s3 = s1 + s2;
            s1 = s2;
            s2 = s3;
        }
        System.out.println(s3+","+filterCount);
        return s3 - filterCount;
    }

}
