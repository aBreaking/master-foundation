package com.abreaking.master.ads.leetcode.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树遍历
 * @author liwei_paas 
 * @date 2020/8/12
 */
public class TraverseBinaryTree {
    public static void main(String args[]){
        List<Integer> list = new ArrayList<Integer>();
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(2);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(5);
        treeNode.right.left = new TreeNode(5);
        treeNode.right.right = new TreeNode(4);
        new TraverseBinaryTree().traverseLeft(treeNode,list);
        System.out.println(list);
    }


    /**
     * 前序遍历
     * @param root
     * @param list
     */
    public void traverseLeft(TreeNode root, List<Integer> list){
        if (root == null){
            return;
        }
        traverseLeft(root.left,list);
        list.add(root.val);
        traverseLeft(root.right,list);
    }
    /**
     * 后序遍历
     * @param root
     * @param list
     */
    public void traverseRight(TreeNode root, List<Integer> list){
        if (root == null){
            return;
        }
        traverseRight(root.right,list);
        list.add(root.val);
        traverseRight(root.left,list);
    }

    /**
     * 将二叉树平衡，遍历，为空的节点也加上
     * @param root
     * @param list
     */
    public void traverseBalanceLeft(TreeNode root, List<Integer> list){
        if (root == null){
            return;
        }
        if (root.left==null && root.right!=null){
            list.add(null);
        }else{
            traverseBalanceLeft(root.left,list);
        }
        list.add(root.val);
        if (root.right==null && root.left!=null){
            list.add(null);
        }else{
            traverseBalanceLeft(root.right,list);
        }
    }

    /**
     * 将二叉树平衡，遍历，为空的节点也加上
     * @param root
     * @param list
     */
    public void traverseBalanceRight(TreeNode root, List<Integer> list){
        if (root == null){
            return;
        }
        if (root.right==null && root.left!=null){
            list.add(null);
        }else{
            traverseBalanceRight(root.right,list);
        }
        list.add(root.val);
        if (root.left==null && root.right!=null){
            list.add(null);
        }else{
            traverseBalanceRight(root.left,list);
        }
    }

    @Test
    public void test01(){
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(2);
        treeNode.left.left = new TreeNode(2);
        treeNode.right.left = new TreeNode(2);
        ArrayList<Integer> list = new ArrayList<>();
        traverseBalanceLeft(treeNode,list);
        System.out.println(list);
    }
}
