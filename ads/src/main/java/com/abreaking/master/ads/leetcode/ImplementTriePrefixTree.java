package com.abreaking.master.ads.leetcode;


import java.util.ArrayList;
import java.util.List;

/**
 * 208. 实现 Trie (前缀树)
 * 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
 *
 * 示例:
 *
 * Trie trie = new Trie();
 *
 * trie.insert("apple");
 * trie.search("apple");   // 返回 true
 * trie.search("app");     // 返回 false
 * trie.startsWith("app"); // 返回 true
 * trie.insert("app");
 * trie.search("app");     // 返回 true
 * 说明:
 *
 * 你可以假设所有的输入都是由小写字母 a-z 构成的。
 * 保证所有输入均为非空字符串。
 *
 * from : https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 *
 * @author liwei
 * @date 2021/3/22
 */
public class ImplementTriePrefixTree {

    public static void main(String[] args) {
        Trie trie = new Trie();
        System.out.println(trie.search("a"));
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // 返回 true
        System.out.println(trie.search("app"));     // 返回 false
        System.out.println(trie.startsWith("app")); // 返回 true
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 true
    }

    /**
     * 算法思路：构建一颗数，每个节点保存字符（a-z）
     * @author liwei
     * @date 2021/3/22
     */
    static class Trie {

        Node head ;

        /** Initialize your data structure here. */
        public Trie() {
            head = new Node();
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            Node cur = head;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                Node node = cur.getChildNode(c);
                if (node==null){
                    node = new Node(c);
                    cur.insertChildNode(node);
                }
                cur = node;
            }
            cur.word ++;
        }

        public boolean search(String word) {
            Node cur = head;
            for (int i = 0; i < word.length(); i++) {
                Node node = cur.getChildNode(word.charAt(i));
                if (node == null){
                    return false;
                }
                cur = node;
            }
            return cur!=null && cur.word > 0;
        }



        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            Node cur = head;
            for (int i = 0; i < prefix.length(); i++) {
                Node node = cur.getChildNode(prefix.charAt(i));
                if (node == null){
                    return false;
                }
                cur = node;
            }
            return cur!=null;
        }

        class Node{
            char value; //当前节点的值
            int word = 0; //用于记录单词出现的次数
            Node[] list; //子节点

            public Node() {
                this.list = new Node[26]; //26个字母，每个都有其固定的位置
            }

            public Node(char value) {
                this();
                this.value = value;
            }

            public Node getChildNode(char c){
                return list[c-'a'];
            }

            public void insertChildNode(Node node){
                list[node.value-'a'] = node;
            }
        }
    }
}
