package com.kute.arithmetic;

/**
 * Created by kute on 2017/5/25.
 */
public class Rerverse_linkedlist {

    private static final Rerverse_linkedlist instance = new Rerverse_linkedlist();

    class Node {
        public String value;
        public Node next;
        public Node(String value) {
            this.value = value;
        }
    }

    public Node create_linkedlist() {
        Node head = new Node("1");
        Node temp = head;
        for(int i=2; i<=10; i++) {
            head.next = new Node(String.valueOf(i));
            head = head.next;
        }
        return temp;
    }

    public static void main(String[] args) {
        Node head = instance.create_linkedlist();

        instance.printLinkedList(head);


//        Node tail = instance.reverse(head);
//        instance.printLinkedList(tail);

//        System.out.println(head.value);
        Node node = instance.test(head);
//        Node node = instance.reverse2(head);
        instance.printLinkedList(node);
        System.out.println(node.value);

    }

    public Node test(Node head) {
        Node pre = head, temp;
        while(head.next != null) {
            temp = head.next.next;
            head.next.next = pre;
            pre = head.next;
            head.next = temp;
        }
        return pre;
    }

    private Node reverse2(Node head) {
        if (head.next == null) {
            // 到达尾结点
            return head;
        }
        Node revertHead = reverse2(head.next);
        // 出栈并赋值nextNode对象
        head.next.next = head;
        head.next = null;
        // 返回尾结点（逆置后的头结点）
        return revertHead;
    }

    private Node reverse(Node head) {
        Node pre = head, temp = null, cur = head.next;
        pre.next = null;
        while(cur != null) {
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public void printLinkedList(Node head) {
        if(null != head) {
            String out = head.value;
            while(head.next != null) {
                out += "->" + head.next.value;
                head = head.next;
            }
            System.out.println(out);
        }
    }

}
