package com.dawn.zgstep.tests;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/1/1 6:05 下午
 */
public class TestClass {

    public static void main(String[] args) {
        Demo demo =new Demo();
        ListNode listNode = new ListNode(-1);
        listNode.next = new ListNode(10000);

        demo.removeNthFromEnd(listNode);
    }
    public static class Demo {
        public void removeNthFromEnd(ListNode head) {
            ListNode dummy = head;
            ListNode cur =  dummy;
            cur.next = new ListNode(-111);
            System.out.println("dummy:"+dummy.next.val+",cur:"+cur.next.val);
        }
    }

     public static class ListNode {
      int val;
      ListNode next;
     ListNode() {}
      ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }


}
