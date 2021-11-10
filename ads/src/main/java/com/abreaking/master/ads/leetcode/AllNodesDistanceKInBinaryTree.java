package com.abreaking.master.ads.leetcode;

import java.util.ArrayList;
import java.util.List;


/**
 * 863. 二叉树中所有距离为 K 的结点
 * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 K 。
 *
 * 返回到目标结点 target 距离为 K 的所有结点的值的列表。 答案可以以任何顺序返回。
 *
 *
 *
 * 示例 1：
 *
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
 * 输出：[7,4,1]
 * 解释：
 * 所求结点为与目标结点（值为 5）距离为 2 的结点，
 * 值分别为 7，4，以及 1
 *
 *
 *
 * 注意，输入的 "root" 和 "target" 实际上是树上的结点。
 * 上面的输入仅仅是对这些对象进行了序列化描述。
 *
 *
 * 提示：
 *
 * 给定的树是非空的。
 * 树上的每个结点都具有唯一的值 0 <= node.val <= 500 。
 * 目标结点 target 是树上的结点。
 * 0 <= K <= 1000.
 *
 * from ： https://leetcode-cn.com/problems/all-nodes-distance-k-in-binary-tree/
 *
 * @author liwei
 * @date 2021/7/28
 */
public class AllNodesDistanceKInBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        System.out.println(new Solution().distanceK(root,root,1));
    }



    static class Solution {
        public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
            List<Integer> ret = new ArrayList<>();

            fromTarget(target,k,ret);

            //从root->target找
            int i = from2Target(root.left, target, 1);
            if (i!=-1){
                if (i==k) ret.add(root.val);
                if (i<k)fromTarget(root.right,(k-i-1),ret);
                if (i>k)fromTarget(root.left,i-k,ret);
            }else{
                i = from2Target(root.right,target,1);
                if (i==k) ret.add(root.val);
                if (i<k)fromTarget(root.left,(k-i-1),ret);
                if (i>k)fromTarget(root.right,i-k,ret);
            }

            return ret;

        }

        public void fromTarget(TreeNode target,int k,List<Integer> ret){
            if (target==null || target.left == null || target.right == null){
                return;
            }
            if (k == 0){
                ret.add(target.val);
                return;
            }

            k--;
            fromTarget(target.left,k,ret);
            fromTarget(target.right,k,ret);
        }

        /**
         * 从from到target的距离
         * @param from
         * @param target
         * @return
         */
        public int from2Target(TreeNode from, TreeNode target,int depth){
            if (from==null){
                return -1;
            }
            if (from.val == target.val){
                return depth;
            }
            depth ++;
            int i = from2Target(from.left, target, depth);
            if (i==-1){
                i = from2Target(from.right,target,depth);
            }
            return i;
        }

    }


    static public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
