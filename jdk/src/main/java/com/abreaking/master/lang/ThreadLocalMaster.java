package com.abreaking.master.lang;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author liwei
 * @date 2021/3/22
 */
public class ThreadLocalMaster {

    static ThreadLocal<MyHolder> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            threadPool.submit(()->{
                MyHolder myHolder = new MyHolder();
                threadLocal.set(myHolder);
                System.out.println(Thread.currentThread().getName()+" doing");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                threadLocal.remove();
            });
        }
        Thread.currentThread().join();
        threadLocal = null;
        System.out.println("done");
    }

    static class MyHolder{
        byte[] bytes = new byte[10 * 1024 * 1024];
    }
}
