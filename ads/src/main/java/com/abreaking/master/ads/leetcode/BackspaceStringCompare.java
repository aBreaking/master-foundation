package com.abreaking.master.ads.leetcode;

/**
 * 给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。
 *
 * @from https://leetcode-cn.com/problems/backspace-string-compare/
 * @author liwei_paas 
 * @date 2020/10/19
 */
public class BackspaceStringCompare {

    public static void main(String[] args) {
        boolean backspaceCompare = new BackspaceStringCompare().backspaceCompare("bbbextm", "bbb#extm");
        System.out.println(backspaceCompare);
    }

    public boolean backspaceCompare(String S, String T) {
        int s = S.length()-1;int t = T.length()-1;
        int ss = 0; int st = 0;
        while (s>=0 || t>=0){
            //找到S下一个可用的位置
            while (s>=0) {
                if (S.charAt(s)=='#'){
                    ss++;
                    s--;
                }else if (ss>0) {
                    ss--;
                    s--;
                }else {
                    break;
                }

            }
            while (t>=0) {
                if (T.charAt(t)=='#'){
                    st++;
                    t--;
                }else if (st>0){
                    st--;
                    t--;
                }else {
                    break;
                }
            }
            if (s>=0 && t>=0){
                if (S.charAt(s) != T.charAt(t)){
                    return false;
                }
            }else{
                if (s>=0 || t>=0){
                    return false;
                }
            }
            s--;t--;
        }
        return true;
    }
}
