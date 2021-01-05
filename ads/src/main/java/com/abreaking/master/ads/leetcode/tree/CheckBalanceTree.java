package com.abreaking.master.ads.leetcode.tree;


/**
 * 实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两棵子树的高度差不超过 1。
 *
 * from : https://leetcode-cn.com/problems/check-balance-lcci/
 * @author liwei_paas 
 * @date 2020/8/12
 */
public class CheckBalanceTree {

    public boolean isBalanced(TreeNode root) {
        if (root == null){
            return true;
        }
        if (Math.abs(depth(root.left)-depth(root.right))>1){
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    /**
     * 树的高度
     * @param root
     * @return
     */
    public int  depth(TreeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(depth(root.left),depth(root.right))+1;
    }
}
