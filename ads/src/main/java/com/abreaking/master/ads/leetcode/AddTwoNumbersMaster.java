package com.abreaking.master.ads.leetcode;

/**
 * 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * @author liwei
 * @date 2021/2/25
 */
public class AddTwoNumbersMaster {


    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode ret = new ListNode();
            int p = 0;
            ListNode last = ret; //ret的末节点
            while (true){
                int v1 = 0;
                int v2 = 0;
                if (l1!=null){
                    v1 = l1.val;
                    l1 = l1.next;
                }
                if (l2!=null){
                    v2 = l2.val;
                    l2 = l2.next;
                }
                int value = v1+v2+p;
                if (value>9){ //进位处理
                    p = 1;
                    last.val = value - 10;
                }else{
                    p = 0;
                    last.val = value;
                }
                if (l1!=null || l2!=null){
                    last.next = new ListNode();
                    last = last.next;
                }else{
                    break;
                }
            }
            if (p>0){ //还有多的进位继续处理
                last.next = new ListNode(1);
            }

            return ret;
        }
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
