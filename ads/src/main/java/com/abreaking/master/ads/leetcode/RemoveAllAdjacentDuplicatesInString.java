package com.abreaking.master.ads.leetcode;

import java.util.Stack;

/**
 * 1047. 删除字符串中的所有相邻重复项
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 *
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 *
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 *
 *
 *
 * 示例：
 *
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 *
 * from : https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/
 * @author liwei
 * @date 2021/3/9
 */
public class RemoveAllAdjacentDuplicatesInString {

    public static void main(String[] args) {
        String S = "aabaabcced";
        System.out.println(new Solution_Stack().removeDuplicates(S));
    }

    /**
     * 出栈入栈的思路解决。
     * 把字符串每个字符入栈，然后与栈顶的元素进行比较
     * from :https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/solution/shan-chu-zi-fu-chuan-zhong-de-suo-you-xi-4ohr/
     *
     */
    static class Solution_Stack{
        public String removeDuplicates(String S) {
            Stack<Character> stack = new Stack();
            char[] chars = S.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!stack.isEmpty() && chars[i] == stack.peek()){
                    stack.pop();
                }else{
                    stack.push(chars[i]);
                }
            }
            StringBuilder ss = new StringBuilder();
            stack.forEach(s->ss.append(s));
            return ss.toString();
        }
    }

    /**
     * 双指针解决，i j,j总在i的后一位。 每次比较i 、j位置的字符是否相等，如果相等，删除i、j的元素，然后i往前一位，j往后一位，再次比较。
     * @author liwei
     * @date 2021/3/9
     */
    static class Solution {
        public String removeDuplicates(String S) {
            if (S.length()<=1){
                return S;
            }
            int i=0,j=1;
            StringBuilder builder = new StringBuilder(S);
            while (j<builder.length()){
                while (i>=0 && j<builder.length() && builder.charAt(i) == builder.charAt(j)){
                    builder.delete(i--,++j);
                    j-=2; //因为删除了两个元素，所以，j的位置需要前移两位
                }
                i = j;
                j++;
            }
            return builder.toString();
        }
    }
}
