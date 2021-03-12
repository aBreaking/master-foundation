package com.abreaking.master.ads.leetcode;

import java.util.Stack;

/**
 * 331. 验证二叉树的前序序列化
 * 序列化二叉树的一种方法是使用前序遍历。当我们遇到一个非空节点时，我们可以记录下这个节点的值。如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
 *
 *      _9_
 *     /   \
 *    3     2
 *   / \   / \
 *  4   1  #  6
 * / \ / \   / \
 * # # # #   # #
 * 例如，上面的二叉树可以被序列化为字符串 "9,3,4,#,#,1,#,#,2,#,6,#,#"，其中 # 代表一个空节点。
 *
 * 给定一串以逗号分隔的序列，验证它是否是正确的二叉树的前序序列化。编写一个在不重构树的条件下的可行算法。
 *
 * 每个以逗号分隔的字符或为一个整数或为一个表示 null 指针的 '#' 。
 *
 * 你可以认为输入格式总是有效的，例如它永远不会包含两个连续的逗号，比如 "1,,3" 。
 *
 * 示例 1:
 *
 * 输入: "9,3,4,#,#,1,#,#,2,#,6,#,#"
 * 输出: true
 * 示例 2:
 *
 * 输入: "1,#"
 * 输出: false
 * 示例 3:
 *
 * 输入: "9,#,#,1"
 * 输出: false
 *
 * from : https://leetcode-cn.com/problems/verify-preorder-serialization-of-a-binary-tree/
 * @author liwei
 * @date 2021/3/12
 */
public class VerifyPreorderSerializationOfABinaryTreeMaster {

    public static void main(String[] args) {
        String s = "9,#,#,1";
        System.out.println(new Solution().isValidSerialization(s));
    }

    /**
     * 注意前提条件：编写一个在不重构树的条件下的可行算法。所以只能通过直接处理字符串。
     * 算法是每次递归叶子节点，判断叶子节点是否正确，如果正确，那么将整个叶子节点视为 # 。 叶子节点的判断是通过紧跟着两个#的来判断
     * 还是用一个栈 保存每次遍历的节点，当遍历到了'#' 判断栈顶是不是 '#'，如果是，就连续pop两个元素。最后判断栈顶还有没有元素即为解
     * @author liwei
     * @date 2021/3/12
     */
    static class Solution {
        public boolean isValidSerialization(String preorder) {

            Stack<String> stack = new Stack<String>();
            String[] split = preorder.split(",");
            int i = 0;
            while (i<split.length){
                if (split[i].equals("#") && !stack.empty() &&  stack.peek().equals("#")){
                    stack.pop();
                    if (stack.empty()){
                        return false;
                    }
                    if (stack.peek().equals("#")){
                        return false;
                    }
                    stack.pop();
                    split[i] = "#";
                }else{
                    stack.push(split[i]);
                    i++;
                }
            }
            if (stack.empty()){
                return true;
            }
            if (stack.size()==1 && stack.peek().equals("#")){
                return true;
            }
            return false;

        }
    }

}
