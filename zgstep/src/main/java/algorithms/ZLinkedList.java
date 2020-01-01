package algorithms;

/**
 * java实现链表
 */
public class ZLinkedList {
   public Node head=null;

    class Node{
        Node next=null;
        int data;
        public Node(int data){
            this.data=data;
        }
    }

    /**
     * 向链表中插入数据
     * @param d
     */
    public  void addNode(int d){
        Node newNode=new Node(d);//实例化一个节点
        if (head==null){
            head=newNode;
            return ;
        }

        Node tmp=head;

        while(tmp.next!=null){
            tmp=tmp.next;
        }

        tmp.next=newNode;
    }

    public boolean deleteNode(int index){
        if (index<1 ||index>length()){
            return false;
        }
        if (index==1){
            head=head.next;
            return true;
        }
        int i=1;
        Node preNode=head;
        Node curNode=preNode.next;
        while(curNode!=null){
            if (i==index){
                preNode.next=curNode.next;
                return true;
            }
            preNode=curNode;
            curNode=curNode.next;
            i++;
        }
        return false;

    }

    public int length(){
        int length=0;
        Node tmp=head;
        while(tmp!=null){
            length++;
            tmp=tmp.next;
        }
        return length;
    }


    public ZLinkedList reverse(ZLinkedList list){
        Node head=list.head;
        Node next=null;
        Node pre=null;
        while(head!=null){
            next=head.next;
            head.next=pre;
            pre=head;
            head=next;
        }
        return list;
    }

//    public void traversal(ZLinkedList list){
//        Node tmp=list.head;
//        while(tmp!=null){
//            System.out.println(tmp.data);
//            tmp=tmp.next;
//        }
//    }

}
