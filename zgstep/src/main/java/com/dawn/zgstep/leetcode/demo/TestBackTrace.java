package com.dawn.zgstep.leetcode.demo;

import java.util.LinkedList;
import java.util.List;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/6/18 9:49 下午
 */
public class TestBackTrace {
    public static void main(String[] args){
        int[] nums = new int[]{1,2,3};
        List<List<Integer>> result=  premute(nums);
        System.out.println(result.toString());
    }
    private static List<List<Integer>> res = new LinkedList<>();
    private static List<List<Integer>> premute(int[] nums){
        LinkedList<Integer> trace = new LinkedList<>();
        backtrace(nums,trace);
        return res;
    }
    private static void backtrace(int[] nums,LinkedList<Integer> track){
        if (track.size()==nums.length){
            System.out.println("=========add in res");
            res.add(new LinkedList<>(track));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if (track.contains(nums[i])){
                continue;
            }
            System.out.println("nums[i]=="+nums[i]);
            track.add(nums[i]);
            backtrace(nums,track);
            track.removeLast();
            System.out.println("回溯后---:"+nums[i]+",,trace:"+track.toString());
        }
    }
}
