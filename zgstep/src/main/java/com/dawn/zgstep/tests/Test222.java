package com.dawn.zgstep.tests;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Test222 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public static void main(String[] args) {

//        ListNode t1 = new ListNode();
//        t1.val = 1;
//        ListNode t2 = new ListNode();
//        t2.val = 2;
//        ListNode t3 = new ListNode();
//        t3.val = 3;
//        ListNode t4 = new ListNode();
//        t4.val =4;
//
//        t1.next = t2;
//        t2.next = t3;
//        t3.next = t4;
//        t4.next = null;
//        ListNode result = deleteDuplicates(t1);
//        while (result != null) {
//            System.out.println("result:" + result.val);
//            result = result.next;
//        }
        int[] heapArr = new int[]{22,43,98,1,4,1,10,7,16,14};
        heapSort(heapArr);
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummyHead = new ListNode(-1);
        ListNode target = dummyHead;
        int repeatVal = -101;
        // dummyHead.next = head;
        while (head != null) {
            //    curVal = head.val;
            if (head.next != null && head.val == head.next.val) {
                repeatVal = head.val;
                System.out.println("重复值：" + repeatVal);
            } else {
                System.out.println("head.val:" + head.val);
                if (head.val != repeatVal) {
                    System.out.println("入队值：" + head.val);
                    ListNode cur = new ListNode(head.val);
                    cur.next = null ;
                    dummyHead.next = cur;
                    dummyHead = dummyHead.next;
                }
            }

            head = head.next;
        }
        return target.next;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void test(){
        int value = Integer.MIN_VALUE;
        Queue<Integer> queue = new LinkedList<>();

       Map<Character,Integer> map = new HashMap<>();

        List<Character> list = new ArrayList(map.keySet());
        Collections.sort(list,(o1, o2) -> map.get(o1) - map.get(o2));

        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        LinkedHashMap<Integer,Integer> linkedHashMap = new LinkedHashMap<>();
        LinkedList linkedList = new LinkedList();
        HashSet hashSet = new HashSet();
        hashSet.add(1);
        String s = "s b s";
        Arrays.asList(1,3);
        PriorityQueue<Map.Entry<Integer,Integer>> queue1 = new PriorityQueue<>(((o1, o2) -> o2.getValue() - o1.getValue()));
    }

    private void loopMap() {
        Map<Integer,Integer> map= new HashMap<>();
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for(Map.Entry<Integer,Integer> entry:entries) {
            entry.getKey();
            entry.getValue();
        }
        map.size();
        char ch = ' ';
        Character c = new Character(ch);
        c.toString();


    }



    private static void heapSort(int arr[]){
        int length = arr.length;
         //建堆
        for(int i=length/2-1;i>=0;i--){
            heapify(arr,i,length);
        }
        //排序
        for(int i = length-1;i>0;i--){
            swap(arr,0,i);
            heapify(arr,0,i);
        }
        //输出--
        for(int i=0;i<length;i++){
            System.out.print(arr[i]+" ");
        }
    }

    //堆排序
    private static void heapify(int arr[] ,int root,int length){
        int largest = root;
        int left = root*2+1;
        int right = root*2+2;
        if(left<length && arr[left]>arr[largest]){
            largest = left;
        }
        if (right<length && arr[right]>arr[largest]){
            largest = right;
        }
        if (largest!=root) {
            swap(arr,root,largest);
            //后面可能无序，需要继续建堆
            heapify(arr,largest,length);
        }
    }

    private static void swap(int[] arr ,int i,int j){
        int temp = arr[i];
        arr[i]= arr[j];
        arr[j] = temp;
    }


}
