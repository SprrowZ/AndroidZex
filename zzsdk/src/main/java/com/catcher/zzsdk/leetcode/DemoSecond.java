package com.catcher.zzsdk.leetcode;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，
 * 并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
/**
 * Definition for singly-linked list.
 */

import java.util.ArrayList;

/**
******************************************************************
 * 遇到的问题：
 * int数字反转，
 * char类型的数据转化为int类型
 */
public class DemoSecond {

    public static void main(String[] args){
       // test();
        addTwoNumbers(new ListNode(243),new ListNode(564));
    }
    public static void test(){
        ListNode listNode=new ListNode(123);
        ListNode listNode2=new ListNode(456);
        String key1=String.valueOf(listNode.val);
        String key2=String.valueOf(listNode2.val);
        int originNum1=0;
        int originNum2=0;
        for (int i=0;i<key1.length();i++){
            originNum1+=Integer.parseInt(String.valueOf(key1.charAt(i)))*Math.pow(10,i);
//            System.out.println("originNum1:"+originNum1+
//                    ",key1.length:"+key1.length()+",,"+key1.charAt(i)+",,"+Math.pow(10,i));
        }
        for (int j=0;j<key2.length();j++){
            originNum2+=Integer.parseInt(String.valueOf(key2.charAt(j)))*Math.pow(10,j);
        }
//        System.out.println("originNum1："+originNum1+",originNum2:"+originNum2);
       int result=originNum1+originNum2;//结果

       String resultStr=String.valueOf(result);
       ListNode resultNode=null;
        ArrayList<ListNode> resultList=new ArrayList<>();
       for (int k=resultStr.length()-1;k>=0;k--){
           resultList.add(new ListNode(Integer.parseInt(String.valueOf(resultStr.charAt(k)))));
       }
       for (int i=0;i<resultStr.length()-1;i++){
           resultList.get(i).next=resultList.get(i+1);
       }
     System.out.println(resultList.get(0).next.val);
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        int carry = 0;
        while(l1 != null || l2 != null || carry != 0) {
            int l1Val = l1 != null ? l1.val : 0;
            int l2Val = l2 != null ? l2.val : 0;
            int sumVal = l1Val + l2Val + carry;
            carry = sumVal / 10;

            ListNode sumNode = new ListNode(sumVal % 10);
            cursor.next = sumNode;
            cursor = sumNode;

            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }
        return root.next;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}
