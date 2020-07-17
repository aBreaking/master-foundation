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
        //assertMatch("1a","?*",true);
        //assertMatch("1a","1*",true);
        assertMatch("abcdefg","ab?de*g",true);
    }

    private static void assertMatch(String s,String p,boolean flag){
        WildcardMatchMaster master = new WildcardMatchMaster();
        boolean match = master.isMatch(s, p);
        if (match ^ flag){
            System.out.println(s+","+p+"\terror->should be "+flag+",but "+match);
        }
    }

    /**
     * 从左至右裁剪字符串法。
     * 失败！
     * 原因：*的匹配考虑不全面，全量匹配还是部分匹配考虑不周到
     * String s = "a";
     * String p = "?*";
     * @param s
     * @param p
     * @return
     */
    private boolean isMatch(String s, String p){
        char[] pc = p.toCharArray();
        // 从左到右匹配s，匹配成功就将s成功 的部分删除掉
        int ls = 0; //记录p匹配到哪里了
        for (int pi = 0; pi < pc.length; pi++) {
            //先比较p 剩余的部分与s是不是一样的，一样的就没必要继续了
            if (s.equals(p.substring(ls))){
                return true;
            }

            char pCur = pc[pi];
            if(s.length()==0 && pCur == '*'){
                ls = pi+1;
                continue;
            }
            if (pCur != '?' && pCur != '*'){
                continue;
            }
            //有匹配符 * 或者 ？ ,那么截取p中匹配符前面的字符串，看下s还有没有
            String psub = p.substring(ls, pi);
            //此时psub 必定是 s的开头
            int indexOfs = s.indexOf(psub);
            if (indexOfs!=0){
                return false;
            }

            //如果是? ，往后面1位
            int offset ;
            if (pCur == '?'){
                offset = 1;
            }else{
                //* 的处理
                offset = 0; //默认0,即*是结尾了
                //找到 * 的下一个字符
                char _pc;
                while(pi<pc.length){
                    if (pc[pi] != '*'){
                        _pc = pc[pi];
                        break;
                    }
                    pi++;
                }

            }
            int sIndex2cut = psub.length()+offset;
            if (psub.length()>=s.length()){
                //需要cut的部分比s还长，那么匹配失败
                return false;
            }
            //现在将s给剪切,p只是记录到哪里了
            s = s.substring(sIndex2cut); //s的剩余部分
            ls = pi+1; //记录p剪切到哪里了
        }
        return s.length()==0;
    }
    private static String tryCut(String s ,String sub,int offset){
        int indexOfs = s.indexOf(sub);
        int tocut = sub.length()+offset;
        if (indexOfs==0 && tocut<=s.length()){
            return s.substring(tocut);
        }
        return "-1";
    }


}
