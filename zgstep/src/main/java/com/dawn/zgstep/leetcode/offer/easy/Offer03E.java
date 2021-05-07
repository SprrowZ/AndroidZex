package com.dawn.zgstep.leetcode.offer.easy;

/**
 * Create by rye
 * at 2021/3/6
 *
 * @description: 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，
 * 但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字
 * <p>
 * 示例 1：
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 * 2<n<100000
 */
public class Offer03E {
    //不能两层遍历，可能会超时; 鸽巢原理
    public static int findRepeatNumber(int[] nums) {
        int[] copyArr = new int[nums.length];
        int result = -1;
        for (int i = 0; i < nums.length; i++) {
            copyArr[nums[i]]++; //因为数字范围在0~n-1之间，所以不会数组越界！！
            if (copyArr[nums[i]] > 1) { //说明对应位置上已经有了原理，就是重复值
                result = nums[i];
            }
        }
        return result;
    }

    public static void out() {
        System.out.println(findRepeatNumber(new int[]{1, 3, 5, 2, 5, 1}));
    }
}
