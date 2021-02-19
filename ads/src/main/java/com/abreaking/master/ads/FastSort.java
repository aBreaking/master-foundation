package com.abreaking.master.ads;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 *
 *它采用了一种分治的策略，通常称其为分治法(Divide-and-ConquerMethod)。
 *
 * 该方法的基本思想是：
 *
 * 1．先从数列中取出一个数作为基准数。
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3．再对左右区间重复第二步，直到各区间只有一个数。
 *
 *  see: https://www.runoob.com/w3cnote/quick-sort.html
 *
 * @author liwei_paas
 * @date 2021/2/19
 */
public class FastSort {

    /**
     * 排序
     *
     * @param array
     * @return
     */
    public static int[] sort(int[] array){
        return doSort(array,0,array.length-1);
    }

    /**
     * 分值策略，排序array 从start 到end的位置
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int[] doSort(int[] array,int start,int end){
        if (start>end){
            return array;
        }
        int x = array[start]; //基准数，比它大的放右边，比它小的放左边
        int i = start; // i 从前往后，找比x大的
        int j = end; // j 从后往前，找比x小的
        while (i<j){
            while (i<j && array[j]>=x){
                //先从后往前找，
                j--;
            }
            // 已经找到了j
            if (i<j){
                array[i] = array[j];
                i++;
            }

            while (i<j && array[i]<x){
                i++;
            }
            // 已经找到了i
            if (i<j){
                array[j] = array[i];
                j--;
            }

        }
        array[i] = x;
        doSort(array,start,i-1);
        doSort(array,j+1,end);
        return array;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int array[] = new int[10];
        for (int j = 0; j < array.length; j++) {
            array[j] = random.nextInt(100);
        }
        System.out.println(Arrays.toString(array));
        int[] sort = sort(array);
        System.out.println(Arrays.toString(array));
    }
}
