package com.abreaking.master.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CyclicBarrier的简单学习使用
 * 一个同步辅助类，在API中是这么介绍的： 它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。 通俗点讲就是：让一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
 *
 * 说白了，就是我设置一个数，当这么多的现成都完毕了，然后才会继续往下面走。。 有点类似countDownLatch，与之不同的 “ 用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。如果需要重置计数，请考虑使用 CyclicBarrier。”
 *
 * http://cmsblogs.com/?p=2241
 * @author liwei_paas
 * @date 2021/2/20
 */
public class CyclicBarrierSimpleMaster {


    public static void main(String[] args) {
        int num = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
            System.out.println("all thread is come");
        });

        for (int i = 0; i < num; i++) {
            final int t = i*1000;
            new Thread(()->{
                try {
                    Thread.sleep(t);
                    System.out.println(Thread.currentThread().getName()+" is comming");
                    cyclicBarrier.await(10,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
