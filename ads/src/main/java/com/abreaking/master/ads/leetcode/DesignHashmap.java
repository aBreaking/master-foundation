package com.abreaking.master.ads.leetcode;

import java.util.Random;

/**
 * 706. 设计哈希映射
 * 不使用任何内建的哈希表库设计一个哈希映射（HashMap）。
 *
 * 实现 MyHashMap 类：
 *
 * MyHashMap() 用空映射初始化对象
 * void put(int key, int value) 向 HashMap 插入一个键值对 (key, value) 。如果 key 已经存在于映射中，则更新其对应的值 value 。
 * int get(int key) 返回特定的 key 所映射的 value ；如果映射中不包含 key 的映射，返回 -1 。
 * void remove(key) 如果映射中存在 key 的映射，则移除 key 和它所对应的 value 。
 *
 * from : https://leetcode-cn.com/problems/design-hashmap/
 * @author liwei
 * @date 2021/3/14
 */
public class DesignHashmap {

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            map.put(i,random.nextInt(100));
            if (i%2==0){
                map.remove(random.nextInt(i+1));
            }
            if (i%3==0){
                map.get(random.nextInt(100));
            }
        }
    }

    static class MyHashMap {

        Node[] nodes = new Node[1024];

        /** Initialize your data structure here. */
        public MyHashMap() {
        }

        /** value will always be non-negative. */
        public void put(int key, int value) {
            putIn(new Entry(key,value),nodes);
        }

        /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
        public int get(int key) {
            int index = key % nodes.length;
            if (nodes[index]!=null){
                Node node = nodes[index];
                while (node!=null){
                    if (node.entry.key == key){
                        return node.entry.value;
                    }
                    node = node.next;
                }
            }
            return -1;
        }

        /** Removes the mapping of the specified value key if this map contains a mapping for the key */
        public void remove(int key) {
            int index = key % nodes.length;
            if (nodes[index]!=null){
                Node pre = nodes[index];
                if (pre.entry.key == key){
                    nodes[index] = pre.next;
                    return;
                }
                Node next = pre.next;
                while (next!=null){
                    if (next.entry.key == key){
                        pre.next = next.next;
                    }
                    pre = next;
                    next = next.next;
                }

            }
        }

        private void putIn(Entry entry,Node[] nodes){
            int index = entry.key % nodes.length;
            if (nodes[index]!=null){
                Node node = nodes[index];
                while (node != null){
                    if (node.entry.key == entry.key){
                        node.entry = entry;
                        return;
                    }
                    if (node.next == null){
                        node.next = new Node(entry);
                        return;
                    }
                    node = node.next;
                }
            }else{
                nodes[index] = new Node(entry);
            }
        }

        class Node{
            Entry entry;
            Node next;

            public Node(Entry entry) {
                this.entry = entry;
            }
        }


        class Entry{
            int key;
            int value;

            public Entry(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
}
