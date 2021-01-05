package com.abreaking.master.ads.leetcode.tree;

import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }

    public TreeNode(Integer...x){
        // TODO 将x[] 按顺序恢复成二叉树

    }

     public static void main(String args[]){
         TreeNode node = new TreeNode(1, 2, 3, 4, 5, 6,7,8,9);
         System.out.println(node);
     }
}
