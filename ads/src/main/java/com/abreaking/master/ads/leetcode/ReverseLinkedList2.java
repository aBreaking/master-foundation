package com.abreaking.master.ads.leetcode;

/**
 * 92. 反转链表 II
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 *
 * 说明:
 * 1 ≤ m ≤ n ≤ 链表长度。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 * from : https://leetcode-cn.com/problems/reverse-linked-list-ii/
 * @author liwei
 * @date 2021/3/18
 */
public class ReverseLinkedList2 {

    public static void main(String[] args) {

        ListNode listNode = new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4,new ListNode(5)))));
        listNode = new Solution().reverseBetween(listNode,1,3);
        while (listNode!=null){
            System.out.print(listNode.val+",");
            listNode = listNode.next;
        }
    }

    /**
     * 反转链表
     * null->1->2->3->null
     * null->3->2->1->null
     * @param node
     * @return
     */
    private static ListNode reverseListNode(ListNode node){
        ListNode pre = null;
        ListNode cur = node;
        while (cur!=null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 算法思路：从left往right每次将current放在start后面
     * 1->2->3->4    left=2 right=3
     * => 1->3->2->4
     * => 1->4->3->2
     *
     * @author liwei
     * @date 2021/3/18
     */
    static class Solution {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            //找到left开始之前的节点
            ListNode start = new ListNode(-1,head);
            ListNode pre = start;
            for (int i = 1; i < left; i++) {
                pre = pre.next;
            }
            //从left到right开始反转
            ListNode cur = pre.next;
            for (int i = left; i < right; i++) {
                //就是将i处的节点每次放在pre后面
                ListNode curNext = cur.next;
                cur.next = curNext.next;
                curNext.next = pre.next;
                pre.next = curNext;
            }
            return start.next;
        }



    }
    static public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
}
