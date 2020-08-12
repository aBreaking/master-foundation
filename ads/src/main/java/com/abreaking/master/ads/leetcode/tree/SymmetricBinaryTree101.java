package com.abreaking.master.ads.leetcode.tree;

import java.util.*;

/**
 *
 * 给定一个二叉树，检查它是否是镜像对称的。
 *  例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *  但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的
 *
 * from : https://leetcode-cn.com/problems/symmetric-tree/
 * @author liwei_paas
 * @date 2020/8/12
 */
public class SymmetricBinaryTree101 {

    public boolean isSymmetric(TreeNode root){
        return checkRecursive(root,root);
    }

    public boolean checkRecursive(TreeNode p,TreeNode q){
        if (p==null && q==null){
            return true;
        }
        if (p==null || q == null){
            return false;
        }
        if (p.val != q.val){
            return false;
        }
        return checkRecursive(p.left,q.right) && checkRecursive(p.right,q.left);
    }

    public boolean checkIterator(TreeNode p,TreeNode q){
        LinkedList<TreeNode> list = new LinkedList<TreeNode>();
        list.push(q);
        list.push(p);
        while (!list.isEmpty()){
            p = list.poll();
            q = list.poll();
            if (p==null && q==null){
                continue;
            }
            if (p ==null || q==null){
                return false;
            }
            if (p.val != q.val){
                return false;
            }
            list.push(p.left);
            list.push(q.right);

            list.push(p.right);
            list.push(q.left);
        }
        return true;
    }

     public boolean checkTraverse(TreeNode root){
        List<Integer> p = new ArrayList<>();
        List<Integer> q = new ArrayList<>();
        traverseBalanceLeft(root,p);
        traverseBalanceRight(root,q);
         for (int i = 0; i < p.size(); i++) {
             if (p.get(i) != q.get(i)){
                 return false;
             }
         }
         return true;
     }

    public void traverseBalanceLeft(TreeNode root, List<Integer> list){
        if (root == null){
            return;
        }
        list.add(root.val);
        if (root.left==null && root.right!=null){
            list.add(null);
        }else{
            traverseBalanceLeft(root.left,list);
        }

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
        list.add(root.val);
        if (root.right==null && root.left!=null){
            list.add(null);
        }else{
            traverseBalanceRight(root.right,list);
        }

        if (root.left==null && root.right!=null){
            list.add(null);
        }else{
            traverseBalanceRight(root.left,list);
        }
    }


}
