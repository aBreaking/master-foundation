package com.abreaking.master.ads.leetcode.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家 就餐的问题
 *
 * from https://leetcode-cn.com/problems/the-dining-philosophers/
 * @author liwei_paas
 * @date 2021/2/23
 */
public class DiningPhilosophersMaster {

    /**
     * 分析：
     * 5个哲学家，那么最多只能同时支持4个哲学家拿叉子（一人就餐，三人各拿一把叉子）；5个同时拿叉子就会出现死锁的情况； 所以需要一个信号量semaphore最多支持4个人拿叉子
     * 哲学家拿了左右的叉子，那么就需要将叉子进行加锁，eat完了之后释放锁，释放信号量。 所以，5把叉子需要5把锁
     */

    static Semaphore semaphore = new Semaphore(4); // 表示最多4个人拿叉子

    static ReentrantLock[] locks = new ReentrantLock[5]; //5个叉子5把锁，也为每把叉子编号
    static {
        for (int i = 0; i < 5; i++) {
            locks[i] = new ReentrantLock();
        }
    }

    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        // 找到该哲学家左右两边的叉子
        int left = philosopher;
        int right = (philosopher+1)%5;

        // 尝试获取叉子
        semaphore.acquire();
        locks[left].lock();
        locks[right].lock();
        pickLeftFork.run(); //获取左边的锁
        pickRightFork.run(); // 右边的锁

        eat.run();

        putLeftFork.run(); //吃完放下左边的锁
        putRightFork.run(); //放下右边的锁
        locks[left].unlock();
        locks[right].unlock();

        semaphore.release();

    }
}
