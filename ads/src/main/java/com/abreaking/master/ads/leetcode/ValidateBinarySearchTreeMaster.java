package com.abreaking.master.ads.leetcode;

/**
 * 验证二叉搜索树
 *
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author liwei
 * @date 2021/2/25
 */
public class ValidateBinarySearchTreeMaster {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(5);
        treeNode.left = new TreeNode(4);
        treeNode.right = new TreeNode(7,new TreeNode(6),new TreeNode(8));
        System.out.println(new Solution().isValidBST(treeNode));
    }

    static class SolutionByZhongXuBianli{

        long last = Long.MIN_VALUE;
        /**
         * 既然是二叉搜索数，那么一定是有序的，所以根据中序遍历出来的结果，判断右边是不是大于左边，进而可确定是不是二叉搜索数
         */

        public boolean isValidBST(TreeNode root){
            if (root==null){
                return true;
            }
            boolean left = isValidBST(root.left);
            if (!left){
                return false;
            }
            //找到了最左边的node
            if (root.val<=last){
                return false;
            }
            last = root.val;
            return isValidBST(root.right);
        }
    }


    static class Solution {

        /**
         *那么根据二叉搜索树的性质，在递归调用左子树时，我们需要把上界 max 改为 root.val，即调用 helper(root.left, min, root.val)，
         * 因为左子树里所有节点的值均小于它的根节点的值。同理递归调用右子树时，我们需要把下界 min 改为 root.val，即调用 helper(root.right, root.val, max)。
         *
         * 作者：LeetCode-Solution
         * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree/solution/yan-zheng-er-cha-sou-suo-shu-by-leetcode-solution/
         * 来源：力扣（LeetCode）
         * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         *
         */
        public boolean isValidBST(TreeNode root) {
            return isValidBST(root,Long.MIN_VALUE, Long.MAX_VALUE);
        }

        public boolean isValidBST(TreeNode node, long min,long max) {
            if (node==null){
                return true;
            }
            if (node.val <= min || node.val>=max){
                return false;
            }
            return isValidBST(node.left,min,node.val) && isValidBST(node.right,node.val,max);
        }
    }



    static public class TreeNode {
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
