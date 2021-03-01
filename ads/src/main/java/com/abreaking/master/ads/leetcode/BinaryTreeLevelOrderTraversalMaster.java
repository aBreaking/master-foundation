package com.abreaking.master.ads.leetcode;


import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树的层序遍历
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 * from: https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 *
 * @author liwei
 * @date 2021/3/1
 */
public class BinaryTreeLevelOrderTraversalMaster {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20,new TreeNode(15),new TreeNode(7));
        System.out.println(new Solution().levelOrder(root));
    }

    static class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> ret = new ArrayList<>();
            if (root==null){
                return ret;
            }
            ArrayList<TreeNode> record = new ArrayList();//用它来记录遍历到那层了,遍历完该层后clear，然后将子层左右节点放进来
            record.add(root);
            int start = 0; // 用它记录遍历到那层
            while (record.size()>start){
                List<Integer> list = new ArrayList<>();
                int size = record.size();
                for (int i = start; i < size; i++) {
                    TreeNode treeNode = record.get(i);
                    list.add(treeNode.val);
                    if (treeNode.left!=null){
                        record.add(treeNode.left);
                    }
                    if (treeNode.right!=null){
                        record.add(treeNode.right);
                    }
                }
                ret.add(list);
                start = size;
            }
            return ret;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
