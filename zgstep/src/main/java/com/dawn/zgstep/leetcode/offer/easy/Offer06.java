package com.dawn.zgstep.leetcode.offer.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2021/2/27
 *
 * @description: 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回
 * 示例：
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 */
public class Offer06 {

    private static int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        int[] result;
        ListNode currentNode = head;
        if (currentNode == null) return null;
        int i = 0;
        do {
            i++;
            currentNode = currentNode.next;
        } while (currentNode != null);

        result = new int[i];
        currentNode = head;
        int j = result.length - 1;
        do {
            result[j] = currentNode.val;
            j--;
            currentNode = currentNode.next;
        } while (currentNode != null);
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/solution/jian-zhi-offer
     * -06-cong-wei-dao-tou-da-yi-k00m/
     * @param head 翻转数组解法
     * @return
     */
    public int[] reversePrintEx(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int size = list.size();
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = list.get(size - i - 1);
        }
        return res;
    }


    public static void output() {
        ListNode first = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        first.next = second;
        second.next = third;

        int[] result = reversePrint(first);
        for (int item : result) {
            System.out.println(item);
        }
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
