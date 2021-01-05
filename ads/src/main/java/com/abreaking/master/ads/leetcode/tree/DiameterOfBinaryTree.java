package com.abreaking.master.ads.leetcode.tree;

/**
 * 543. 二叉树的直径
 *  给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
 * from : https://leetcode-cn.com/problems/diameter-of-binary-tree/
 * @author liwei_paas 
 * @date 2020/8/12
 */
public class DiameterOfBinaryTree {



    /**
     * 算法思路：根据每个根节点：左边的深度+右边的深度 == 直径
     *  以此递归，每一次的左右节点 都视作根节点，计算直径，取最后最大值配置运营
     *  —— by liwei@abreaking
     *  //TODO 一个问题：太慢了！   执行用时：28 ms
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null){
            return 0;
        }
        int dleft = depth(root.left);
        int dright = depth(root.right);
        int rd = depth(root.left)+depth(root.right);
        return  dleft > dright ? Math.max(rd,diameterOfBinaryTree(root.left)):Math.max(rd,diameterOfBinaryTree(root.right));
    }

    /**
     * root 的最大深度找出来
     * @param root
     * @return
     */
    public int depth(TreeNode root){
        if (root == null){
            return 0;
        }
        return Math.max(depth(root.left),depth(root.right))+1;
    }
}
