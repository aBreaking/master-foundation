package com.abreaking.master.ads.linkedlist;

/**
 * 链表，
 * @author liwei_paas
 * @date 2019/12/26
 */
public class SimpleNode {

    Node head;

    public void addNode(Node node){
        if (head == null){
            head = node;
            return;
        }
        Node tmp = head;
        while (tmp.next!=null){
            tmp = tmp.next;
        }
        tmp.next = node;
    }

    public Node get(){
        return head;
    }

    static class Node{
        int val;
        Node next;
        public Node(int val){
            this.val = val;
        }
    }

    public static void main(String[] args) {
        SimpleNode node = new SimpleNode();
        for (int i = 0; i < 5; i++) {
            node.addNode(new Node(i));
        }
        System.out.println(node.get());
    }
}
