package com.abreaking.master.ads.leetcode;


/**
 *
 * ����һ���������� arr ��
 *
 * ����Ҫ��������ȡ�����±� i��j �� k ������ (0 <= i < j <= k < arr.length) ��
 *
 * a �� b �������£�
 *
 * a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
 * b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
 * ע�⣺^ ��ʾ ��λ��� ������
 *
 * �뷵���ܹ��� a == b ��������Ԫ�� (i, j , k) ����Ŀ��
 *
 * ?
 *
 * ʾ�� 1��
 *
 * ���룺arr = [2,3,1,6,7]
 * �����4
 * ���ͣ������������Ԫ��ֱ��� (0,1,2), (0,2,2), (2,3,4) �Լ� (2,4,4)
 * ʾ�� 2��
 *
 * ���룺arr = [1,1,1,1,1]
 * �����10
 * ʾ�� 3��
 *
 * ���룺arr = [2,3]
 * �����0
 * ʾ�� 4��
 *
 * ���룺arr = [1,3,5,7,9]
 * �����3
 * ʾ�� 5��
 *
 * ���룺arr = [7,11,12,9,5,2,7,17,22]
 * �����8
 * ?
 *
 * ��ʾ��
 *
 * 1 <= arr.length <= 300
 * 1 <= arr[i] <= 10^8
 *
 * ��Դ�����ۣ�LeetCode��
 * ���ӣ�https://leetcode-cn.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor
 * ����Ȩ������������С���ҵת������ϵ�ٷ���Ȩ������ҵת����ע��������
 *
 * @author liwei
 * @date 2021/5/18
 */
public class countTripletsThatCanFormTtwoArraysOfEqualXor {

    /**
     *  ���룺arr = [2,3,1,6,7]
     *  * �����4
     *  * ���ͣ������������Ԫ��ֱ��� (0,1,2), (0,2,2), (2,3,4) �Լ� (2,4,4)
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {2,3,1,6,7};
        System.out.println(new Solution().countTriplets(arr));
    }

    /**
     * ǰ׺���
     * ��Si = a0 ^ a1 ^... ^ ai-1.  i=0ʱ��s0 = 0;
     * Si ^ Sj = (a0 ^ a1 ^... ^ a[i-1]) ^ (a0 ^ a1 ^... ^ ai-1 ^ ai ^ a[i+1] ^...^ a[j-1]) =  ai ^ a[i+1] ^...^ a[j-1] => a
     * ͬ��Sj ^ S(k+1) = aj ^ a[j+1] ^... ^ a[k] => b
     *
     * a = b
     * ��
     * Si ^ Sj = Sj ^ S(k+1)  => Si = S(k+1)
     *
     *
     */
    static class Solution {
        public int countTriplets(int[] arr) {
            int[] s = new int[arr.length+1];
            for (int i = 0; i < arr.length; i++) {
                s[i+1] = s[i] ^ arr[i];
            }

            int ret = 0;
            for (int i = 0; i < arr.length; i++) {
                for (int k = i+1; k < arr.length; k++) {
                    if (s[i] == s[k+1]) {
                        ret += k-i;
                    }
                }
            }
            return ret;
        }
    }
}