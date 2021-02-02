package com.abreaking.master.ads.leetcode;


import java.util.*;

/**
 * 跳表的设计
 * from : https://leetcode-cn.com/problems/design-skiplist
 *
 * @see: http://zhangtielei.com/posts/blog-redis-skiplist.html
 *
 * 有点慢，还是有点改进呀
 * 执行用时：
 * 381 ms
 * , 在所有 Java 提交中击败了
 * 16.90%
 * 的用户
 * 内存消耗：
 * 46.8 MB
 * , 在所有 Java 提交中击败了
 * 14.09%
 * 的用户
 *
 * @author liwei_paas
 * @date 2021/2/1
 */
public class SkipListMaster {

    public static void main(String[] args) {

        int count = 100 * 1024;

        long l1 = System.currentTimeMillis();
        Skiplist skiplist = new Skiplist();
        for (int i = 1; i < count; i++) {
            skiplist.add(i);
        }
        long l2 = System.currentTimeMillis();
        System.out.println("Skiplist add ->"+(l2-l1));

        Set set = new TreeSet();
        for (int i = 0; i < count; i++) {
            set.add(i);
        }
        long l3 = System.currentTimeMillis();
        System.out.println("TreeSet add ->" +(l3-l2) );

        System.out.println(skiplist.search(99));
        System.out.println(skiplist.search(100531));
        System.out.println(skiplist.search(367324324));

        long l4 = System.currentTimeMillis();
        System.out.println("skip list find ->" + (l4-l3));
        System.out.println(set.contains(99));
        System.out.println(set.contains(100531));
        System.out.println(set.contains(367324324));
        long l5 = System.currentTimeMillis();
        System.out.println("set find ->" + (l5-l4));
    }

    static class Skiplist {
        /**
         * 最大层数
         */
        private static int DEFAULT_MAX_LEVEL = 32;
        /**
         * 随机层数概率，也就是随机出的层数，在 第1层以上(不包括第一层)的概率，层数不超过maxLevel，层数的起始号为1
         */
        private static double DEFAULT_P_FACTOR = 0.25;

        Node head = new Node(null,DEFAULT_MAX_LEVEL); //头节点

        int currentLevel = 1; //表示当前nodes的实际层数，它从1开始


        public Skiplist() {
        }

        public boolean search(int target) {
            Node searchNode = head;
            for (int i = currentLevel-1; i >=0; i--) {
                searchNode = findClosest(searchNode, i, target);
                if (searchNode.next[i]!=null && searchNode.next[i].value == target){
                    return true;
                }
            }
            return false;
        }

        /**
         *
         * @param num
         */
        public void add(int num) {
            int level = randomLevel();
            Node updateNode = head;
            Node newNode = new Node(num,level);
            // 计算出当前num 索引的实际层数，从该层开始添加索引
            for (int i = currentLevel-1; i>=0; i--) {
                //找到本层最近离num最近的节点，而后开始设置每一层该num的跳表
                updateNode = findClosest(updateNode,i,num);
                if (i<level){
                    if (updateNode.next[i]==null){
                        updateNode.next[i] = newNode;
                    }else{
                        Node temp = updateNode.next[i];
                        updateNode.next[i] = newNode;
                        newNode.next[i] = temp;
                    }
                }
            }
            if (level > currentLevel){ //如果随机出来的层数比当前的层数还大，那么超过currentLevel的head 直接指向newNode
                for (int i = currentLevel; i < level; i++) {
                    head.next[i] = newNode;
                }
                currentLevel = level;
            }

        }

        public boolean erase(int num) {
            boolean flag = false;
            Node searchNode = head;
            for (int i = currentLevel-1; i >=0; i--) {
                searchNode = findClosest(searchNode, i, num);
                if (searchNode.next[i]!=null && searchNode.next[i].value == num){
                    //找到该层中该节点
                    searchNode.next[i] = searchNode.next[i].next[i];
                    flag = true;
                    continue;
                }
            }
            return flag;
        }

        /**
         * 找到level层 value 刚好不小于node 的节点
         * @param node
         * @param levelIndex
         * @param value
         * @return
         */
        private Node findClosest(Node node,int levelIndex,int value){
            while ((node.next[levelIndex])!=null && value >node.next[levelIndex].value){
                node = node.next[levelIndex];
            }
            return node;
        }


        /**
         * 随机一个层数
         */
        private static int randomLevel(){
            int level = 1;
            while (Math.random()<DEFAULT_P_FACTOR && level<DEFAULT_MAX_LEVEL){
                level ++ ;
            }
            return level;
        }


        class Node{
            Integer value;
            Node[] next;

            public Node(Integer value,int size) {
                this.value = value;
                this.next = new Node[size];
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        }

    }


}