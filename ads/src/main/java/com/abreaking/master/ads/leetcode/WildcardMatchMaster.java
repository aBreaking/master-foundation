package com.abreaking.master.ads.leetcode;

/**
 * 算法：通配符匹配
 *
 * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符串（包括空字符串）。
 * 两个字符串完全匹配才算匹配成功。
 *
 * 说明:
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
 *
 * 示例 1:
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 *
 * 示例 2:
 * 输入:
 * s = "aa"
 * p = "*"
 * 输出: true
 * 解释: '*' 可以匹配任意字符串。
 *
 * 示例 3:
 * 输入:
 * s = "cb"
 * p = "?a"
 * 输出: false
 * 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 *
 * 示例 4:
 * 输入:
 * s = "adceb"
 * p = "*a*b"
 * 输出: true
 * 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
 *
 * 示例 5:
 * 输入:
 * s = "acdcb"
 * p = "a*c?b"
 * 输出: false
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wildcard-matching
 *
 * @author liwei_paas
 * @date 2020/7/16
 */
public class WildcardMatchMaster {

    public static void main(String args[]){
        WildcardMatchMaster master = new WildcardMatchMaster();
        String s = "aa";
        String p = "ab?";
        System.out.println(master.isMatch(s,p));
    }

    private boolean isMatch(String s, String p){
        char[] pc = p.toCharArray();
        // 从左到右匹配s，匹配成功就将s成功 的部分删除掉
        int ls = 0; //记录匹配到哪里了
        for (int pi = 0; pi < pc.length; pi++) {
            if(s.length()==0){
                return false;
            }
            //找出p 应该匹配出来的部分
            if (pc[pi] == '?'){
                String psub = p.substring(ls, pi);
                //?前面的字符串部分匹配
                s = tryCut(s,psub,1);
                if (s.equals("-1")){
                    return false;
                }
                ls = pi+1;
            }else if(pc[pi] == '*'){
                String psub = p.substring(ls, pi);
                s = tryCut(s,psub,0);
                if (s.equals("-1")){
                    return false;
                }
                //找到*的下一个字母
                if(pi < pc.length-1){
                    int lastIndexOfs = s.lastIndexOf(pc[++pi])+1;
                    if (lastIndexOfs==0){
                        return false;
                    }
                    s = s.substring(0,lastIndexOfs);
                }
            }
        }
        return s.length()==0;
    }

    private static String tryCut(String s ,String sub,int offset){
        int indexOfs = s.indexOf(sub);
        if (indexOfs!=-1){
            int s2sub = indexOfs+sub.length()+offset;
            return s.substring(s2sub>=s.length()?s.length():s2sub);
        }
        return "-1";
    }


}
